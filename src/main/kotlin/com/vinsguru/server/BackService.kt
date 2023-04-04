package com.vinsguru.server

import com.vinsguru.models.Balance
import com.vinsguru.models.BalanceCheckRequest
import com.vinsguru.models.BankServiceGrpc
import com.vinsguru.models.WithdrawRequest
import com.vinsguru.models.Money
import io.grpc.stub.StreamObserver

class BackService: BankServiceGrpc.BankServiceImplBase() {
    override fun getBalance(request: BalanceCheckRequest?,
                            responseObserver: StreamObserver<Balance>?) {
        val accountNumber = request?.accountNumber
        val balance = Balance.newBuilder()
            .setAmount(AccountDatabase.getBalance(accountNumber) ?: 0)
            .build()
        responseObserver?.onNext(balance)
        responseObserver?.onCompleted()
    }

    override fun withdraw(request: WithdrawRequest?, responseObserver: StreamObserver<Money>?) {

        val accountNumber = request?.accountNumber ?: 0
        val amount = request?.amount ?: 0
        val balance = AccountDatabase.getBalance(accountNumber)
        for (i in 0 .. (amount/10)) {
            val money = Money.newBuilder().setValue(10).build()
            responseObserver?.onNext(money)
            AccountDatabase.deductBalance(accountNumber, 10)
            Thread.sleep(1000)
        }
        responseObserver?.onCompleted()

    }
}