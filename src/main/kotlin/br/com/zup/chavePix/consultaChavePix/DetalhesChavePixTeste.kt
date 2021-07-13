package br.com.zup.chavePix.consultaChavePix

import br.com.zup.chavePix.registroChave.TipoChave
import br.com.zup.chavePix.registroChave.TipoConta
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime

@Introspected
class DetalhesChavePix2(
    val clienteId: String,
    val pixIs: String,
    val tipoChave: TipoChave,
    val valorChave: String,
    val titular: TitularResponse2,
    val conta: ContaResponse2,
    val criadoEm: LocalDateTime
){}


@Introspected
class TitularResponse2(
    val nome: String,
    val cpf: String
) {}

@Introspected
data  class ContaResponse2(
    val instituicao: String,
    val agencia: String,
    val numero: String,
    val tipoConta: TipoConta
){}
