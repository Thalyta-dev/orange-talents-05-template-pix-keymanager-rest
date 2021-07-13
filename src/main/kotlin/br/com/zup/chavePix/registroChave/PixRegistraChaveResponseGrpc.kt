package br.com.zup.chavePix.registroChave

import br.com.zup.edu.shared.validation.ValidUUID
import io.micronaut.core.annotation.Introspected

@Introspected
class PixRegistraChaveResponseGrpc(
    @field: ValidUUID val pixId: String,
    @field: ValidUUID val clientId: String

){

}