package com.project.crudspring.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String image;
    @JsonIgnore
    private String password;

}
