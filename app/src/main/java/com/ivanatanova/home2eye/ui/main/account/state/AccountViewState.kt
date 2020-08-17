package com.ivanatanova.home2eye.ui.main.account.state

import android.os.Parcelable
import com.ivanatanova.home2eye.models.AccountProperties
import kotlinx.android.parcel.Parcelize

const val ACCOUNT_VIEW_STATE_BUNDLE_KEY = "com.ivanatanova.home2eye.ui.main.account.state.AccountViewState"

@Parcelize
class AccountViewState(

    var accountProperties: AccountProperties? = null

) : Parcelable