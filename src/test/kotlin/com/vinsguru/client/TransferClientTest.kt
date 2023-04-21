package com.vinsguru.client

import com.vinsguru.models.TransferRequest
import com.vinsguru.models.TransferServiceGrpc
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferClientTest {

    private lateinit var transferServiceGrpc: TransferServiceGrpc.TransferServiceStub

    @BeforeAll
    fun setUp() {
        val managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
            .usePlaintext()
            .build()
        transferServiceGrpc = TransferServiceGrpc.newStub(managedChannel)
    }

    @Test
    fun transfer() {
        val latch = CountDownLatch(1)
        val response = TransferStreamingResponse(latch)
        val requestStreamObserver = transferServiceGrpc.transfer(response)

        for (i in 0 .. 100) {
            val request = TransferRequest.newBuilder()
                .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
                .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
                .setAmount(ThreadLocalRandom.current().nextInt(1, 21))
                .build()
            requestStreamObserver.onNext(request)
        }
        requestStreamObserver.onCompleted()
        latch.await()
    }

}