package com.example.myapplication.networking

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by Vinod Kumar on 7/8/19.
 */

@Retention(RetentionPolicy.RUNTIME)
@kotlin.annotation.Target
annotation class NoAuthorizationHeader