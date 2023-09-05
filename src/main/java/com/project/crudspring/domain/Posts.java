package com.project.crudspring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
@AllArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "Digite um título")
    private String title;

    @NotBlank(message = "Digite um texto")
    private String content;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime published;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Posts() {
        this.published = LocalDateTime.now();
    }

    public Posts(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.published = LocalDateTime.now();
        this.user = user;
    }
}