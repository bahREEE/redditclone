package com.example.reddit.repositories;

import com.example.reddit.entities.Subreddit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long>{
    
}
