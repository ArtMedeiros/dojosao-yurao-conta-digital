package br.com.zup.contadigital.conta

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.math.BigDecimal
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller
@RequestMapping("/api/conta")
class ContaController(private val contaRepository: ContaRepository) {

    @Transactional
    @PostMapping("/credita")
    fun credita(@Valid @RequestBody request: TransacaoRequest): ResponseEntity<Any> {
        val conta = contaRepository.findById(request.idConta).get()
        val credita = conta.credita(request.valor)

        if (!credita) {
            return ResponseEntity.unprocessableEntity().body(("Transação não efetuada"))
        }

        return ResponseEntity.ok().build()
    }

    @Transactional
    @PostMapping("/debita")
    fun debita(@RequestBody @Valid request: TransacaoRequest): ResponseEntity<Any> {
        val conta = contaRepository.findById(request.idConta).get()
        val debita = conta.debita(request.valor, request.idClient)

        if (!debita) {
            return ResponseEntity.unprocessableEntity().body("Transação não efetuada")
        }

        return ResponseEntity.ok().build()
    }

    // Temporário
    @Transactional
    @GetMapping("/criaconta")
    fun criaConta(): ResponseEntity<Any>{
        val conta: Conta = Conta(saldo = BigDecimal(2500), numeroConta = 123456 )
        return ResponseEntity.ok().build()
    }


}