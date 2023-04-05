package com.vinsguru.client

import com.vinsguru.models.BalanceCheckRequest
import com.vinsguru.models.BankServiceGrpc
import com.vinsguru.models.WithdrawRequest
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankClientTest {

    private lateinit var blockingStub: BankServiceGrpc.BankServiceBlockingStub

    @BeforeAll
    fun setUp() {
        val managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
            .usePlaintext()
            .build()
        blockingStub = BankServiceGrpc.newBlockingStub(managedChannel)
    }

    @Test
    fun balanceTest() {
        val balanceCheckRequest = BalanceCheckRequest.newBuilder()
            .setAccountNumber(5)
            .build()
        val balance = this.blockingStub.getBalance(balanceCheckRequest)
        println("Received: " + balance.amount)
    }

    @Test
    fun withdrawTest() {
        val request = WithdrawRequest.newBuilder()
            .setAccountNumber(7)
            .setAmount(10)
            .build()
        this.blockingStub.withdraw(request)
            .forEachRemaining { money -> println("Received: ${money.value}") }
    }

}