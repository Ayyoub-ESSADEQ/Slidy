package com.example.slidy

import ExampleClient

/**
 * The server here is the phone client connected to the server. It's the one that going to send
 * messages to the websocket server and then to the powerpoint controller.
 */
class Controller {
    private var server : ExampleClient
    constructor(server : ExampleClient){
        this.server = server;
    }
    fun nextSlide(){
        server.send(generateCommand("Slidy", "next"))
    }

    fun previousSlide(){
        server.send(generateCommand("Slidy", "backward"))
    }

    private fun generateCommand(source: String, command: String): String {

        return """
            {
                "source" : "$source",
                "destination" : "PowerPoint",
                "content" : {
                    "command" : "$command"
                }
            } 
        """.trimIndent()
    }
}