package com.example.reddit.repositories;

import com.example.reddit.entities.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
    
}
