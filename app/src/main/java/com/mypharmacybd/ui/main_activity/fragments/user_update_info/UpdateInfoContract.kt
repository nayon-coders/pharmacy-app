package com.mypharmacybd.ui.main_activity.fragments.user_update_info

import com.mypharmacybd.data_models.address.*
import com.mypharmacybd.data_models.user.UserUpdateInfo
import com.mypharmacybd.data_models.user.UserUpdateInfoResponse
import com.mypharmacybd.network.api.models.APIFailure

interface UpdateInfoContract {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()

        fun setDivisionToView(divisionResponse: DivisionResponse)
        fun setDistrictsToView(districtResponse: DistrictResponse)
        fun setUpazilasToView(upazilaResponse: UpazilaResponse)
        fun onProfileUpdateComplete(userUpdateInfoResponse: UserUpdateInfoResponse)
        fun onProfileUpdateFailed()
    }

    interface Presenter {
        fun setView(view: View)
        fun getDivision()
        fun getDistrictsByDivision(division: Division)
        fun getUpazilasByDistrict(district: District)
        fun onUpdateOrderClicked(userUpdateInfo: UserUpdateInfo)

        fun getDivisionIdByName(name: String): Int
        fun getDistrictIdByName(name: String): Int
        fun getUpazilaIdByName(name: String): Int
    }

    interface Model {
        fun getDivision(onFinishedListener: OnFinishedListener)
        fun getDistrictByDivision(division: Division, onFinishedListener: OnFinishedListener)
        fun getUpazilaByDistrict(district: District, onFinishedListener: OnFinishedListener)
        fun updateUserInfo(updateInfo: UserUpdateInfo, onFinishedListener: OnFinishedListener)
    }

    interface OnFinishedListener {
        // Get Division Response
        fun onSuccessGetDivision(divisionResponse: DivisionResponse)
        fun onFailureGetDivision(apiFailure: APIFailure)

        // Get District Response
        fun onSuccessGetDistrict(districtResponse: DistrictResponse)
        fun onFailureGetDistrict(apiFailure: APIFailure)

        // Get Upazila Response
        fun onSuccessGetUpazila(upazilaResponse: UpazilaResponse)
        fun onFailureGetUpazila(apiFailure: APIFailure)

        // Update User Information Response
        fun onSuccessUpdateUserInfo(userUpdateInfoResponse: UserUpdateInfoResponse)
        fun onFailureUpdateUserInfo(apiFailure: APIFailure)

    }
}