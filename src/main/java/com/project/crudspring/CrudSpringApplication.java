package com.project.crudspring;

import com.project.crudspring.domain.Posts;
import com.project.crudspring.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	public void run(String... args) throws Exception {
		User user1 = new User("User 1", "user1@email.com", "123456789");

		Posts post1 = new Posts("Post 1", "Body 1", user1);
		Posts post2 = new Posts("Post 2", "Body 2", user1);
		Posts post3 = new Posts("Post 3", "Body 3", user1);
	}

}
