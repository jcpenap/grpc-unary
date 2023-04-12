package com.vinsguru.client

import com.vinsguru.models.Balance
import io.grpc.stub.StreamObserver
import java.util.concurrent.CountDownLatch

class BalanceStreamObserver(private val countDownLatch: CountDownLatch): StreamObserver<Balance> {
    override fun onNext(balance: Balance?) {
        println("Final Balance: ${balance?.amount}")
    }

    override fun onError(p0: Throwable?) {
        this.countDownLatch.countDown()
    }

    override fun onCompleted() {
        println("Server is done!")
        this.countDownLatch.countDown()
    }
}