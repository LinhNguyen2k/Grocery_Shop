package com.example.grocery_shop.util

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import androidx.core.content.edit

object UserManager {
    private const val PREF_NAME = "mor.com.spaceshare.user"
    private const val ACCESS_TOKEN = "access_token"
    private const val USER_ID = "user_id_v2"
    private const val DEVICE_ID = "device_id"
    private const val PASSWORD = "pass_word_v2"
    private const val USERNAME = "user_name_login_v2"
    private const val FULL_NAME = "full_name_v2"
    private const val PROFILE_IMAGE = "profile_image_v2"
    private const val DATE_OF_BIRTH = "date_of_birth"
    private const val EMAIL = "email_v2"
    private const val PHONE = "phone_v2"
    private const val COUNTRY_CODE = "country_code"
    private const val ADDRESS = "address"
    private const val IS_LOGGED_IN = "is_logged_in"

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /**
     * Authorization token obtained via Authenticate step
     * Used to access private resources on backend server
     * Token life time lasts 1 day, after that it will be rejected and not valid anymore
     */
    fun setToken(context: Context, token: String) {
        getPrefs(context).edit { putString(ACCESS_TOKEN, token) }
    }

    //Return token without JWT
    fun getToken(context: Context): String = getPrefs(context).getString(ACCESS_TOKEN, "") ?: ""

    /**
     * Set and get User Id
     */
    fun setUserId(context: Context, userId: Int) {
        getPrefs(context).edit { putInt(USER_ID, userId) }
    }

    fun getUserId(context: Context): Int = getPrefs(context).getInt(USER_ID, 0)

    /**
     * Set and get Full Name
     */
    fun setFulLName(context: Context, fullName: String) {
        getPrefs(context).edit { putString(FULL_NAME, fullName) }
    }

    fun setPassWord(context: Context, passWord: String) {
        getPrefs(context).edit { putString(PASSWORD, passWord) }
    }

    fun getPassWord(context: Context): String = getPrefs(context).getString(PASSWORD, "")?: ""

    fun setDateOfBirth(context: Context, passWord: String) {
        getPrefs(context).edit { putString(DATE_OF_BIRTH, passWord) }
    }

    fun getDateOfBirth(context: Context): String = getPrefs(context).getString(DATE_OF_BIRTH, "")?: ""

    fun setUserName(context: Context, userName: String) {
        getPrefs(context).edit { putString(USERNAME, userName) }
    }

    fun getUserName(context: Context): String = getPrefs(context).getString(USERNAME, "")?: ""


    fun getFullName(context: Context): String = getPrefs(context).getString(FULL_NAME, "") ?: ""

    /**
     * Set and get Email
     */
    fun setEmail(context: Context, email: String) {
        getPrefs(context).edit { putString(EMAIL, email) }
    }

    fun getEmail(context: Context): String = getPrefs(context).getString(EMAIL, "") ?: ""

    /**
     * Set and get Email
     */
    fun setPhotoUrl(context: Context, photoUrl: String) {
        getPrefs(context).edit { putString(PROFILE_IMAGE, photoUrl) }
    }

    fun getPhotoUrl(context: Context): String = getPrefs(context).getString(PROFILE_IMAGE, "") ?: ""

    /**
     * Set and get phone number
     */
    fun setPhone(context: Context, phone: String) {
        getPrefs(context).edit { putString(PHONE, phone) }
    }

    fun getPhone(context: Context): String = getPrefs(context).getString(PHONE, "")?.trim() ?: ""

    /**
     * Set and get national code
     */
    fun setCountryCode(context: Context, nationalCode: String) {
        getPrefs(context).edit { putString(COUNTRY_CODE, nationalCode) }
    }

    fun getCountryCode(context: Context): String =
        getPrefs(context).getString(COUNTRY_CODE, "") ?: ""

    fun getPhoneWithCountryCode(context: Context): String {
        return "+${getCountryCode(context)} ${getPhone(context)}"
    }

    /**
     * Set and get Log in state
     */
    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        getPrefs(context).edit { putBoolean(IS_LOGGED_IN, isLoggedIn) }
    }

    fun getLoginState(context: Context): Boolean = getPrefs(context).getBoolean(IS_LOGGED_IN, false)

//    fun setUserInfo(context: Context, user: User) {
//        setUserId(context, user.id ?: 0)
//        setFulLName(context, user.fullName ?: "")
//        setEmail(context, user.email ?: "")
//        setPhone(context, user.mobile ?: "")
//        setCountryCode(context, user.countryCode ?: "")
//        setPhotoUrl(context, user.photoUrl ?: "")
//
//        // reset User Token in case user change email
//        if (!user.token.isNullOrEmpty()) {
//            setToken(context, user.token ?: "")
//
//        }
//    }

//    fun getUserInfo(context: Context): User = User().apply {
//        id = getUserId(context)
//        fullName = getFullName(context)
//        email = getEmail(context)
//    }

    fun clearUserInfo(context: Context) {
        getPrefs(context).edit { remove(USER_ID) }
        getPrefs(context).edit { remove(FULL_NAME) }
        getPrefs(context).edit { remove(PROFILE_IMAGE) }
        getPrefs(context).edit { remove(EMAIL) }
        getPrefs(context).edit { remove(PHONE) }
        getPrefs(context).edit { remove(COUNTRY_CODE) }
        getPrefs(context).edit { remove(ADDRESS) }
    }

    fun clear(context: Context) {
        getPrefs(context).edit { clear() }
    }

    fun setDeviceToken(context: Context, deviceToken: String) {
        getPrefs(context).edit { putString(DEVICE_ID, deviceToken) }
    }

    fun getDeviceToken(context: Context): String = getPrefs(context).getString(DEVICE_ID, "") ?: ""
}