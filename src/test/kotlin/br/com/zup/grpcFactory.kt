package br.com.zup

import br.com.zup.GrpcFactory.GrpcClientFactory
import com.zup.PixRegistraServiceGrpc
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import org.mockito.Mockito.mock

@Factory
    @Replaces(factory = GrpcClientFactory::class)
    class grpcFactory {
        @Bean
        fun registeStub()= mock(PixRegistraServiceGrpc.PixRegistraServiceBlockingStub::class.java)
    }