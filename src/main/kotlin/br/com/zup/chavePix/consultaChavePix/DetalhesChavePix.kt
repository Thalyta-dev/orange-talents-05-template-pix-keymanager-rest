package br.com.zup.chavePix.consultaChavePix

import br.com.zup.chavePix.registroChave.TipoConta
import br.com.zup.chavePix.registroChave.TipoChave

import com.zup.*
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
  class DetalhesChavePix(
    val chavePix: PixConsultaResponse
) {
    val clienteId = chavePix.clientId
    val pixIs = chavePix.pixId
//    val tipoChave: TipoChave = TipoChave.valueOf(chavePix.tipoChave.toString())
    val valorChave = chavePix.valorChave
    val titular = TitularResponse(chavePix.titular)
    val createAt = chavePix.criadoEm.let {LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)}
    val conta = ContaResponse(chavePix.conta)

}

@Introspected
 class TitularResponse(val titular: Titular) {
    val nome = titular.nome
    val cpf = titular.cpf

}

@Introspected
 class ContaResponse(val conta: Conta) {

    val instituicao = conta.nomeInstituicao
    val agencia = conta.agencia
    val numero = conta.numero
//    val tipoConta = TipoConta.valueOf(conta.tipoConta.toString())

}
