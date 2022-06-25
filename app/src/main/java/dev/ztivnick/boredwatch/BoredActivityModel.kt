package dev.ztivnick.boredwatch

data class BoredActivityModel(
    var activity: String,
    var type: String,
    var participants: Int,
    var price: Double,
    var link: String,
    var key: String,
    var accessibility: Double
)