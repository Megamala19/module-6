package com.example.ormlearn.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "option_text")
    private String optionText;

    // Score awarded if this option is picked, e.g. 1.0 for the fully
    // correct option, 0.5 for a partially correct one, 0.0 otherwise.
    @Column(name = "score")
    private double score;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isCorrect() {
        return score >= 1.0;
    }

    @Override
    public String toString() {
        return optionText + "\t" + score;
    }
}
