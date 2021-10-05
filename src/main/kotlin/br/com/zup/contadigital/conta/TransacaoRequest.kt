package br.com.zup.contadigital.conta

import br.com.zup.contadigital.compartilhado.validadores.ExistsById
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class TransacaoRequest (
        @field:ExistsById(fieldName = "id", entityClass = Conta::class) val idConta: Long,
        @field:Positive @field:NotNull val valor: BigDecimal,
        val idCliente: String,
        )