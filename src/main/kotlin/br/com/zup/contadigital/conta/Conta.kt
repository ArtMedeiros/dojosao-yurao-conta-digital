package br.com.zup.contadigital.conta

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
class Conta(
    var saldo: BigDecimal,
    val numeroConta: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    private val idCliente: String = UUID.randomUUID().toString()

    // Lock Otimista - impede que seja feito requisições repetidas
    @Version
    val versao: Long = 0

    fun credita(valor: BigDecimal): Boolean{
        return if(valor <= BigDecimal.ZERO){
            false
        } else {
            this.saldo += valor
            true
        }
    }

    fun debita(valor: BigDecimal, idCliente: String?): Boolean {
        return if (valor <= BigDecimal.ZERO || valor > saldo || this.idCliente != idCliente) {
            false
        } else {
            this.saldo -= valor
            true
        }
    }
}