package com.mypharmacybd.data_models.order.get

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mypharmacybd.data_models.order.get.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderData(

    @SerializedName("id") var id: String? = null,
    @SerializedName("order_number ") var orderNumber: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("shipping") var shipping: String? = null,
    @SerializedName("discount") var discount: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("division") var division: Division? = Division(),
    @SerializedName("district") var district: District? = District(),
    @SerializedName("upazila") var upazila: Upazila? = Upazila(),
    @SerializedName("zip") var zip: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("payment_status") var paymentStatus: String? = null,
    @SerializedName("delivery_man") var deliveryMan: DeliveryMan? = DeliveryMan()

) : Parcelable