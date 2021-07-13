package br.com.zup

import br.com.zup.chavePix.registroChave.RegistroChaveRequest
import br.com.zup.chavePix.registroChave.TipoChave
import br.com.zup.chavePix.registroChave.TipoConta
import com.zup.PixRegistraResponse
import com.zup.PixRegistraServiceGrpc
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest(
    transactional = false,
    rollback = false,
    transactionMode = TransactionMode.SINGLE_TRANSACTION
)
class RegistraChavePixtTest {

    @Inject
    lateinit var grpcClient: PixRegistraServiceGrpc.PixRegistraServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @Test
    fun deveCadastrarChavePix() {

        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
        val pixId = "5260263c-a3c1-4727-ae32-3bdb2538841b"

        val request = HttpRequest.POST("/pix/${clienteId}/registra", criaRequisicao())

        Mockito.`when`(grpcClient.cadastraChave(criaRequisicao().toGrpcRequest(clienteId)))
            .thenReturn(
                PixRegistraResponse.newBuilder()
                    .setPixId("5260263c-a3c1-4727-ae32-3bdb25388412")
                    .setClientId("5260263c-a3c1-4727-ae32-3bdb2538841b").build()
            )


        val response = client.toBlocking().exchange(request, RegistroChaveRequest::class.java)



        assertEquals(HttpStatus.CREATED, response.status)
//        assertEquals("/pix/${clienteId}/registra/${pixId}", response.header("Location"))


    }

    @Test
    fun naodeveCadastrarChavePixPoisJaExiste() {

        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
        val pixId = "5260263c-a3c1-4727-ae32-3bdb2538841b"

        val request = HttpRequest.POST("/pix/${clienteId}/registra", criaRequisicao())

        Mockito.`when`(grpcClient.cadastraChave(criaRequisicao().toGrpcRequest(clienteId))).thenThrow(Status.ALREADY_EXISTS.asRuntimeException())

        val response = Assertions.assertThrows(HttpClientResponseException::class.java){
         client.toBlocking().exchange(request, RegistroChaveRequest::class.java) }


        assertNotNull(response)
        assertEquals(HttpStatus.CONFLICT, response.status)


    }



    @Test
    fun naodeveCadastrarChavePixPoisClienteNaoExiste() {

        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
        val pixId = "5260263c-a3c1-4727-ae32-3bdb2538841b"

        val request = HttpRequest.POST("/pix/${clienteId}/registra", criaRequisicao())

        Mockito.`when`(grpcClient.cadastraChave(criaRequisicao().toGrpcRequest(clienteId))).thenThrow(Status.NOT_FOUND.asRuntimeException())

        val response = Assertions.assertThrows(HttpClientResponseException::class.java){
            client.toBlocking().exchange(request, RegistroChaveRequest::class.java) }


        assertNotNull(response)
        assertEquals(HttpStatus.NOT_FOUND, response.status)


    }

    fun criaRequisicao(): RegistroChaveRequest {
        return RegistroChaveRequest(
            TipoChave.TELEFONE,
            "34850850828",
            TipoConta.CONTA_CORRENTE
        )
    }

    @Singleton
    @Replaces(bean = PixRegistraServiceGrpc.PixRegistraServiceBlockingStub::class)
    fun `mockGrpc`() = Mockito.mock(PixRegistraServiceGrpc.PixRegistraServiceBlockingStub::class.java)


}