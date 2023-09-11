package com.project.crudspring.services;

import com.project.crudspring.DTO.PostsDTO;
import com.project.crudspring.domain.Posts;
import com.project.crudspring.domain.User;
import com.project.crudspring.repositories.PostsRepository;
import com.project.crudspring.repositories.UserRepository;
import com.project.crudspring.utils.TokenService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsService implements Serializable{
    private static final long serialVersionUID = 1L;

    PostsRepository repository;
    ModelMapper mapper;
    UserRepository userRepository;

    @Autowired
    public PostsService(PostsRepository repository, ModelMapper mapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }




    public String getEmail(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get().getEmail();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");

    }



    public List<PostsDTO> getAllPosts() {
        List<Posts> posts = repository.findAll();
        return posts.stream()
                .map(post -> mapper.map(post, PostsDTO.class))
                .collect(Collectors.toList());
    }


    public Optional<PostsDTO> getPostById(Integer id) {
        Optional<Posts> post = repository.findById(id);
        return post.map(value -> mapper.map(value, PostsDTO.class));
    }


    public PostsDTO createPost(PostsDTO postDTO) {
        Posts post = mapper.map(postDTO, Posts.class);
        Posts savedPost = repository.save(post);
        return mapper.map(savedPost, PostsDTO.class);
    }


    public PostsDTO updatePost(Integer id, PostsDTO post) {
        Optional<Posts> existingPost = repository.findById(id);
        if (existingPost.isPresent()) {
            Posts updatedPost = mapper.map(post, Posts.class);
            updatedPost.setId(id);
            Posts savedPost = repository.save(updatedPost);
            return mapper.map(savedPost, PostsDTO.class);
        }
        return null;
    }


    public void deletePost(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado");
        }
    }


        public List<PostsDTO> getPostsByUserId(Integer id) {

            List<Posts> posts = repository.findAllByUserId(id);

            return posts.stream()
                    .map(post -> mapper.map(post, PostsDTO.class))
                    .collect(Collectors.toList());
        }
}
