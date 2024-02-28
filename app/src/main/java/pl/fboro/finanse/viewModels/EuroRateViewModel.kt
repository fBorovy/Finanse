package pl.fboro.finanse.viewModels

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import pl.fboro.finanse.remote.dto.EuroRate

class EuroRateViewModel {

    private val defaultEuroRate = "http://api.nbp.pl/api/exchangerates/rates/a/eur/"
    private val baseEuroRateUrl = "https://api.nbp.pl/api/exchangerates/rates/A/EUR/"

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json { ignoreUnknownKeys = true})
        }
    }

    suspend fun fetchEuroRate(date: String): Double {
        val url = "$baseEuroRateUrl$date"

        return try {
            client.get<EuroRate>(url).rates.first().mid
        } catch (e: RedirectResponseException) {
            4.50
        } catch (e: ClientRequestException) {
            4.50
        } catch (e: ServerResponseException) {
            client.get<EuroRate>(defaultEuroRate).rates.first().mid
        } catch (e: Exception) {
            4.50
        }
    }
}