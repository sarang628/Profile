package com.sarang.profile.uistate

data class Feed(
    val reviewId: Int = -1,/* 1 */
    val userId: Int,/* 2 */
    val restaurantId: Int,/* 3 */
    val userName: String,/* 4 */
    val restaurantName: String,/* 5 */
    val profilePicUrl: String,/* 6 */
    val contents: String,/* 7 */
    val rating: Float,/* 8 */
    val likeAmount: Int,/* 9 */
    val commentAmount: Int,/* 10 */
    val createDate: String,/* 11 */
    val reviewImage: List<String>,/* 12 */
    val isLike: Boolean,/* 12 */
    val isFavorite: Boolean/* 12 */
)
