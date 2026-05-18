package com.DT099G.exjobb

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.DT099G.exjobb.components.AppComponentDefinition
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Singleton som tillhandahåller en gemensam HttpClient för alla API anrop
 */
object ApiClient {
    val client = HttpClient(CIO) {
        install(plugin = ContentNegotiation) {
            json()
        }
    }
}

/**
 * Hanterar hämtningen och lagringen av användargränssnittet från API:et
 * Ansvarar för inläsningen av sidor från API och konvertering från JSON till objekt
 * Sköter kommunikationen med serven
 */
class LayoutViewModel : ViewModel(){
    //mutableStateOf = gör varialen reaktiv när värdet ändras uppdateras UI
    var viewLayout by mutableStateOf<AppComponentDefinition?>(null)
        private set // Andra får läsa variablen men enbart denna klass får ändra den

    var serverResponse = mutableStateOf<ServerReponse?>(value = null)
        private set


    var currentPage: String = "/start"
    var previousPage: MutableList<String> = mutableListOf()


    /**
     * Funktion som läser in en ny sida från API:et
     */
    fun load(page: String){
        viewModelScope.launch {  // viewModelScope sätter livslängden på processen så det stängs när scopet stängs
            withContext(Dispatchers.IO){  // withContext = kör denna kod på en bakgrundstråd, återgå när den är klar
                fetchAPI(page)
                // Lägger till
                previousPage.add(page)
                currentPage = page
            }
        }
    }

    fun back(){
        viewModelScope.launch {  // viewModelScope sätter livslängden på processen så det stängs när scopet stängs
            withContext( Dispatchers.IO ){  // withContext = kör denna kod på en bakgrundstråd, återgå när den är klar
                // Ta bort senaste, vilket är den sidan man lämnar
                previousPage.removeAt(previousPage.lastIndex)
                // Ladda sidan som var innan
                fetchAPI(previousPage.last())
                //Ändrar current page till nuvarande sidan
                currentPage = previousPage.last()
            }
        }
    }

    fun getWeatherData(access: String, lon: String, lat: String){
        viewModelScope.launch {  // viewModelScope sätter livslängden på processen så det stängs när scopet stängs
            withContext( Dispatchers.IO ){  // withContext = kör denna kod på en bakgrundstråd, återgå när den är klar
                fetchWeather( access = access, lon = lon, lat = lat )
            }
        }
    }

    fun transfer(from: String, to: String, sum: Double){
        viewModelScope.launch {  // viewModelScope sätter livslängden på processen så det stängs när scopet stängs
            withContext( Dispatchers.IO ){  // withContext = kör denna kod på en bakgrundstråd, återgå när den är klar
                transferBalance( from = from, to = to, sum = sum )
            }
        }
    }

    fun add(currency: String){
        viewModelScope.launch {  // viewModelScope sätter livslängden på processen så det stängs när scopet stängs
            withContext( Dispatchers.IO ){  // withContext = kör denna kod på en bakgrundstråd, återgå när den är klar
                addAccount( currency = currency )
            }
        }
    }

    fun remove( account: String ){
        viewModelScope.launch {  // viewModelScope sätter livslängden på processen så det stängs när scopet stängs
            withContext( Dispatchers.IO ){  // withContext = kör denna kod på en bakgrundstråd, återgå när den är klar
                removeAccount( account = account )
            }
        }
    }

    /**
     * Hämtar layouten på json format från API:et och konverterar till AppComponentDefinintion,
     * sparar i variabeln viewLayout
     */
    private suspend fun fetchAPI(page: String) {
        try {
            val url: String = "http://10.0.2.2:3000$page"

            //Skickar ett HTTP GET request till url:en
            val response: String = ApiClient.client.get(urlString = url ).body()

            // Konverterar från json till AppComponentDefinition (Objekt)
            val data = Json.decodeFromString<AppComponentDefinition>(response)

            viewLayout = data

        } catch (e: Exception) {
            println("FetchAPI Error: $e")
        }
    }

    /**
     *  Hämtar väder och layout från servern
     */
    suspend fun fetchWeather(access: String, lon: String, lat: String) {
        try {
            val url: String = "http://10.0.2.2:3000/weather/data"
            //val url: String = "http://localhost:3000/weather/data"

            println("lat: " + lat)
            println("long: " + lon)

            // Skickar en post till servern med json data och tar emot svaret som ett ServerResponse objekt.
            val response: AppComponentDefinition = ApiClient.client.get(urlString = url){
                parameter("access", access)
                parameter("lon", lon)
                parameter("lat", lat)
            }.body()

            viewLayout = response
        } catch (e: Exception) {
            println("FetchAPI Error: $e")
        }
    }


    /**
     *  Överför pengar från ett konto till ett annat.
     */
    suspend fun transferBalance(from: String, to: String, sum: Double) {
        try {
            val url: String = "http://10.0.2.2:3000/banken/transferBalance"

            // Skickar en post till servern med json data och tar emot svaret som ett ServerResponse objekt.
            val response: ServerReponse = ApiClient.client.post(urlString = url){
                contentType(ContentType.Application.Json)
                setBody(TransferRequestType(from = from, to = to, sum = sum))
            }.body()

            // Tar hand om svaret
            serverResponse.value = ServerReponse(title = response.title, message = response.message)
        } catch (e: Exception) {
            println("FetchAPI Error: $e")
            serverResponse.value = ServerReponse(title = "Misslyckades", message = "Ett fel uppstod")
        }
    }

    /**
     *  Skapa ett konto
     */
    suspend fun addAccount(currency: String) {
        try {
            val url: String = "http://10.0.2.2:3000/banken/Account/Add"

            val response: ServerReponse = ApiClient.client.post(urlString = url){
                contentType(ContentType.Application.Json)
                setBody(AddRequestType(currency= currency))
            }.body()

            serverResponse.value = ServerReponse(title = response.title, message = response.message)
        } catch (e: Exception) {
            println("FetchAPI Error: $e")
            serverResponse.value = ServerReponse(title = "Misslyckades", message = "Ett fel uppstod")
        }
    }


    /**
     *  Ta bort ett konto
     */
    suspend fun removeAccount(account: String) {
        try {
            val url: String = "http://10.0.2.2:3000/banken/Account/Remove"

            val response: ServerReponse = ApiClient.client.post(urlString = url){
                contentType(ContentType.Application.Json)
                setBody(RemoveRequestType(account = account))
            }.body()

            serverResponse.value = ServerReponse(title = response.title, message = response.message)
        } catch (e: Exception) {
            println("FetchAPI Error: $e")
            serverResponse.value = ServerReponse(title = "Misslyckades", message = "Ett fel uppstod")
        }
    }
}

@Serializable
data class TransferRequestType(val from: String, val to: String, val sum: Double)

@Serializable
data class AddRequestType(val currency: String)

@Serializable
data class RemoveRequestType(val account: String)

@Serializable
data class ServerReponse(
    var title: String,
    var message: String
)