package com.example.grocery_shop.base

interface ActivityResultObservable {
    fun addObserver(activityResultObserver: ActivityResultObserver)
    fun removeObserver(activityResultObserver: ActivityResultObserver)
}