package br.com.zup.contadigital.conta

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
internal class ContaControllerTest(
    @Autowired val repository: ContaRepository,
    @Autowired val mockMvc: MockMvc
) {

    companion object {
        private const val numeroConta: Long = 11211211
        private val saldoInicial = BigDecimal(1000.00)
        val conta = Conta(saldoInicial, numeroConta)
    }

    @BeforeEach
    fun setup() {
        repository.deleteAll()
        repository.save(conta)
    }

    @Test
    fun `deve realizar credito com valor positivo e retornar 200`() {

        val contaRegistrada = repository.findAll()[0]
        val valor = BigDecimal(100.00)
        val transacaoRequest = TransacaoRequest(contaRegistrada.id!!, valor)

        val valorEsperado = saldoInicial.add(transacaoRequest.valor)

        mockMvc.perform(
            post("/api/conta/credita")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    ObjectMapper()
                        .writeValueAsString(transacaoRequest)
                )
        )
            .andExpect(status().isOk)

        repository.findById(contaRegistrada.id!!).get()
            .run {
                assertFalse(saldo == ContaControllerTest.saldoInicial)
                assertEquals(valorEsperado.setScale(2), saldo.setScale(2))
            }
    }

    @Test
    internal fun `deve retornar 422 caso o valor seja negativo`() {

        val contaRegistrada = repository.findAll()[0]
        val valor = BigDecimal(-100.00)
        val transacaoRequest = TransacaoRequest(contaRegistrada.id!!, valor)

        val valorEsperado = saldoInicial

        mockMvc.perform(
            post("/api/conta/credita")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    ObjectMapper()
                        .writeValueAsString(transacaoRequest)
                )
        )
            .andExpect(status().isUnprocessableEntity)

        repository.findById(contaRegistrada.id!!).get()
            .run {
                assertFalse(saldo == ContaControllerTest.saldoInicial)
                assertEquals(valorEsperado.setScale(2), saldo.setScale(2))
            }
    }

    @Test
    internal fun `deve realizar debito e retornar 200 com valor positivo menor ao saldo e idCliente igual ao do dono da conta`(){
        val contaRegistrada = repository.findAll()[0]
        val valorPositivo = conta.saldo - BigDecimal(1)
        val idCliente = contaRegistrada.idCliente
        val transacaoRequest = TransacaoRequest(idCliente, contaRegistrada.id!!, valorPositivo)

        val valorEsperado = conta.saldo.minus(valorPositivo)

        mockMvc.perform(
            post("/api/conta/debita")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    ObjectMapper().writeValueAsString(transacaoRequest)
                )
        ).andExpect(status().isOk)

        repository.findById(contaRegistrada.id!!).get()
            .run{
                assertFalse(saldo.setScale(2) == saldoInicial.setScale(2))
                assertEquals(valorEsperado.setScale(2), saldo.setScale(2))
            }
    }

    @Test
    internal fun `não deve realizar debito e retornar 422 quando valor for maior que o saldo`(){
        val contaRegistrada = repository.findAll()[0]
        val valorPositivo = conta.saldo + BigDecimal(1)
        val idCliente = contaRegistrada.idCliente
        val transacaoRequest = TransacaoRequest(idCliente, contaRegistrada.id!!, valorPositivo)

        val valorEsperado = conta.saldo

        mockMvc.perform(
            post("/api/conta/debita")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    ObjectMapper().writeValueAsString(transacaoRequest)
                )
        ).andExpect(status().isUnprocessableEntity)

        repository.findById(contaRegistrada.id!!).get()
            .run{
                assertTrue(saldo.setScale(2) == saldoInicial.setScale(2))
                assertEquals(valorEsperado.setScale(2), saldo.setScale(2))
            }
    }

    @Test
    internal fun `não deve realizar debito e retornar 422 quando valor for negativo`(){
        val contaRegistrada = repository.findAll()[0]
        val valorNegativo = BigDecimal(-1)
        val idCliente = contaRegistrada.idCliente
        val transacaoRequest = TransacaoRequest(idCliente, contaRegistrada.id!!, valorNegativo)

        val valorEsperado = conta.saldo

        mockMvc.perform(
            post("/api/conta/debita")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    ObjectMapper().writeValueAsString(transacaoRequest)
                )
        ).andExpect(status().isUnprocessableEntity)

        repository.findById(contaRegistrada.id!!).get()
            .run{
                assertTrue(saldo.setScale(2) == saldoInicial.setScale(2))
                assertEquals(valorEsperado.setScale(2), saldo.setScale(2))
            }
    }

    @Test
    internal fun `não deve realizar debito e retornar 422 quando idCliente for diferente`(){
        val contaRegistrada = repository.findAll()[0]
        val valorPositivo = conta.saldo + BigDecimal(1)
        val idCliente = "abcabcabcabcabc"
        val transacaoRequest = TransacaoRequest(idCliente, contaRegistrada.id!!, valorPositivo)

        val valorEsperado = conta.saldo

        mockMvc.perform(
            post("/api/conta/debita")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    ObjectMapper().writeValueAsString(transacaoRequest)
                )
        ).andExpect(status().isUnprocessableEntity)

        repository.findById(contaRegistrada.id!!).get()
            .run{
                assertTrue(saldo.setScale(2) == saldoInicial.setScale(2))
                assertEquals(valorEsperado.setScale(2), saldo.setScale(2))
            }
    }
}