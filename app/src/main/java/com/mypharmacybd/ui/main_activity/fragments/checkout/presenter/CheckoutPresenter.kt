package com.mypharmacybd.ui.main_activity.fragments.checkout.presenter

import android.util.Log
import com.mypharmacybd.data_models.address.*
import com.mypharmacybd.data_models.order.PostOrder
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.checkout.CheckoutContract
import com.mypharmacybd.user.Cart

class CheckoutPresenter(
    val model: CheckoutContract.Model
) : CheckoutContract.Presenter, CheckoutContract.OnFinishedListener {

    private var _view: CheckoutContract.View? = null

    private val view get() = _view!!

    override fun setView(view: CheckoutContract.View) {
        _view = view
    }

    override fun getDivision() {
        model.hitDivisionApi(onFinishedListener = this)
    }

    override fun getDistrictByDivision(division: Division) {
        model.hitDistrictApi(division, this)
    }

    override fun getUpazilaByDistrict(district: District) {
        model.hitUpazilaApi(district, this)
    }

    override fun getSelectedDivision(): Division? = model.selectedDivision

    override fun getSelectedDistrict(): District? = model.selectedDistrict

    override fun getSelectedUpazila(): Upazila? = model.selectedUpazila

    override fun onDivisionSelected(position: Int) {
        val division:Division? = getDivisionByPosition(position)
        if(division != null){
            model.hitDistrictApi(division, this)
            model.selectedDivision = division
        } else {
            view.clearDistrictSpinner()
        }
    }

    override fun onDistrictSelected(position: Int) {
        val district:District? = getDistrictByPosition(position)

        if(district != null){
            model.hitUpazilaApi(district, this)
            model.selectedDistrict = district
        } else {
            view.clearUpazilaSpinner()
        }
    }

    override fun onUpazilaSelected(position: Int) {
        val upazila:Upazila? = getUpazilaByPosition(position)
        if(upazila != null){
            model.selectedUpazila = upazila
        }
    }

    override fun onConfirmPlaceOrder() {
        val postOrder:PostOrder? = view.prepareAllData()
        if(postOrder != null){
            model.postOrder(postOrder, this)

        }
    }

    override fun onSuccessGetDivision(divisionResponse: DivisionResponse) {
        view.setDivisionToSpinner(getDivisionList(divisionResponse))
    }

    override fun onFailureGetDivision(apiFailure: APIFailure) {
        Log.d(TAG, "onFailureGetDivision: status code = ${apiFailure.statusCode}")
        Log.d(TAG, "onFailureGetDivision: message = ${apiFailure.message}")
    }

    override fun onSuccessGetDistrict(districtResponse: DistrictResponse) {
        view.setDistrictToSpinner(getDistrictList(districtResponse))
    }

    override fun onFailureGetDistrict(apiFailure: APIFailure) {

    }

    override fun onSuccessGetUpazila(upazilaResponse: UpazilaResponse) {
        view.setUpazilaToSpinner(getUpazilaList(upazilaResponse))
    }

    override fun onFailureGetUpazila(apiFailure: APIFailure) {

    }

    override fun destroyView() {
        _view = null
    }

    private fun getDivisionList(divisionResponse: DivisionResponse):List<String>{
        val divisionList = ArrayList<String>()
        divisionList.add("Select Division")
        for(division in divisionResponse.divisionList){
            divisionList.add(division.name)
        }
        return divisionList
    }

    private fun getDistrictList(districtResponse: DistrictResponse):List<String>{
        val districtList = ArrayList<String>()
        districtList.add("Select District")

        for(district in districtResponse.districtList){
            districtList.add(district.name)
        }

        return districtList
    }

    private fun getUpazilaList(upazilaResponse: UpazilaResponse):List<String>{
        val upazilaList = ArrayList<String>()
        upazilaList.add("Select Upazila")

        for(upazila in upazilaResponse.upazilaList){
            upazilaList.add(upazila.name)
        }

        return upazilaList
    }

    private fun getDivisionByPosition(position: Int):Division?{
        if(position == 0) return  null
        return model.divisionResponse.divisionList[position-1]
    }

    private fun getDistrictByPosition(position: Int):District?{
        if(position == 0) return null
        return model.districtResponse.districtList[position - 1]
    }

    private fun getUpazilaByPosition(position: Int):Upazila?{
        if(position == 0) return null
        return model.upazilaResponse.upazilaList[position - 1 ]
    }

    override fun onPostOrderSuccess() {
        view.orderSuccess()
    }

    override fun onPostOrderFailed() {
        view.showDialog(
            "Order Failed",
            "An error occurred while submitting order."
        )
    }

    override fun clearCart() {
        model.clearCart(this)
    }

    companion object {
        private const val TAG = "CheckoutPresenter"
    }
}