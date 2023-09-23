package br.com.sismico.rinha.repository

import br.com.sismico.rinha.entity.Pessoa
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PessoaRepository: CrudRepository<Pessoa, UUID> {
    fun findTop50ByTermosContainingIgnoreCase(termo: String): List<Pessoa>
    fun findByApelido(apelido: String): Pessoa?
}
