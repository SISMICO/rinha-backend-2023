package br.com.sismico.rinha.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class PessoaAlreadyExistsException : Exception("Pessoa já existe")
