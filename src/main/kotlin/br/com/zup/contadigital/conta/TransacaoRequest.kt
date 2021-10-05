package br.com.zup.contadigital.conta

import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class TransacaoRequest (
        @field:Positive @field:NotNull val valor: BigDecimal,
        val idCliente: Long,

        )