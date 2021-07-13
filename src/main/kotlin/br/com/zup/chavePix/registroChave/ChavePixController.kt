package br.com.zup.chavePix.registroChave

import br.com.zup.edu.shared.validation.ValidUUID
import com.zup.PixDeletaRequest
import com.zup.PixDeletaServiceGrpc
import com.zup.PixRegistraServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid


@Controller("/pix/{clienteId}")
@Validated
class ChavePixController(


    ) {


    @Inject
    lateinit var grpPixCadastra: PixRegistraServiceGrpc.PixRegistraServiceBlockingStub

    @Inject
    lateinit var grpCPixRemove: PixDeletaServiceGrpc.PixDeletaServiceBlockingStub

    @Post("/registra")
    fun registraChavePix(
        @Body @Valid request: RegistroChaveRequest,
        @ValidUUID @PathVariable clienteId: String
    ): HttpResponse<Any> {

        grpPixCadastra.cadastraChave(request.toGrpcRequest(clienteId)).let { chaveResponse ->

            PixRegistraChaveResponseGrpc(chaveResponse.pixId, chaveResponse.clientId).run {

                val uri = UriBuilder.of("/pix/{clienteId}/registra/{id}")
                    .expand(mutableMapOf(Pair("clienteId", this.clientId), Pair("id", this.pixId)))
                return HttpResponse.created<Any?>(uri).body(this)

            }
        }
    }

    @Delete("/deleta/{pixId}")
    fun deletaChavepIX(
        @ValidUUID @PathVariable clienteId: String,
        @ValidUUID @PathVariable pixId: String
    ): HttpResponse<Any> {

        grpCPixRemove.deletaChave(PixDeletaRequest.newBuilder().setPixId(pixId).setClientId(clienteId).build())
        return HttpResponse.status(HttpStatus.OK)

    }


}