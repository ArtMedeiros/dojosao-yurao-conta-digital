package br.com.zup.contadigital.conta

import br.com.zup.contadigital.compartilhado.validadores.ExistsById
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class TransacaoRequest (
        val idConta: Long,
        val valor: BigDecimal
        )
{

        val idClient: String
                get() {
                        TODO()
                }

//        constructor(idClient: String, valor: BigDecimal, request: TransacaoRequest) : this(request.idConta, valor)
}

//class Request(val idConta: Long, valor: BigDecimal) {
//        constructor(idClient: String, valor: BigDecimal, request: Request) : this(request.idConta, valor)
//}