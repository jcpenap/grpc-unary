package com.vinsguru.server

import com.vinsguru.models.Balance
import com.vinsguru.models.BalanceCheckRequest
import com.vinsguru.models.BankServiceGrpc
import io.grpc.stub.StreamObserver

class BackService: BankServiceGrpc.BankServiceImplBase() {
    override fun getBalance(request: BalanceCheckRequest?,
                            responseObserver: StreamObserver<Balance>?) {
        val accountNumber = request?.accountNumber
        val balance = Balance.newBuilder()
            .setAmount(accountNumber?.let { it * 10 } ?: 0)
            .build()
        responseObserver?.onNext(balance)
        responseObserver?.onCompleted()
    }
}