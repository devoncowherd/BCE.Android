package com.example.bce.data.model

data class Request(
    var caseID : String?,
    var dateRequested : String?,
    var dateScheduled : String?,
    var description : String?,
    var urgency : String?,
    var status : String?,
    var fees : List<Fee>?,
    var jobType : String?
    )
