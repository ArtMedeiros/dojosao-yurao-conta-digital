package br.com.zup.contadigital.conta

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Conta(
    var saldo: BigDecimal,
    val numeroConta: Long,
    val idCliente: Long?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    // Lock Otimista - impede que seja feito requisições repetidas
    @Version
    val versao: Long = 0

    fun credita(valor: BigDecimal): Boolean {
        return if (valor <= BigDecimal.ZERO) {
            false
        } else {
            this.saldo += valor
            true
        }
    }

    fun debita(valor: BigDecimal, idCliente: Long?): Boolean {
        println(idCliente)
        return if (valor <= BigDecimal.ZERO || valor > saldo || this.idCliente != idCliente) {
            false
        } else {
            this.saldo -= valor
            true
        }
    }

    fun consultaSaldo(valor: Long): Boolean {
        return this.saldo >= BigDecimal(valor)
    }
}