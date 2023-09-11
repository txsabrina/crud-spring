package com.project.crudspring.DTO;

import com.project.crudspring.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsDTO {

    private Integer id;

    @NotBlank(message = "Digite um título")
    private String title;

    @NotBlank(message = "Digite um texto")
    private String content;

    private LocalDateTime published;
    private LocalDateTime updated;

    private UserDTO userId;
}
