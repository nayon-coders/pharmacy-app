package com.mypharmacybd.ui.main_activity.fragments.checkout

import com.mypharmacybd.data_models.address.*
import com.mypharmacybd.data_models.order.PostOrder
import com.mypharmacybd.network.api.models.APIFailure

interface CheckoutContract {

    interface View{
        fun setDivisionToSpinner(divisionList: List<String>)
        fun setDistrictToSpinner(districtList: List<String>)
        fun setUpazilaToSpinner(upazilaList: List<String>)
        fun clearDistrictSpinner()
        fun clearUpazilaSpinner()
        fun showDialog(title:String, message:String)
        fun prepareAllData():PostOrder?

        fun orderSuccess()

    }

    interface Presenter{
        fun setView(view: View)
        fun destroyView()
        fun getDivision()
        fun getDistrictByDivision(division: Division)
        fun getUpazilaByDistrict(district: District)

        fun onDivisionSelected(position:Int)

        fun onDistrictSelected(position:Int)

        fun onUpazilaSelected(position:Int)

        fun onConfirmPlaceOrder()

        fun getSelectedDivision():Division?

        fun getSelectedDistrict():District?

        fun getSelectedUpazila():Upazila?

        fun clearCart()
    }

    interface Model{
        val divisionResponse:DivisionResponse
        val districtResponse :DistrictResponse
        val upazilaResponse : UpazilaResponse

        var selectedDivision:Division?
        var selectedDistrict:District?
        var selectedUpazila:Upazila?

        fun hitDivisionApi(onFinishedListener: OnFinishedListener)
        fun hitDistrictApi(division: Division, onFinishedListener: OnFinishedListener)
        fun hitUpazilaApi(district: District, onFinishedListener: OnFinishedListener)

        fun postOrder(postOrder: PostOrder, onFinishedListener: OnFinishedListener)

        fun clearCart(onFinishedListener: OnFinishedListener)
    }

    interface OnFinishedListener{
        fun onSuccessGetDivision(divisionResponse: DivisionResponse)
        fun onFailureGetDivision(apiFailure: APIFailure)

        fun onSuccessGetDistrict(districtResponse: DistrictResponse)
        fun onFailureGetDistrict(apiFailure: APIFailure)

        fun onSuccessGetUpazila(upazilaResponse: UpazilaResponse)
        fun onFailureGetUpazila(apiFailure: APIFailure)

        fun onPostOrderSuccess()
        fun onPostOrderFailed()
    }

}
