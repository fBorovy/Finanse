package pl.fboro.finanse.remote.dto

@kotlinx.serialization.Serializable
data class EuroRate(
    val rates: List<Rate>
)
