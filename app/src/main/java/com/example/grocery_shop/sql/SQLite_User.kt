package com.example.grocery_shop.sql


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.grocery_shop.model.review.User
import java.lang.Exception


class SQLite_User(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "Account_List.db"
        const val DB_TABLE_LOGIN = "registration"
        const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE registration(ID INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS $DB_TABLE_LOGIN")
            onCreate(db)
        }
    }

    fun addUser(
        userId: String
    ): Long {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("userId", userId)
        return db.insert("registration", null, contentValues)
    }


    fun checkUser(userId: String): Boolean {
        var list = getAllUser()
        if (list != null) {
            for (item in list) {
                if (item.userId == userId)
                    return true
            }
        }
        return false
    }

    fun deleteAllUser(): Boolean {
        var db = writableDatabase
        return db.delete(
            DB_TABLE_LOGIN,
            null,
            null
        ) > 0
    }
//    fun updateUser(email: String?, password: String?): Boolean {
//        val db = writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put("email", email)
//        contentValues.put("password", password)
//
//        return db.update("registration",contentValues,"name=?",
//            arrayOf(email.toString())
//        ) >= 0
//    }

//    fun getallUser(): ArrayList<User> {
//        var arrayList: ArrayList<User> = ArrayList()
//        val db = this.writableDatabase
//        val cursor: Cursor?
//        try {
//            cursor = db.rawQuery("select * from registration", null)
//        } catch (e: Exception) {
//            return ArrayList()
//        }
//        if(cursor.moveToFirst())
//        {
//            do {
//                val user = User(cursor.getString(0).toString(),cursor.getString(1).toString())
//                arrayList.add(user)
//            }while (cursor.moveToNext())
//        }
//        return arrayList
//    }

    @SuppressLint("Range")
    fun getAllUser(): List<User> {
        val user: ArrayList<User> = ArrayList<User>()
        val db = this.writableDatabase
        val cursor: Cursor = db.query(
            false, DB_TABLE_LOGIN,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val userId = cursor.getString(cursor.getColumnIndex("userId"))
            user.add(User(userId))
        }
        return user
    }

}