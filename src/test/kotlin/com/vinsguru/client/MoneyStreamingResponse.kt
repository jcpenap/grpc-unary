package com.vinsguru.client

import com.vinsguru.models.Money
import io.grpc.stub.StreamObserver

class MoneyStreamingResponse: StreamObserver<Money> {
    override fun onNext(money: Money?) {
        println("Received async: ${money?.value}")
    }

    override fun onError(throwable: Throwable?) {
        println(throwable?.message)
    }

    override fun onCompleted() {
        println("Server is done!!")
    }
}