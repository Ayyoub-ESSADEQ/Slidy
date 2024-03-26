package com.example.slidy

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import java.nio.ByteBuffer

class ChatServer : WebSocketServer {
    var members = mutableMapOf<String, WebSocket>()
    constructor(port: Int) : super(InetSocketAddress(port))

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        //Do nothing
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        //Do nothing
    }

    override fun onMessage(socket: WebSocket, message: String) {
        //The messages that we receive from the clients.
        broadcast(message)
        val letter = Json.decodeFromString<Message>(message)

        //Deconstruction of the messages' components.
        val source = letter.source
        val destination = letter.destination
        val content = letter.content


        //Check if the message only used to instantiate a connection with the server.
        /*
        if(content.command === "connect"){
            members[source] = socket
        }
        else{
            members[destination]?.send(message)
        }
        */
    }

    override fun onMessage(conn: WebSocket, message: ByteBuffer) {
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        ex.printStackTrace()
    }

    override fun onStart() {
        println("Server started!")
        connectionLostTimeout = 0
        connectionLostTimeout = 100
    }
}
