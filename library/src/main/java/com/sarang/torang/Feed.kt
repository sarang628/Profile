package com.sarang.torang

data class Feed(
    val reviewId        : Int           = -1,
    val userId          : Int           = 0,
    val restaurantId    : Int?          = 0,
    val userName        : String        = "",
    val restaurantName  : String?       = "",
    val profilePicUrl   : String        = "",
    val contents        : String        = "",
    val rating          : Float         = 0f,
    val likeAmount      : Int           = 0,
    val commentAmount   : Int           = 0,
    val createDate      : String        = "",
    val reviewImages    : List<String>  = listOf(),
    val isLike          : Boolean       = false,
    val isFavorite      : Boolean       = false
)

val Feed.reviewImage: String get() = if (this.reviewImages.isEmpty()) ""
                                     else this.reviewImages[0]
