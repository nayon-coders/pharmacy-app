package com.mypharmacybd.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.other.Constants

@Database(
    entities = [
        SessionEntity::class,
        CartEntity::class
    ],
    version = Constants.DB_VERSION
)
abstract class PharmacyDatabase : RoomDatabase() {
    abstract fun getPharmacyDAO(): PharmacyDAO
}