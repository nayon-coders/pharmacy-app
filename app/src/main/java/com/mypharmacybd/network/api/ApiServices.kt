package com.mypharmacybd.network.api

import androidx.annotation.RawRes
import com.google.gson.JsonObject
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.data_models.address.DistrictResponse
import com.mypharmacybd.data_models.address.DivisionResponse
import com.mypharmacybd.data_models.address.UpazilaResponse
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.data_models.order.PostOrder
import com.mypharmacybd.data_models.order.PostOrderResponse
import com.mypharmacybd.data_models.prescription.PrescriptionObjectSubmit
import com.mypharmacybd.data_models.search.SearchResponse
import com.mypharmacybd.data_models.slider.SliderData
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.data_models.user.UserUpdateInfo
import com.mypharmacybd.data_models.user.UserUpdateInfoResponse
import com.mypharmacybd.prescriptionView.PrescriptionAllMedicne
import com.mypharmacybd.prescriptionView.detailsOfPrescription.Prescription
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginCredentials
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginResponse
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationData
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationResponse
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.dataclass.ViewOrderItemData
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.AllPrescription
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.viewImage.PrescriptionViewResponse
import kotlinx.parcelize.RawValue
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("register")
    fun userRegistration(
        @HeaderMap headerMap: Map<String, String>,
        @Body registrationData: RegistrationData
    ): Call<RegistrationResponse>

    @POST("login")
    fun userLogin(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap,
        @Body loginCredentials: LoginCredentials
    ): Call<LoginResponse>

    @GET("categories")
    fun getAllCategories(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap,
    ): Call<Categories>


    @GET("products")
    fun getAllProducts(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap
    ): Call<Products>

    @GET("category/products/1")
    fun getHomeProducts(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap
    ): Call<Categories>

    @GET("products/{name}")
    fun getProductByCategory(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap,
        @Path("name") category: String
    ): Call<Products>

    @GET("user/show")
    fun showUserData(@HeaderMap headerMap: Map<String, String>): Call<UserResponse>

    @GET("divisions")
    fun getDivision(@HeaderMap headerMap: Map<String, String>): Call<DivisionResponse>

    @GET("districts/{id}")
    fun getDistrictsByDivId(
        @HeaderMap headerMap: Map<String, String>,
        @Path("id") divisionId: String
    ): Call<DistrictResponse>

    @GET("upazilas/{id}")
    fun getUpazilasByDisId(
        @HeaderMap headerMap: Map<String, String>,
        @Path("id") districtId: String
    ): Call<UpazilaResponse>


    @PUT("user/update")
    fun updateUserInformation(
        @HeaderMap headerMap: Map<String, String>,
        @Body updateInfo: UserUpdateInfo
    ): Call<UserUpdateInfoResponse>

    @GET("sliders")
    fun getSliders(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap
    ): Call<SliderData>

    @POST("order/store")
    fun postOrder(
        @HeaderMap headerMap: Map<String, String>,
        @Body postOrder: PostOrder
    ): Call<PostOrderResponse>



    @GET("orders")
    fun getOrders(
        @HeaderMap headerMap: Map<String, String>
    ): Call<GetOrderResponse>

    @GET("search")
    fun searchByProductName(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap,
        @Query("product") queryString: String
    ):Call<SearchResponse>

    @POST("requisition/store")
    fun uploadPrescription(@HeaderMap headerMap: Map<String, String>,
                           @Body prescriptionObject: PrescriptionObjectSubmit):Call<UserUpdateInfoResponse>

    @GET("requisition/{id}")
    fun getPrescriptionDetails(@HeaderMap headerMap: Map<String, String> ,
                               @Path("id") id : Int) : Call<Prescription>


    @GET("{id}/requisition-products")
    fun getAllMedicineList(@HeaderMap headerMap: Map<String, String> ,
                           @Path("id") id : Int) : Call<PrescriptionAllMedicne>


    @GET("requisitions")
    fun getOrdersPrescription(
        @HeaderMap headerMap: Map<String, String>
    ): Call<AllPrescription>


    @PUT("requisition/cancel/{id}")
    fun cancelReq(@HeaderMap headerMap: Map<String, String>,@Path("id") id : Int ) : Call<JsonObject>


    @GET("{id}/requisition-files")
    fun viewPrescription(@HeaderMap headerMap: Map<String, String>,@Path("id") id : Int) : Call<PrescriptionViewResponse>


    @GET("order/{id}")
    fun viewOrderDetails(@HeaderMap headerMap: Map<String, String>,@Path("id") id : Int) : Call<ViewOrderItemData>

    @PUT("order/cancel/{id}")
    fun cancelSpecificOrder(@HeaderMap headerMap: Map<String, String>,@Path("id") id : Int) : Call<JsonObject>
}