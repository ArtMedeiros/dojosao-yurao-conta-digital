package br.com.zup.contadigital.compartilhado.validadores

import javax.persistence.EntityManager
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

class ExistsByIdValidator (private val manager: EntityManager) : ConstraintValidator<ExistsById, Any> {

    private var fieldName: String? = null
    private var entityClass: KClass<*>? = null

    override fun initialize(constraintAnnotation: ExistsById?) {
        this.entityClass = constraintAnnotation?.entityClass
        this.fieldName = constraintAnnotation?.fieldName
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return true

        val jpql = "SELECT 1 FROM ${entityClass?.simpleName} e WHERE e.$fieldName = :value"
        val entities = manager
            .createQuery(jpql, Integer::class.java)
            .setParameter("value", value)
            .resultList

        return entities.isNotEmpty()
    }
}