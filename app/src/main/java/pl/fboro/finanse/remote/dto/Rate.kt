package pl.fboro.finanse.remote.dto

@kotlinx.serialization.Serializable
data class Rate(
    val no: String,
    val effectiveDate: String,
    val mid: Double
)