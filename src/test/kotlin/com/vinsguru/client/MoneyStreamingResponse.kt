package com.vinsguru.client

import com.vinsguru.models.Money
import io.grpc.stub.StreamObserver
import java.util.concurrent.CountDownLatch

class MoneyStreamingResponse(private val latch: CountDownLatch): StreamObserver<Money> {
    override fun onNext(money: Money?) {
        println("Received async: ${money?.value}")
    }

    override fun onError(throwable: Throwable?) {
        println(throwable?.message)
        latch.countDown()
    }

    override fun onCompleted() {
        println("Server is done!!")
        latch.countDown()
    }
}