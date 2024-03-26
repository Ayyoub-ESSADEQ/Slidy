package com.example.slidy

import ExampleClient
import android.content.res.Resources
import java.io.InputStream
import java.net.URI
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

fun webSocketClientSSL(url:String, resources: Resources) : ExampleClient{
    val client = ExampleClient(URI(url))
    val storeType = "PKCS12"
    val keyPassword = "slidyslidy"
    val keystoreInputStream: InputStream = resources.openRawResource(R.raw.key)
    val ks = KeyStore.getInstance(storeType)

    ks.load(keystoreInputStream, keyPassword.toCharArray())

    val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    kmf.init(ks, keyPassword.toCharArray())
    val tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    tmf.init(ks)
    var sslContext: SSLContext? = null
    sslContext = SSLContext.getInstance("TLS")
    sslContext.init(kmf.keyManagers, tmf.trustManagers, null)

    client.setSocketFactory(sslContext.socketFactory)
    return client
}