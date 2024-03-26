import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate

/**
 * This example demonstrates how to create a WebSocket connection to a server. Only the most
 * important callbacks are overloaded.
 */
class ExampleClient : WebSocketClient {
    constructor(serverURI: URI?) : super(serverURI)

    override fun onOpen(handshakedata: ServerHandshake) {
        send(
            """
            {
                "source" : "Slidy",
                "destination" : "PowerPoint",
                "content": {
                    "command" : "next"
                }
            }
            """.trimIndent()
        )
    }

    override fun onMessage(message: String) {
        println("received: $message")
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        // The close codes are documented in class org.java_websocket.framing.CloseFrame
        println(
            "Connection closed by " + (if (remote) "remote peer" else "us") + " Code: " + code + " Reason: "
                    + reason
        )
    }

    override fun onError(ex: Exception) {
        ex.printStackTrace()
        // If the error is fatal, then onClose will be called additionally
    }

    companion object {
        // Create a TrustManager that ignores hostname verification
        private val trustAllCertificates: X509TrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                // Do nothing
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                // Do nothing
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }
        }

        // Create an SSLContext with the custom TrustManager
        private val sslContext: SSLContext = SSLContext.getInstance("TLS").apply {
            init(null, arrayOf<TrustManager>(trustAllCertificates), null)
        }
    }

    init {
        // Set the custom SSLContext with the TrustManager that ignores hostname verification
        setSocketFactory(sslContext.socketFactory)
    }
}
