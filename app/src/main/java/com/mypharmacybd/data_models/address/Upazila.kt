package com.mypharmacybd.data_models.address

import android.util.Log
import com.google.gson.annotations.SerializedName

data class UpazilaResponse(
    @SerializedName("data") var upazilaList: List<Upazila>
){
    fun getUpazilaPosition(id: Int):Int{
        Log.d(TAG, "getUpazilaPosition: size = ${upazilaList.size}")
        for (i in upazilaList.indices){
            Log.d(TAG, "getUpazilaPosition: Upazila = ${upazilaList[i].name}")
            if(upazilaList[i].id == id) {
                Log.d(TAG, "getUpazilaPosition: id($id) match with ${upazilaList[i].name}")
                return i
            }
        }
        return -1
    }
    
    companion object{
        private const val TAG = "Upazila"
    }
}

data class Upazila(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("district_id") val divisionId:String,
)