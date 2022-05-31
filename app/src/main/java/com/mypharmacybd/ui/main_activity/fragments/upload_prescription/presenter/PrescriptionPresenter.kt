package com.mypharmacybd.ui.main_activity.fragments.upload_prescription.presenter

import com.mypharmacybd.data_models.address.*
import com.mypharmacybd.data_models.prescription.PrescriptionObjectSubmit
import com.mypharmacybd.data_models.user.UserUpdateInfoResponse
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.upload_prescription.PrescriptionContact
import okhttp3.RequestBody

class PrescriptionPresenter(val model : PrescriptionContact.Model) : PrescriptionContact.Presenter ,PrescriptionContact.OnFinishedListener{

    private var _view:PrescriptionContact.View? = null



    private var _divisionResponse: DivisionResponse? = null
    private var _districtResponse: DistrictResponse? = null
    private var _upazilaResponse: UpazilaResponse? = null
    override fun setView(view: PrescriptionContact.View) {
        this._view = view
        getDivision()
    }

    override fun getDivision() {
        _view?.showProgressBar()
        model.getDivision(this)
    }

    override fun getDistrictsByDivision(division: Division) {
        _view?.showProgressBar()
        model.getDistrictByDivision(division, this)
    }

    override fun getUpazilasByDistrict(district: District) {
        _view?.showProgressBar()
        model.getUpazilaByDistrict(district, this)
    }

    override fun getDivisionIdByName(name: String): Int {
        for(division in _divisionResponse?.divisionList!!){
            if(division.name == name) return division.id
        }
        return -1
    }

    override fun getDistrictIdByName(name: String): Int {
        for(district in _districtResponse?.districtList!!){
            if(district.name == name) return district.id
        }

        return -1
    }

    override fun getUpazilaIdByName(name: String): Int {
        for(upazila in _upazilaResponse?.upazilaList!!){
            if(upazila.name == name) return upazila.id
        }
        return -1
    }

    override fun onPrescriptionUpload(objectAllPresc: PrescriptionObjectSubmit) {
        _view?.showProgressBar()
        model.updatePrescriptionInfo(objectAllPresc,this)
    }

    override fun onViewDestroyed() {
        _view = null
    }

    override fun onSuccessGetDivision(divisionResponse: DivisionResponse) {
        _view?.hideProgressBar()
        _view?.setDivisionToView(divisionResponse)
        _divisionResponse = divisionResponse
    }

    override fun onFailureGetDivision(apiFailure: APIFailure) {
        _view?.hideProgressBar()
    }

    override fun onSuccessGetDistrict(districtResponse: DistrictResponse) {
        _view?.hideProgressBar()
        _view?.setDistrictsToView(districtResponse)
        _districtResponse = districtResponse
    }

    override fun onFailureGetDistrict(apiFailure: APIFailure) {
        _view?.hideProgressBar()
    }

    override fun onSuccessGetUpazila(upazilaResponse: UpazilaResponse) {
        _view?.hideProgressBar()
        _view?.setUpazilasToView(upazilaResponse)
        _upazilaResponse = upazilaResponse
    }

    override fun onFailureGetUpazila(apiFailure: APIFailure) {
        _view?.hideProgressBar()
    }

    override fun onSuccessUploadPrescription(valueSuccessObject: UserUpdateInfoResponse) {
        _view?.hideProgressBar()
        _view?.onPrescriptionUpdateComplete(valueSuccessObject)
    }

    override fun onFailureUploadPrescription(apiFailure: APIFailure) {
        _view?.hideProgressBar()
        _view?.onPrescriptionUpdateFailure()
    }
}