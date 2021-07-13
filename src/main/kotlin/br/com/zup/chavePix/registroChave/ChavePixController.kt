package br.com.zup.chavePix.registroChave

import br.com.zup.edu.shared.validation.ValidUUID
import com.zup.PixRegistraServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.validation.Valid


@Controller
@Validated
class ChavePixController(

    val grpCPix: PixRegistraServiceGrpc.PixRegistraServiceBlockingStub,
) {

    @Post("/pix/{clienteId}/registra")
    fun registraChavePix(@Body @Valid request: RegistroChaveRequest, @ValidUUID @PathVariable clienteId: String):HttpResponse<Any>{

      grpCPix.cadastraChave( request.toGrpcRequest(clienteId)).let {chaveResponse ->

            PixRegistraChaveResponseGrpc(chaveResponse.pixId, chaveResponse.clientId).run{

                val uri = UriBuilder.of("/pix/{clienteId}/registra/{id}").expand(mutableMapOf(Pair("clienteId", this.clientId), Pair("id", this.pixId)))
                return HttpResponse.created(uri)

            }
        }
    }
}