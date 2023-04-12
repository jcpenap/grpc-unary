package com.vinsguru.server

import com.vinsguru.models.Balance
import com.vinsguru.models.DepositRequest
import io.grpc.stub.StreamObserver

class CashStreamingRequest(private val balanceStreamObserver: StreamObserver<Balance>,
                           private var accountBalance: Int = 0): StreamObserver<DepositRequest> {


    override fun onNext(depositRequest: DepositRequest?) {
        val accountNumber = depositRequest?.accountNumber ?: 0
        val amount = depositRequest?.amount ?: 0
        this.accountBalance = AccountDatabase.addBalance(accountNumber, amount) ?: 0

    }

    override fun onError(p0: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onCompleted() {
        val balance = Balance.newBuilder().setAmount(this.accountBalance).build()
        this.balanceStreamObserver.onNext(balance)
        this.balanceStreamObserver.onCompleted()
    }
}