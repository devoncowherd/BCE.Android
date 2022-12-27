package com.example.bce.data.model

data class BCEUser (var userId : String? = null,
                    var firstName : String? = null,
                    var middleName : String? = null,
                    var lastName : String? = null,
                    var address : String? = null,
                    var city : String? = null,
                    var state : String? = null,
                    var zipCode : String? = null,
                    var phoneNumber : String?= null,
                 //@SuppressWarnings("WeakerAccess")
                    var email : String? = null,
                    var password : String? = null,
                 //@Exclude val isAuthenticated : Boolean,
                 //@Exclude val isNew : Boolean,
                 //@Exclude val isCreated : Boolean
                ) //: Serializable
