package br.com.zup.contadigital.conta

import java.math.BigDecimal

data class TransacaoRequest(
    val idConta: Long,
    val valor: BigDecimal
) {

    var idCliente: Long? = null

    constructor(idCliente: Long, idConta: Long, valor: BigDecimal) : this(idConta, valor) {
        this.idCliente = idCliente
    }
}