package br.com.zup.contadigital.conta

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
internal class ContaTest(){

    companion object{
        val conta = Conta(BigDecimal(100), 11111111)
    }

    @BeforeEach
    fun setup(){

    }


    @Test
    fun `deve creditar o valor`(){

        val credito = conta.credita(BigDecimal(50))

        assertTrue(credito)
        assertEquals(BigDecimal(150), conta.saldo)
    }

    @Test
    fun `nao deve realizar credito com valor negativo`(){
        //val conta = Conta
    }




}