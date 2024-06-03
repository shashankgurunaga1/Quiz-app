package com.quiz.QuizApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.QuizApp.models.Question;

@Repository
public interface QuestionsRepository extends JpaRepository<Question,Integer> {
    
}
