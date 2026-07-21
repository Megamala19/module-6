package com.example.ormlearn.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "question_text")
    private String questionText;

    @OneToMany(mappedBy = "question")
    private List<Options> optionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Options> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Options> optionList) {
        this.optionList = optionList;
    }

    @Override
    public String toString() {
        return questionText;
    }
}
