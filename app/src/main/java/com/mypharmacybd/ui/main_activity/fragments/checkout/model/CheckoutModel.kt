package com.mypharmacybd.ui.main_activity.fragments.checkout.model

import android.util.Log
import com.mypharmacybd.data_models.address.*
import com.mypharmacybd.data_models.order.PostOrder
import com.mypharmacybd.data_models.order.PostOrderResponse
import com.mypharmacybd.data_models.order.PostProduct
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.checkout.CheckoutContract
import com.mypharmacybd.user.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutModel(
    val apiServices: ApiServices,
    val dbService: PharmacyDAO
) : CheckoutContract.Model {

    val scope = CoroutineScope(Dispatchers.IO)

    private var _divisionResponse:DivisionResponse? = null
    private var _districtResponse:DistrictResponse? = null
    private var _upazilaResponse:UpazilaResponse? = null

    private var _selectedDivision:Division? = null
    private var _selectedDistrict:District? = null
    private var _selectedUpazila:Upazila? = null

    override val divisionResponse get() = _divisionResponse!!
    override val districtResponse get() = _districtResponse!!
    override val upazilaResponse get() = _upazilaResponse!!

    override var selectedDivision: Division? = null
    override var selectedDistrict: District? = null
    override var selectedUpazila: Upazila? = null

    override fun hitDivisionApi(onFinishedListener: CheckoutContract.OnFinishedListener) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken
        Log.d(TAG, "hitDivisionApi: Auth = ${Session.authToken}")

        apiServices.getDivision(headerMap).enqueue(object : Callback<DivisionResponse> {
            override fun onResponse(
                call: Call<DivisionResponse>,
                response: Response<DivisionResponse>
            ) {
                if (response.isSuccessful) {
                    _divisionResponse = response.body()
                    onFinishedListener.onSuccessGetDivision(divisionResponse)
                } else {
                    onFinishedListener.onFailureGetDivision(
                        apiFailure = APIFailure(response.code(), response.message())
                    )
                }
            }

            override fun onFailure(call: Call<DivisionResponse>, t: Throwable) {
                onFinishedListener.onFailureGetDivision(
                    APIFailure(
                        -1, t.message.toString()
                    )
                )
            }
        })


    }

    override fun hitDistrictApi(
        division: Division, onFinishedListener
        : CheckoutContract.OnFinishedListener
    ) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiServices.getDistrictsByDivId(headerMap, division.id.toString()).enqueue(object :
            Callback<DistrictResponse> {
            override fun onResponse(
                call: Call<DistrictResponse>,
                response: Response<DistrictResponse>
            ) {
                if (response.isSuccessful) {
                    _districtResponse = response.body()
                    onFinishedListener.onSuccessGetDistrict(districtResponse)
                } else {
                    onFinishedListener.onFailureGetDistrict(
                        APIFailure(
                            statusCode = response.code(),
                            message = response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<DistrictResponse>, t: Throwable) {
                onFinishedListener.onFailureGetDistrict(
                    APIFailure(
                        statusCode = -1,
                        message = t.message.toString()
                    )
                )
            }
        })
    }

    override fun hitUpazilaApi(
        district: District, onFinishedListener
        : CheckoutContract.OnFinishedListener
    ) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiServices.getUpazilasByDisId(headerMap, district.id.toString()).enqueue(object :
            Callback<UpazilaResponse> {
            override fun onResponse(
                call: Call<UpazilaResponse>,
                response: Response<UpazilaResponse>
            ) {
                if (response.isSuccessful) {
                    _upazilaResponse = response.body()
                    onFinishedListener.onSuccessGetUpazila(upazilaResponse)
                } else {
                    onFinishedListener.onFailureGetUpazila(
                        APIFailure(
                            statusCode = response.code(),
                            message = response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<UpazilaResponse>, t: Throwable) {
                onFinishedListener.onFailureGetUpazila(
                    APIFailure(
                        statusCode = -1,
                        message = t.message.toString()
                    )
                )
            }
        })
    }

    override fun postOrder(postOrder:PostOrder, onFinishedListener: CheckoutContract
    .OnFinishedListener) {
        val headerMap = ApiConfig.headerMap

        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken
        apiServices.postOrder(headerMap, postOrder).enqueue(object : Callback<PostOrderResponse>{
            override fun onResponse(
                call: Call<PostOrderResponse>,
                response: Response<PostOrderResponse>
            ) {
                if(response.isSuccessful){
                    onFinishedListener.onPostOrderSuccess()
                } else {
                    onFinishedListener.onPostOrderFailed()
                }

            }

            override fun onFailure(call: Call<PostOrderResponse>, t: Throwable) {
                onFinishedListener.onPostOrderFailed()
            }
        })
    }

    override fun clearCart(onFinishedListener: CheckoutContract.OnFinishedListener) {
        scope.launch {
            dbService.deleteAllCartEntity()
        }

    }

    companion object {
        private const val TAG = "CheckoutModel"
    }
}