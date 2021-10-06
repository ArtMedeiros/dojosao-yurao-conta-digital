package br.com.zup.contadigital.conta

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
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
        private val Saldo = BigDecimal(1000.00)
        val conta = Conta(Saldo, numeroConta)
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

        val valorEsperado = Saldo.add(transacaoRequest.valor)

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
                assertFalse(saldo == ContaControllerTest.Saldo)
                assertEquals(valorEsperado.setScale(2), saldo.setScale(2))
            }
    }

    @Test
    internal fun `deve retornar 422 caso o valor seja negativo`() {

        val contaRegistrada = repository.findAll()[0]
        val valor = BigDecimal(-100.00)
        val transacaoRequest = TransacaoRequest(contaRegistrada.id!!, valor)

        val valorEsperado = Saldo

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
                assertFalse(saldo == ContaControllerTest.Saldo)
                assertEquals(valorEsperado.setScale(2), saldo.setScale(2))
            }
    }
}