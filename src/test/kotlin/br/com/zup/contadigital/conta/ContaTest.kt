package br.com.zup.contadigital.conta

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
internal class ContaTest(){

    companion object{
        val numeroConta: Long = 11211211
        val saldo = BigDecimal(100)
        val conta = Conta(saldo, numeroConta)
    }

    @Test
    fun `deve realizar credito quando valor for positivo`(){
        val valorPositivo = BigDecimal(50)
        val valorEsperado = valorPositivo + conta.saldo

        val credito = conta.credita(valorPositivo)

        assertTrue(credito)
        assertEquals(valorEsperado, conta.saldo)
    }

    @Test
    fun `nao deve realizar credito quando valor for negativo`(){
        val valorNegativo = BigDecimal(-1)
        val valorEsperado = conta.saldo

        val credito = conta.credita(valorNegativo)

        assertFalse(credito)
        assertEquals(valorEsperado, conta.saldo)
    }

    @Test
    fun `nao deve realizar credito quando valor for zero`(){
        val valorEsperado = conta.saldo
        val credito  = conta.credita(BigDecimal.ZERO)

        assertFalse(credito)
        assertEquals(valorEsperado, conta.saldo)
    }

    @Test
    fun `deve debitar quando o valor for positivo menor que o saldo e o idCliente for igual ao do dono da conta`(){
        val idCliente = conta.idCliente
        val valorPositivo = BigDecimal(50)
        val valorEsperado = conta.saldo - valorPositivo

        val debito = conta.debita(valorPositivo, idCliente)

        assertTrue(debito)
        assertEquals(valorEsperado, conta.saldo)
    }

    @Test
    fun `nao deve debitar quando o valor for maior que o saldo`(){
        val idCliente = conta.idCliente
        val valorMaior = conta.saldo + BigDecimal(1)
        val valorEsperado = conta.saldo

        val debito = conta.debita(valorMaior, idCliente)

        assertFalse(debito)
        assertEquals(valorEsperado, conta.saldo)
    }

    @Test
    fun `nao deve debitar quando o idCliente for diferente do dono da conta`(){
        val idCliente = "1111111111111111"
        val valorPositivo = BigDecimal(50)
        val valorEsperado = conta.saldo

        val debito = conta.debita(valorPositivo, idCliente)

        assertFalse(debito)
        assertEquals(valorEsperado, conta.saldo)
    }

    @Test
    fun `nao deve debitar quando o valor for negativo`(){
        val idCliente = conta.idCliente
        val valorNegativo = BigDecimal(-1)
        val valorEsperado = conta.saldo

        val debito = conta.debita(valorNegativo, idCliente)

        assertFalse(debito)
        assertEquals(valorEsperado, conta.saldo)
    }

    @Test
    fun `nao deve debitar quando o valor for zero`(){
        val idCliente = conta.idCliente
        val valorEsperado = conta.saldo

        val debito = conta.debita(BigDecimal.ZERO, idCliente)

        assertFalse(debito)
        assertEquals(valorEsperado, conta.saldo)
    }

}