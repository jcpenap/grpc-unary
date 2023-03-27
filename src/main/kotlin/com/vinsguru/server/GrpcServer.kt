package com.vinsguru.server

import io.grpc.ServerBuilder

fun main(args: Array<String>) {
    val server = ServerBuilder.forPort(6565)
        .addService(BackService())
        .build()
    server.start()
    server.awaitTermination()
}