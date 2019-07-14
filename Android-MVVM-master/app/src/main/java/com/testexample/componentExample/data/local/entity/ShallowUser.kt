package com.testexample.componentExample.data.local.entity

import com.google.gson.annotations.SerializedName

class ShallowUser {

    @SerializedName("reputation")
    var reputation: Int = 0
    @SerializedName("user_id")
    var user_id: Int = 0
    @SerializedName("user_type")
    var user_type: String?= null
    @SerializedName("accept_rate")
    var accept_rate: Int = 0
    @SerializedName("profile_image")
    var profile_image: String?= null
    @SerializedName("display_name")
    var display_name: String?=null
}