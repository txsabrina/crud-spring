package com.project.crudspring.DTO;

import com.project.crudspring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Integer id;
    private String title;
    private String content;
    private User userId;
    private LocalDateTime published;
    private LocalDateTime updated;
}
