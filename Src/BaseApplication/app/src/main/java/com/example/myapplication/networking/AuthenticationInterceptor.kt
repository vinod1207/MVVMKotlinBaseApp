package com.example.myapplication.networking

import android.content.Context
import com.example.myapplication.BaseAppApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Vinod Kumar on 7/8/19.
 */
/*This class is used to intercept the request created by retrofit and add required parameter for network bidding... */
class AuthenticationInterceptor(context: Context) : Interceptor {


    init {
        BaseAppApplication.app?.mAppComponent?.provideIn(context.applicationContext as BaseAppApplication)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        // Nothing to add to intercepted request if:
        // a) Authorization value is empty because user is not logged in yet
        // b) There is already a header with updated Authorization value
        val builder = originalRequest.newBuilder()


        // Add authorization header with updated authorization value to intercepted request
        builder.header("Content-Type", "application/json")

       /* val invocation = originalRequest.tag(Invocation::class.java)
        var noAuthorization: NoAuthorizationHeader? = null
        if (invocation != null) {
            noAuthorization = invocation.method().getAnnotation(NoAuthorizationHeader::class.java)
        }


        if (noAuthorization == null) {
            if (!TextUtils.isEmpty(AppPreferences.getPreferenceInstance(FonHomeApplication.getApp()).getAuthToken())) {
                builder.header(
                    KEY_AUTHORIZATION,
                    AppPreferences.getPreferenceInstance(FonHomeApplication.getApp()).getAuthToken()
                )
            }
        }
*/

        val modifiedRequest = builder.build()
        return chain.proceed(modifiedRequest)
    }

    companion object {


        private val KEY_AUTHORIZATION = "Authorization"
    }


}
