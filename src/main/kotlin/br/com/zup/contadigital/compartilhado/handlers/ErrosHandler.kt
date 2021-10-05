package br.com.zup.contadigital.compartilhado.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrosHandler() {
    @Autowired
    lateinit var messageSource: MessageSource

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<Any>  {
        var dto: MutableList<ErrorsDto> = mutableListOf()
        var fieldErrors: MutableList<FieldError> = ex.bindingResult.fieldErrors

        fieldErrors.forEach { e ->
            val msg: String = messageSource.getMessage(e, LocaleContextHolder.getLocale())
            val erro = ErrorsDto(e.field, msg)
            dto.run {
                add(erro)
            }
        }

        return ResponseEntity.badRequest().body(dto)
    }
}