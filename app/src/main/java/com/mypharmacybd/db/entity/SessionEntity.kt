package com.mypharmacybd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_session", )
data class SessionEntity(
    var userId: String?,
    var name:String,
    var slug:String,
    var email:String,
    var phone:String,
    var accessToken: String
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}