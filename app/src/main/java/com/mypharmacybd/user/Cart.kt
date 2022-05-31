package com.mypharmacybd.user

import androidx.lifecycle.LiveData
import com.mypharmacybd.db.entity.CartEntity

object Cart {
    var cartListLiveData:LiveData<List<CartEntity>>? = null
}