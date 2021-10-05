package br.com.zup.contadigital.conta

import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Validated
@Controller
@RequestMapping("/api/conta")
class ContaController {

    @PostMapping("/credita/{id}")
    fun credita(@PathVariable idConta: Long, @Valid @RequestBody request: TransacaoRequest){

    }

    @PostMapping("/debita/{idConta}")
    fun debita(@PathVariable idConta: Long, @RequestBody @Valid request: TransacaoRequest){

    }

}