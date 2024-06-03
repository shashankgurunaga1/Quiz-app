package com.quiz.QuizApp.controllers;

import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.QuizApp.models.Question;
import com.quiz.QuizApp.services.QuestionsServices;

@RestController
@RequestMapping("/questions")

public class QuestionControllers {
    @Autowired
    QuestionsServices question_service;

    @PostMapping("/create_question")
    public Question addQuestions(@RequestBody Question q) {
        System.out.println("question text in QuestionController " + q);
        return question_service.createQuestion(q);
    }

    @GetMapping("/getting_questions")
    public List<Question> receiveQuestions() {
        return question_service.getQuestions();
    }

    @PutMapping("/update_questions/{question_id}")
    public Question updatingQuestions(@PathVariable(value = "question_id") Integer question_id,
            @RequestBody Question q2) {
        System.out.println(" Inside Questioncontroller java update question" + q2);
        return question_service.updateQuestions(question_id, q2);
    }

    @DeleteMapping("/delete_questions/{question_id}")
    public void deleting_question(@PathVariable(value = "question_id") Integer question_id) {
        System.out.println("inside delete question ");
        question_service.deleteQuestions(question_id);
    }

}
