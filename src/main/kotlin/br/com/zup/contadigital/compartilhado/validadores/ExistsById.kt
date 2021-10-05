package br.com.zup.contadigital.compartilhado.validadores

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FIELD
//    ,AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsByIdValidator::class])
@MustBeDocumented
annotation class ExistsById(
    val message: String = "ID não encontrado.",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
    val fieldName: String,
    val entityClass: KClass<*>
)
