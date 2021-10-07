package br.com.zup.contadigital.conta

import br.com.zup.contadigital.compartilhado.validadores.ExistsById
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class TransacaoRequest (
        val idConta: Long,
        val valor: BigDecimal
        )
{

        var idCliente: String? = "Esse é o id do cliente"

        constructor(idCliente: String, idConta: Long, valor: BigDecimal) : this(idConta, valor){
                this.idCliente = idCliente
        }
}

//class Request(val idConta: Long, valor: BigDecimal) {
//        constructor(idClient: String, valor: BigDecimal, request: Request) : this(request.idConta, valor)
//}