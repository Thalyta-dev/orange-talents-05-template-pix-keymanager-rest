package com.zup.annotationValidacoes

import br.com.zup.chavePix.registroChave.RegistroChaveRequest
import br.com.zup.chavePix.registroChave.TipoChave

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton

@Singleton
class ChavePixValidador: ConstraintValidator<ValidChavePix,RegistroChaveRequest>{


    override fun isValid(
        value: RegistroChaveRequest,
        annotationMetadata: AnnotationValue<ValidChavePix>,
        context: ConstraintValidatorContext
    ): Boolean {

        if(value.tipoChave == TipoChave.ALEATORIA) return true

        return value.tipoChave.validaChave(value.valorChave!!)
    }
}
