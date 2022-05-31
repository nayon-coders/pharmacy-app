package com.mypharmacybd.data_models.address

import com.google.gson.annotations.SerializedName
import java.text.FieldPosition

data class DivisionResponse(
    @SerializedName("data") var divisionList:List<Division>
) {
     fun getDivisionPosition(id:Int):Int{
        for(i in 0..divisionList.size){
            val division = divisionList[i]
            if(division.id == id) return i
        }
        return -1
    }

}

class Division(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
)
