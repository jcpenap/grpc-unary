package com.vinsguru.server

import com.vinsguru.models.Account
import com.vinsguru.models.TransferRequest
import com.vinsguru.models.TransferResponse
import com.vinsguru.models.TransferStatus
import io.grpc.stub.StreamObserver

class TransferStreamingRequest(
    private val transferResponseStreamObserver: StreamObserver<TransferResponse>
    ): StreamObserver<TransferRequest> {

    override fun onNext(transferRequest: TransferRequest) {
        val fromAccount = transferRequest.fromAccount
        val toAccount = transferRequest.toAccount
        val amount = transferRequest.amount
        val balance = AccountDatabase.getBalance(fromAccount) ?: 0
        var status = TransferStatus.FAILED

        if(balance >= amount && fromAccount != toAccount) {
            AccountDatabase.deductBalance(fromAccount, amount)
            AccountDatabase.addBalance(toAccount, amount)
            status = TransferStatus.SUCCESS
        }

        val fromAccountInfo = Account.newBuilder()
            .setAccountNumber(fromAccount)
            .setAmount(balance)
            .build()

        val toAccountInfo = Account.newBuilder()
            .setAccountNumber(toAccount)
            .setAmount(balance)
            .build()

        val transferResponse = TransferResponse.newBuilder()
            .setStatus(status)
            .addAccounts(fromAccountInfo)
            .addAccounts(toAccountInfo)
            .build()

        this.transferResponseStreamObserver.onNext(transferResponse)

    }

    override fun onError(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onCompleted() {
        AccountDatabase.printAccountDetails()
        this.transferResponseStreamObserver.onCompleted()
    }

}