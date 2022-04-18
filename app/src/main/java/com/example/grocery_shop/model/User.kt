package com.example.grocery_shop.model

data class User(val userId : Long, val avatar : String? = null, val username : String? = null, val fullName : String? = null,
                val email : String? = null, val password : String? = null, val phone : String? = null)

//"userId": 5,
//"avatar": "string",
//"username": "LinhNguyen",
//"fullName": "Nguyen Anh Linh",
//"email": "nguyenanhlinh27092000@gmail.com",
//"password": "$2a$10$91FhEHaAYwpZsUVpm/io/.wuKWlviAjLt4WFTrnsRDEHnI058PXA2",
//"phone": "0815619200",
//"dateOfBirthday": null,
//"authProvider": null,
//"cart": [],
//"orders": [],
//"reviews": [],
//"role": {
//    "id": 3,
//    "name": "ROLE_CUSTOMER"
//},
//"delete": false