package com.mypharmacybd.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.db.entity.SessionEntity

@Dao
interface PharmacyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(sessionEntity: SessionEntity):Long

    @Query("SELECT * FROM user_session")
    suspend fun getSession():List<SessionEntity>

    @Query("DELETE FROM user_session WHERE id = 0")
    suspend fun deleteSession()

    @Delete
    suspend fun deleteCartEntity(entity:CartEntity)

    @Query("DELETE FROM product_cart")
    suspend fun deleteAllCartEntity()

    @Update
    suspend fun updateCartEntity(entity: CartEntity)


    @Insert(entity = CartEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cartEntity:CartEntity):Long

    @Query("SELECT * FROM product_cart")
    fun getAllCartProduct():LiveData<List<CartEntity>>
}