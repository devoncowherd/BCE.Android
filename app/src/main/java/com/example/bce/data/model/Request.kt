package com.example.bce.data.model

data class Request( var requestId : Int,
                    var caseID : Int,
                    var description : String,
                    var urgency : String,
                    var progress : String
                    )
