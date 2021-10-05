package br.com.zup.contadigital.conta

import org.springframework.data.jpa.repository.JpaRepository

interface ContaRepository: JpaRepository<Conta, Long> {
}