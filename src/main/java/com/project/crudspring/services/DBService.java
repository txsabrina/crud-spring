package com.project.crudspring.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.crudspring.domain.Posts;
import com.project.crudspring.domain.User;
import com.project.crudspring.repositories.PostsRepository;
import com.project.crudspring.repositories.UserRepository;

@Service
public class DBService {
  
	@Autowired
	private PostsRepository postsRepository;
	@Autowired
	private UserRepository userRepository;

    
    public void instanciaBaseDeDados() {
		User user1 = new User("User 1", "user1@email.com", "123456789");
		User user2 = new User("User 2", "user2@email.com", "987654321");

		Posts post1 = new Posts("Post 1", "Body 1", user1);
		Posts post2 = new Posts("Post 2", "Body 2", user1);
		Posts post3 = new Posts("Post 3", "Body 3", user1);

		Posts post4 = new Posts("Post 4", "Body 4", user2);
		Posts post5 = new Posts("Post 5", "Body 5", user2);

		user1.getPosts().addAll(Arrays.asList(post1, post2, post3));
		user2.getPosts().addAll(Arrays.asList(post4, post5));

		userRepository.saveAll(Arrays.asList(user1, user2));
		postsRepository.saveAll(Arrays.asList(post1, post2, post3, post4, post5));

    }  
}
