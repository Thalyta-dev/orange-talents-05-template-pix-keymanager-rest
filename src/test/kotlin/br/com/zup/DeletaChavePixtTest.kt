//package br.com.zup
//
//import com.zup.PixDeletaRequest
//import com.zup.PixDeletaResponse
//import com.zup.PixDeletaServiceGrpc
//import io.grpc.Status
//import io.micronaut.context.annotation.Factory
//import io.micronaut.context.annotation.Replaces
//import io.micronaut.http.HttpRequest
//import io.micronaut.http.HttpStatus
//import io.micronaut.http.client.HttpClient
//import io.micronaut.http.client.annotation.Client
//import io.micronaut.http.client.exceptions.HttpClientResponseException
//import io.micronaut.test.annotation.TransactionMode
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import org.mockito.Mockito
//import javax.inject.Inject
//import javax.inject.Singleton
//
//
//@MicronautTest(
//    transactional = false,
//    rollback = false,
//    transactionMode = TransactionMode.SINGLE_TRANSACTION
//)
//class DeletaChavePixtTest {
//
//    @Inject
//    lateinit var grpcClient: PixDeletaServiceGrpc.PixDeletaServiceBlockingStub
//
//    @field:Inject
//    @field:Client("/")
//    lateinit var client: HttpClient
//
//
//    @Test
//    fun deveCadastrarChavePix() {
//
//        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
//        val pixId = "5260263c-a3c1-4727-ae32-3bdb2538841b"
//
//        val request = HttpRequest.DELETE("/pix/${clienteId}/deleta/${pixId}", clienteId)
//
//        Mockito.`when`(grpcClient.deletaChave(PixDeletaRequest.newBuilder().setClientId(clienteId).setPixId(pixId).build()))
//            .thenReturn(
//                PixDeletaResponse.newBuilder()
//                    .setPixId("5260263c-a3c1-4727-ae32-3bdb25388412").build()
//            )
//
//
//        val response = client.toBlocking().exchange(request, Any::class.java)
//
//
//
//        assertEquals(HttpStatus.OK, response.status)
////        assertEquals("/pix/${clienteId}/registra/${pixId}", response.header("Location"))
//
//
//    }
//    @Test
//    fun naoDeveCadastrarChavePixPoisChaveJaFoiRemovida() {
//
//        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
//        val pixId = "5260263c-a3c1-4727-ae32-3bdb2538841b"
//
//        val request = HttpRequest.DELETE("/pix/${clienteId}/deleta/${pixId}", clienteId)
//
//        Mockito.`when`(grpcClient.deletaChave(PixDeletaRequest.newBuilder().setClientId(clienteId).setPixId(pixId).build())).thenThrow(Status.NOT_FOUND.asRuntimeException())
//
//
//        val response = Assertions.assertThrows(HttpClientResponseException::class.java){
//            client.toBlocking().exchange(request, Any::class.java)}
//
//
//        assertEquals(HttpStatus.NOT_FOUND, response.status)
////        assertEquals("/pix/${clienteId}/registra/${pixId}", response.header("Location"))
//
//
//    }
//
//    @Test
//    fun naoDeveCadastrarChavePixPoisClienteNaoExiste() {
//
//        val clienteId = "5260263c-a3c1-4727-ae32-3bdb25388412"
//        val pixId = "5260263c-a3c1-4727-ae32-3bdb2538841b"
//
//        val request = HttpRequest.DELETE("/pix/${clienteId}/deleta/${pixId}", clienteId)
//
//        Mockito.`when`(grpcClient.deletaChave(PixDeletaRequest.newBuilder().setClientId(clienteId).setPixId(pixId).build())).thenThrow(Status.NOT_FOUND.asRuntimeException())
//
//
//        val response = Assertions.assertThrows(HttpClientResponseException::class.java){
//            client.toBlocking().exchange(request, Any::class.java)}
//
//
//        assertEquals(HttpStatus.NOT_FOUND, response.status)
////        assertEquals("/pix/${clienteId}/registra/${pixId}", response.header("Location"))
//
//
//    }
//
//
//
//
//}