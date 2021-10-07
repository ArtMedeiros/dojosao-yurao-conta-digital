package br.com.zup.contadigital.conta

import java.math.BigDecimal

data class TransacaoRequest(
    val idConta: Long,
    val valor: BigDecimal
) {

    var idCliente: String? = "Esse é o id do cliente"

    constructor(idCliente: String, idConta: Long, valor: BigDecimal) : this(idConta, valor) {
        this.idCliente = idCliente
    }
}