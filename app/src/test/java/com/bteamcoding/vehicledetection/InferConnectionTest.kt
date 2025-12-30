import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File

fun main() = runBlocking {

    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    val baseUrl = "http://localhost:8000"
    val imageFile = File("app/src/main/res/drawable/test_ir_00004.jpg")

    require(imageFile.exists()) { "❌ test.jpg not found" }

    println("📡 Testing /infer connection...")

    try {
        val response = client.post("$baseUrl/infer") {

            parameter("threshold_score", 0.5)

            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "file",
                            imageFile.readBytes(),
                            Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=\"${imageFile.name}\""
                                )
                            }
                        )
                    }
                )
            )
        }

//        println("✅ Status: ${response.status}")
        println("📦 Body:")
        println(response.bodyAsText())

    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        client.close()
    }
}
