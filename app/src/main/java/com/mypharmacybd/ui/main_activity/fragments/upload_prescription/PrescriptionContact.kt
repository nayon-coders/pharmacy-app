package com.mypharmacybd.ui.main_activity.fragments.upload_prescription

import com.google.gson.JsonObject
import com.mypharmacybd.data_models.address.*
import com.mypharmacybd.data_models.prescription.PrescriptionObjectSubmit
import com.mypharmacybd.data_models.user.UserUpdateInfo
import com.mypharmacybd.data_models.user.UserUpdateInfoResponse
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.user_update_info.UpdateInfoContract
import okhttp3.RequestBody

interface PrescriptionContact {
    interface View {
        fun showProgressBar()
        fun hideProgressBar()

        fun setDivisionToView(divisionResponse: DivisionResponse)
        fun setDistrictsToView(districtResponse: DistrictResponse)
        fun setUpazilasToView(upazilaResponse: UpazilaResponse)
        fun onPrescriptionUpdateComplete(valueSuccessObject :  UserUpdateInfoResponse)
        fun onPrescriptionUpdateFailure()
    }
    interface Presenter{
        fun setView(view:View)

        fun getDivision()
        fun getDistrictsByDivision(division: Division)
        fun getUpazilasByDistrict(district: District)
        fun getDivisionIdByName(name: String): Int
        fun getDistrictIdByName(name: String): Int
        fun getUpazilaIdByName(name: String): Int
        fun onPrescriptionUpload(objectAllPresc : PrescriptionObjectSubmit)
        fun onViewDestroyed()
    }

    interface Model{
        fun getDivision(onFinishedListener: PrescriptionContact.OnFinishedListener)
        fun getDistrictByDivision(division: Division, onFinishedListener: PrescriptionContact.OnFinishedListener)
        fun getUpazilaByDistrict(district: District, onFinishedListener: PrescriptionContact.OnFinishedListener)
        fun updatePrescriptionInfo(valueObject: PrescriptionObjectSubmit, onFinishedListener: PrescriptionContact.OnFinishedListener)
    }


    interface OnFinishedListener{
        // Get Division Response
        fun onSuccessGetDivision(divisionResponse: DivisionResponse)
        fun onFailureGetDivision(apiFailure: APIFailure)

        // Get District Response
        fun onSuccessGetDistrict(districtResponse: DistrictResponse)
        fun onFailureGetDistrict(apiFailure: APIFailure)

        // Get Upazila Response
        fun onSuccessGetUpazila(upazilaResponse: UpazilaResponse)
        fun onFailureGetUpazila(apiFailure: APIFailure)

        //presciption_Upload
        fun onSuccessUploadPrescription(valueSuccessObject : UserUpdateInfoResponse)
        fun onFailureUploadPrescription(apiFailure: APIFailure)
    }
}