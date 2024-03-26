import android.annotation.SuppressLint
import android.content.res.Resources
import com.example.slidy.R
import com.example.slidy.ChatServer
import org.java_websocket.server.DefaultSSLWebSocketServerFactory
import org.java_websocket.server.WebSocketServer
import java.io.InputStream
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

@SuppressLint("NewApi")
fun websocketServerSSL(port :Int, resources : Resources) : WebSocketServer{
    val chatServer = ChatServer(port)

    // load up the key store
    val storeType = "PKCS12"
    val keyPassword = "slidyslidy"
    val keystoreInputStream: InputStream = resources.openRawResource(R.raw.key)
    // Create and initialize a KeyStore instance
    val ks =
        KeyStore.getInstance(storeType) // You can change this to "PKCS12" or other formats if needed
    ks.load(keystoreInputStream, keyPassword.toCharArray())

    val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    kmf.init(ks, keyPassword.toCharArray())
    val tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    tmf.init(ks)
    var sslContext: SSLContext? = null
    sslContext = SSLContext.getInstance("TLS")
    sslContext.init(kmf.keyManagers, tmf.trustManagers, null)
    chatServer.setWebSocketFactory(DefaultSSLWebSocketServerFactory(sslContext))
    return chatServer
}