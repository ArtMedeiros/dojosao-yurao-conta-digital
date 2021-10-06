package br.com.zup.contadigital.conta

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest()
internal class ContaControllerTest(@Autowired val repository: ContaRepository){

    @Test
    fun `deve realizar credito com valor positivo e retornar 200`(){

    }

}