package com.testexample.componentExample.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * File Description
 *
 */
@Entity(tableName = "articles")
class ArticleEntity {

    @SerializedName("title")
    var title: String? = null
    @Embedded
    @SerializedName("owner")
    var owner: ShallowUser? = null
    @SerializedName("is_answered")
    var isAnswered: Boolean = false
    @SerializedName("view_count")
    var viewCount: Int = 0
    @SerializedName("favorite_count")
    var favoriteCount: Int = 0
    @SerializedName("down_vote_count")
    var downVoteCount: Int = 0
    @SerializedName("up_vote_count")
    var upVoteCount: Int =0
    @SerializedName("answer_count")
    var answerCount: Int = 0
    @SerializedName("score")
    var score: Int = 0
    @SerializedName("last_activity_date")
    var lastActivityDate: Date? = null
    @SerializedName("creation_date")
    var creationDate: Date? = null
    @SerializedName("last_edit_date")
    var lastEditDate: Date? = null
    @PrimaryKey
    @SerializedName("question_id")
    var questionId: Int = 0
    @SerializedName("link")
    var link: String? = null
    @SerializedName("body")
    var body: String? = null
    @SerializedName("tags")
    var tags: List<String>? = null

}
