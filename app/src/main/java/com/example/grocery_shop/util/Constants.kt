package com.example.grocery_shop.util

import android.content.Context
import android.widget.Toast

class Constants {
    companion object {
        val LIVE_BASE_URL = "https://sale-app-server.herokuapp.com/"
    }
    val SEARCH_NEWS_TIME_DELAY = 500L


    //Login response
    val LOGIN_STATUS = "login_status"
    val LOGIN_FULL_NAME = "login_full_name"
    val LOGIN_EMAIL = "login_email"
    val LOGIN_PHOTO_URL = "login_photo_url"
    val LOGIN_TOKEN = "login_token"
    val CART_COUNT = "cart_count"
    val LOGIN_ID = "login_id"
    val LOGIN_UNIQUE_ID = "login_unique_id"
    val LOGIN_MOBILE = "login_mobile_no"
    val DATA = "data"


    //value backstack
    val HOME_FRAGMENT = "HomeFragment"
    val DISCOVER_FRAGMENT = "DiscoverFragment"
    val UPLOAD_FRAGMENT = "UploadFragment"
    val MESSAGE_FRAGMENT = "MessengerFragment"

    fun appToast(mContext: Context?, message: String?) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }


}