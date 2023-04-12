package com.vinsguru.client

import com.google.common.util.concurrent.Uninterruptibles
import com.vinsguru.models.BalanceCheckRequest
import com.vinsguru.models.BankServiceGrpc
import com.vinsguru.models.WithdrawRequest
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankClientTest {

    private lateinit var blockingStub: BankServiceGrpc.BankServiceBlockingStub
    private lateinit var bankServiceStub: BankServiceGrpc.BankServiceStub

    @BeforeAll
    fun setUp() {
        val managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
            .usePlaintext()
            .build()
        blockingStub = BankServiceGrpc.newBlockingStub(managedChannel)
        bankServiceStub = BankServiceGrpc.newStub(managedChannel)
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

    @Test
    fun withdrawAsyncTest() {
        val latch = CountDownLatch(1)
        val request = WithdrawRequest.newBuilder()
            .setAccountNumber(10)
            .setAmount(50)
            .build()
        bankServiceStub.withdraw(request, MoneyStreamingResponse(latch))
        latch.await()
    }

}