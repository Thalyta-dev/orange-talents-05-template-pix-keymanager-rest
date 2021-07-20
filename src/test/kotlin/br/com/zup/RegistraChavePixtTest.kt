//package br.com.zup
//
//import br.com.zup.chavePix.registroChave.RegistroChaveRequest
//import br.com.zup.chavePix.registroChave.TipoChave
//import br.com.zup.chavePix.registroChave.TipoConta
//import com.zup.PixRegistraResponse
//import com.zup.PixRegistraServiceGrpc
//import io.grpc.Status
//import io.grpc.StatusRuntimeException
//import io.micronaut.context.annotation.Replaces
//import io.micronaut.http.HttpRequest
//import io.micronaut.http.HttpStatus
//import io.micronaut.http.client.HttpClient
//import io.micronaut.http.client.annotation.Client
//import io.micronaut.http.client.exceptions.HttpClientResponseException
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertNotNull
//import org.junit.jupiter.api.Test
//import org.mockito.Mockito
//import org.mockito.Mockito.any
//import org.mockito.Mockito.mock
//import javax.inject.Inject
//import javax.inject.Singleton
//
//
//@MicronautTest
//internal class RegistraChavePixtTest {
//
//    @Inject
//    lateinit var grpcClient: PixRegistraServiceGrpc.PixRegistraServiceBlockingStub
//
//    @field:Inject
//    @field:Client("/")
//    lateinit var client: HttpClient
//
//    @Test
//    fun deveCadastrarChavePix() {
//
//        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
//        val pixId = "5260263c-a3c1-4727-ae32-3bdb2538841b"
//
//        Mockito.`when`(grpcClient.cadastraChave(any()))
//            .thenReturn(
//                PixRegistraResponse.newBuilder()
//                    .setPixId(pixId)
//                    .setClientId(clienteId).build()
//            )
//        val request = HttpRequest.POST("/pix/${clienteId}/registra", criaRequisicao())
//        val response = client.toBlocking().exchange(request, RegistroChaveRequest::class.java)
//        assertEquals(HttpStatus.CREATED, response.status)
//        assertEquals("/pix/${clienteId}/registra/${pixId}", response.header("Location"))
//
//    }
//
//    @Test
//    fun naodeveCadastrarChavePixPoisJaExiste() {
//
//        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
//
//        val request = HttpRequest.POST("/pix/${clienteId}/registra", criaRequisicao())
//
//        Mockito.`when`(grpcClient.cadastraChave(any())).thenThrow(StatusRuntimeException(Status.ALREADY_EXISTS))
//
//        val response = Assertions.assertThrows(HttpClientResponseException::class.java){
//            client.toBlocking().exchange(request, RegistroChaveRequest::class.java) }
//
//
//        assertNotNull(response)
//        assertEquals(HttpStatus.CONFLICT, response.status)
//
//
//    }
//
//    @Test
//    fun naodeveCadastrarChavePixPoisClienteNaoExiste() {
//
//        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
//        val pixId = "5260263c-a3c1-4727-ae32-3bdb2538841b"
//
//        val request = HttpRequest.POST("/pix/${clienteId}/registra", criaRequisicao())
//
//        Mockito.`when`(grpcClient.cadastraChave(any())).thenThrow(StatusRuntimeException(
//            Status.NOT_FOUND))
//
//        val response = Assertions.assertThrows(HttpClientResponseException::class.java){
//            client.toBlocking().exchange(request, RegistroChaveRequest::class.java) }
//
//
//        assertNotNull(response)
//        assertEquals(HttpStatus.NOT_FOUND, response.status)
//
//
//    }
//
//    fun criaRequisicao(): RegistroChaveRequest {
//        return RegistroChaveRequest(
//            TipoChave.TELEFONE,
//            "34850850828",
//            TipoConta.CONTA_CORRENTE
//        )
//    }
//
//    @Singleton
//    @Replaces(bean = PixRegistraServiceGrpc.PixRegistraServiceBlockingStub::class)
//    fun registraStub()= mock(PixRegistraServiceGrpc.PixRegistraServiceBlockingStub::class.java)
//}