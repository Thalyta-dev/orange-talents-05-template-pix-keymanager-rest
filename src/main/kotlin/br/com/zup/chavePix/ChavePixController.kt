package br.com.zup.chavePix

import br.com.zup.chavePix.consultaChavePix.*
import br.com.zup.chavePix.listaChavePIX.DetalhesChaveTodasChavePix
import br.com.zup.chavePix.registroChave.PixRegistraChaveResponseGrpc
import br.com.zup.chavePix.registroChave.RegistroChaveRequest
import br.com.zup.chavePix.registroChave.TipoChave
import br.com.zup.chavePix.registroChave.TipoConta

import br.com.zup.edu.shared.validation.ValidUUID
import com.zup.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
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

    @Inject
    lateinit var grpCPixConsulta: PixConsultaServiceGrpc.PixConsultaServiceBlockingStub

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
    ): HttpResponse<DetalhesChavePix> {

        grpCPixRemove.deletaChave(PixDeletaRequest.newBuilder().setPixId(pixId).setClientId(clienteId).build())
        return HttpResponse.status(HttpStatus.OK)

    }

    @Get("/consulta/{pixId}")
    fun consultaPixId(
        @ValidUUID @PathVariable clienteId: String,
        @ValidUUID @PathVariable pixId: String
    ): HttpResponse<DetalhesChavePix2> {

        val consulta = grpCPixConsulta.consulta(PixConsultaRequest.newBuilder().setPixId(pixId).setClientId(clienteId).build())

        val titular = TitularResponse2(consulta.titular.nome, consulta.titular.cpf)
        val conta = ContaResponse2(consulta.conta.nomeInstituicao, consulta.conta.agencia, consulta.conta.numero,TipoConta.valueOf(consulta.conta.tipoConta.toString()) )
        val detalhesChavePix = DetalhesChavePix2(consulta.clientId, consulta.pixId,TipoChave.valueOf(consulta.tipoChave.toString()), consulta.valorChave,titular,conta,
            consulta.criadoEm.let { LocalDateTime.ofInstant(
            Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC) })
        return HttpResponse.status<DetalhesChavePix>(HttpStatus.OK).body(detalhesChavePix)

    }



    @Get("/consulta")
    fun consultaTodasChaves(@ValidUUID @PathVariable clienteId: String): HttpResponse<List<DetalhesChaveTodasChavePix>> {

        val consulta = grpCPixConsulta.consultaTodasChavesCliente(PixConsultaChavesRequest.newBuilder().setClientId(clienteId).build())

        val detalhesChavePix =  consulta.chavesPixList.map { consulta ->
             DetalhesChaveTodasChavePix(consulta.clientId, consulta.clientId,TipoChave.valueOf(consulta.tipoChave.toString()), consulta.valorChave,consulta.criadoEm.let { LocalDateTime.ofInstant(
                Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC) })
        }

        return HttpResponse.status<DetalhesChavePix>(HttpStatus.OK).body(detalhesChavePix)

    }



}