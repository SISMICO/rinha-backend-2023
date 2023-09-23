package br.com.sismico.rinha.controller

import br.com.sismico.rinha.entity.Pessoa
import br.com.sismico.rinha.exceptions.PessoaAlreadyExistsException
import br.com.sismico.rinha.repository.PessoaRepository
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriBuilder
import java.util.UUID
import java.util.function.Consumer

@RestController
class RinhaController(
    val repository: PessoaRepository
) {
    @GetMapping("/contagem-pessoas")
    fun getContagem(): Long {
        return repository.count()
    }

    @PostMapping("/pessoas")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPessoa(@Valid @RequestBody pessoa: Pessoa, response: HttpServletResponse): Pessoa {
        if (repository.findByApelido(pessoa.apelido!!) !== null) {
            throw PessoaAlreadyExistsException()
        }
        pessoa.id = UUID.randomUUID()
        pessoa.termos = (pessoa.apelido + pessoa.nome + pessoa.stack.joinToString(separator = "")).lowercase()
        repository.save(pessoa)

        response.addHeader(HttpHeaders.LOCATION, "/pessoas/${pessoa.id}")
        return pessoa
    }

    @GetMapping("/pessoas/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getPessoa(@PathVariable id: UUID): Pessoa {
        return repository.findByIdOrNull(id) ?: throw NotFoundException()
    }

    @GetMapping("/pessoas")
    fun getPessoa(@RequestParam t: String): List<Pessoa> {
        return repository.findTop50ByTermosContainingIgnoreCase(t.lowercase())
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): Map<String, String?> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return errors
    }
}
