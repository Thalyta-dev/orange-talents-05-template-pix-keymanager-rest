package br.com.zup.chavePix.registroChave

import br.com.zup.edu.shared.validation.ValidUUID
import com.zup.PixRegistraRequest
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull


@Introspected
data class RegistroChaveRequest(

    @field: NotNull val tipoChave: TipoChave,
    val valorChave: String,
    @field: NotNull val tipoConta: TipoConta

    ) {


    fun toGrpcRequest(clienteId: String): PixRegistraRequest {

        return PixRegistraRequest.newBuilder()
            .setIdCliente(clienteId)
            .setTipoChave(com.zup.TipoChave.valueOf(tipoChave.toString()))
            .setValorChave(valorChave)
            .setTipoConta(com.zup.TipoConta.valueOf(tipoConta.toString())).build()
    }
}

enum class TipoChave {

    EMAIL {
        override fun validaChave(chave: String): Boolean {
            return chave.matches("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$".toRegex())
        }

    },
    TELEFONE {
        override fun validaChave(chave: String): Boolean {

            return chave.matches("^[0-9]{11}\$".toRegex())

        }

    },
    CPF {
        override fun validaChave(chave: String): Boolean {

            return chave.matches("^([0-9]{3}+-[0-9]{3}+-[0-9]{3}+.[0-9]{2})\$".toRegex())
        }

    },
    ALEATORIA {
        override fun validaChave(chave: String): Boolean {
            return true
        }


    };

    abstract fun validaChave(chave: String): Boolean

}

enum class TipoConta {
    CONTA_CORRENTE ,
    CONTA_POUPANCA

}
