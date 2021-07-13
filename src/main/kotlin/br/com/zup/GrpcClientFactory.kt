package br.com.zup
import com.zup.PixConsultaServiceGrpc
import com.zup.PixDeletaServiceGrpc
import com.zup.PixRegistraServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton


@Factory
class GrpcClientFactory {

    @Singleton
    fun pix(@GrpcChannel("pix") channel: ManagedChannel): PixRegistraServiceGrpc.PixRegistraServiceBlockingStub {
        return PixRegistraServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun pixDelete(@GrpcChannel("pix") channel: ManagedChannel): PixDeletaServiceGrpc.PixDeletaServiceBlockingStub {
        return PixDeletaServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun pixConsulta(@GrpcChannel("pix") channel: ManagedChannel): PixConsultaServiceGrpc.PixConsultaServiceBlockingStub {
        return PixConsultaServiceGrpc.newBlockingStub(channel)
    }
}