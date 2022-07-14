package dev.ztivnick.boredwatch

import java.util.*

data class BoredActivityModel(
    var activity: String,
    var type: String,
    var participants: Int,
    var price: Double,
    var link: String,
    var key: String,
    var accessibility: Double
)

public enum class BoredActivityType {
    EDUCATION, RECREATIONAL, SOCIAL, DIY, CHARITY, COOKING, RELAXATION, MUSIC, BUSYWORK;

    public override fun toString(): String =
        name.substring(0, 1).uppercase(Locale.ROOT) + name.substring(1).lowercase(Locale.ROOT);
}