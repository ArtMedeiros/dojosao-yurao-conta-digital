package br.com.zup.contadigital.conta

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
class Conta (
        val saldo: BigDecimal,
        val numeroConta: Long,
        val idCliente: String = UUID.randomUUID().toString()
        )
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null

        // Lock Otimista - impede que seja feito requisições repetidas
        @Version
        val versao: Long = 0
}