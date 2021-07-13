package br.com.zup
import com.zup.PixRegistraServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton


@Factory
class GrpcClientFactory {

    @Singleton
    fun fretesClientsStub(@GrpcChannel("pix") channel: ManagedChannel): PixRegistraServiceGrpc.PixRegistraServiceBlockingStub {
        return PixRegistraServiceGrpc.newBlockingStub(channel)
    }
}