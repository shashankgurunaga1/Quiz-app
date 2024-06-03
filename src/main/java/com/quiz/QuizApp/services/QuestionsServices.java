package com.quiz.QuizApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.QuizApp.models.Question;
import com.quiz.QuizApp.repositories.QuestionsRepository;

@Service
public class QuestionsServices {
    @Autowired
    QuestionsRepository quest_repo;

    public Question createQuestion(Question q) {
        return quest_repo.save(q);
    }

    public List<Question> getQuestions() {
        return quest_repo.findAll();
    }

    public Question updateQuestions(Integer question_id, Question q) {
        System.out.println("Inside  question services and the question body is" + q);
        Question query = quest_repo.findById(question_id).get();
      //  query.setQuestion_id(q.getQuestion_id());
        query.setQuestion_text(q.getQuestion_text());
        query.setMarks(q.getMarks());
        query.setQuiz_id(q.getQuiz_id());
        query.setAnswer(q.getAnswer());
        query.setOption1(q.getOption1());
        query.setOption2(q.getOption2());
        query.setOption3(q.getOption3());



        return quest_repo.save(query);
    }

    public void deleteQuestions(Integer question_id) {
        quest_repo.deleteById(question_id);
    }

}
