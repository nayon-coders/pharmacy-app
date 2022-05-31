package com.mypharmacybd.data_models.address

import com.google.gson.annotations.SerializedName

data class DistrictResponse(
    @SerializedName("data") var districtList: List<District>
){
    fun getDistrictPosition(id:Int):Int{
        for (i in 0..districtList.size){
            val district = districtList[i]
            if(district.id == id) return i
        }
        return -1
    }
}

data class District(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("division_id") val divisionId:String,
)
