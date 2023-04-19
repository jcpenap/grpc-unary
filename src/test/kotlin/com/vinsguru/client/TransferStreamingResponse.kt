package com.vinsguru.client

import com.vinsguru.models.TransferResponse
import io.grpc.stub.StreamObserver
import java.util.concurrent.CountDownLatch

class TransferStreamingResponse(private val latch: CountDownLatch): StreamObserver<TransferResponse> {
    override fun onNext(transferResponse: TransferResponse) {
        println("Status: ${transferResponse.status}")
        transferResponse.accountsList
            .stream()
            .map { account -> "${account.accountNumber} : ${account.amount}" }
            .forEach{ println(it) }
        println("----------------------------------")
    }

    override fun onError(throwable: Throwable) {
        this.latch.countDown()
    }

    override fun onCompleted() {
        println("All transfers done!")
        this.latch.countDown()
    }
}