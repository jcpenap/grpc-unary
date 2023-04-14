package com.vinsguru.server

import com.vinsguru.models.TransferRequest
import com.vinsguru.models.TransferResponse
import com.vinsguru.models.TransferServiceGrpc
import io.grpc.stub.StreamObserver

class TransferService: TransferServiceGrpc.TransferServiceImplBase() {
    override fun transfer(responseObserver: StreamObserver<TransferResponse>?): StreamObserver<TransferRequest> {
        return super.transfer(responseObserver)
    }
}