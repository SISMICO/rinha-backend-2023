package br.com.sismico.rinha.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import java.util.Calendar
import java.util.UUID

@Entity
data class Pessoa(

    @Id var id: UUID?,

    @field:NotBlank(message = "Apelido precisa ser preenchido") @field:NotNull @field:Length(max = 5) @Column(unique = true) var apelido: String?,

    @field:NotBlank(message = "Nome precisa ser preenchido") @field:NotNull @field:Length(max = 100) var nome: String?, var nascimento: Calendar,

    var stack: List<@Length(max = 32, message = "Stack pode ter no máximo 32 caracteres") String>,

    @JsonIgnore var termos: String?
)
