package com.webservices.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webservices.demo.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
