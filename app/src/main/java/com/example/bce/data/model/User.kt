package com.example.bce.data.model

data class User (//var userId : Int,
                 var firstName : String,
                //var middleName : String,
                 var lastName : String,
                 var address : String,
                 var phoneNumber : String,
                 //@SuppressWarnings("WeakerAccess")
                 var email : String,
                 var password : String,
                 //@Exclude val isAuthenticated : Boolean,
                 //@Exclude val isNew : Boolean,
                 //@Exclude val isCreated : Boolean
                ) //: Serializable
