package com.example.myapplication.baseUi

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.myapplication.R
import com.example.myapplication.apputils.FileUtil
import com.example.myapplication.apputils.RotateImageUtil
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by Vinod Kumar on 9/8/19.
 */
open class BaseActivity : AppCompatActivity() {

    val REQUEST_CODE_TAKE_PICTURE = 3000
    val REQUEST_CODE_GALLERY = 3001
    val REQUEST_CODE_PDF = 3002

    lateinit var mChooseImageAlert: AlertDialog
    private var mImageCaptureUri: Uri? = null
    private var mCapturedListener: OnCapturedListener? = null
    private var mFile: File? = null


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> try {
                    val mImageUri = savingImage(
                        RotateImageUtil.handleSamplingAndRotationBitmap(
                            applicationContext,
                            data!!.data
                        )
                    )
                    showPicTypeDialog(mImageUri!!)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                REQUEST_CODE_TAKE_PICTURE -> if (data != null && data.data != null) {
                    mImageCaptureUri = data.data
                } else if (mImageCaptureUri != null) {
                    try {
                        val mImageUri = savingImage(
                            RotateImageUtil.handleSamplingAndRotationBitmap(
                                applicationContext,
                                mImageCaptureUri
                            )
                        )
                        showPicTypeDialog(mImageUri!!)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }


            }
        }
    }


    open fun savingImage(finalBitmap: Bitmap): Uri? {
        var finalBitmap = finalBitmap
        finalBitmap = getResizedBitmap(finalBitmap, 400)
        val root = getExternalFilesDir(null)!!.absolutePath
        val myDir = File(root + AppConstant.IMAGE_PATH)
        if (!myDir.isDirectory) {
            myDir.mkdirs()
        }
        val fname = "Image-" + System.currentTimeMillis() + ".jpg"
        val file = File(myDir, fname)
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Uri.fromFile(file)
    }

    /**
     * reduces the size of the image
     *
     * @param image
     * @param maxSize
     * @return
     */
    open fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }


    private fun showPicTypeDialog(uri: Uri) {
        try {
            mFile = FileUtil.getFileToKeepImage(this@BaseActivity)
            val inputStream = contentResolver.openInputStream(uri)
            val fileOutputStream = FileOutputStream(mFile)
            FileUtil.copyStream(inputStream, fileOutputStream)
            fileOutputStream.close()
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (mCapturedListener != null) {
            mCapturedListener!!.onCaptured(Uri.fromFile(mFile))
        }
    }


    /**
     * this method is used to show Post dialog
     */
    open fun showPicDialog() {
        val items = resources.getStringArray(R.array.upload_photo)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.upload_image))
        builder.setItems(
            items
        ) { dialog: DialogInterface?, item: Int ->
            if (items[item] == items[0]) {
                startCamera()
            } else if (items[item] == items[1]) {
                openGallery()
            } else if (items[item] == items[2]) {
                mChooseImageAlert?.dismiss()
            }
        }
        mChooseImageAlert = builder.create()
        if (!mChooseImageAlert.isShowing) {
            mChooseImageAlert.show()
        }

    }


    /**
     * This method is used to start camera
     */
    open fun startCamera() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val photoPickerIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val baseDir = externalCacheDir!!.path
            val file =
                File(baseDir, "tmp_" + System.currentTimeMillis() + ".jpg")
            mImageCaptureUri = FileProvider.getUriForFile(
                this, packageName
                        + ".provider", file
            )
            photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                photoPickerIntent.clipData = ClipData.newRawUri("", mImageCaptureUri)
                photoPickerIntent.addFlags(
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            startActivityForResult(photoPickerIntent, REQUEST_CODE_TAKE_PICTURE)
        } else {
            Log.v(javaClass.simpleName, "Media not mounted.")
        }
    }

    /**
     * This method is used to take picture from gallery
     */
    open fun openGallery() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(
                Intent.createChooser(photoPickerIntent, "Select Picture"),
                REQUEST_CODE_GALLERY
            )
        } else {
            Log.v(javaClass.simpleName, "Media not mounted.")
        }
    }


    /**
     * This method is used to initialize the OnPicCapturedListener
     *
     * @param listener
     */
    open fun setOnCapturedListener(listener: OnCapturedListener) {
        mCapturedListener = listener
    }

    /**
     * This interface is used to catch the photo bitmap from camera or gallery
     * and navigate, on where this interface is implemented
     */
    interface OnCapturedListener {
        fun onCaptured(uri: Uri?)
    }

    fun showProgressDialog() {
        indeterminateProgressDialog("This a progress dialog")
    }

    fun hideProgressDialog() {
        indeterminateProgressDialog("This a progress dialog").hide()
    }

    fun showShortToast(message: String) {
        toast(message)
    }

    fun showLongToast(message: String) {
        longToast(message)
    }
}