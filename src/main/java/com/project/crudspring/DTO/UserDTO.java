package com.project.crudspring.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {

    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String name;

    @NotBlank(message = "O e-mail é obrigatório")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    @Email(message = "O e-mail deve ser válido.")
    private String email;


    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO(Integer id,String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }
}
