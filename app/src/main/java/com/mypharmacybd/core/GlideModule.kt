package com.mypharmacybd.core

import android.util.Log
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        val value =  super.isManifestParsingEnabled()
        Log.d(TAG, "isManifestParsingEnabled: $value")
        return value
    }
    
    companion object{
        private const val TAG = "GlideModule"
    }
}