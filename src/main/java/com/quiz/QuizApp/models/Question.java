package com.quiz.QuizApp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Data
@Builder
@Entity
@Table(name = "Questions")

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QUESTION_ID")
    public Integer question_id;

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Integer getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Integer quiz_id) {
        this.quiz_id = quiz_id;
    }

    @Column(name = "TEXT1")
    private String question_text;

    @Column(name = "MARKS")
    private Integer marks;

    @Column(name = "QUIZ_ID")
    private Integer quiz_id;

    @Column(name = "Answer")
    private String answer;

    @Column(name = "option1")
    private String option1;

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    @Column(name = "option2")
    private String option2;

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    @Column(name = "option3")
    private String option3;
    
    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
