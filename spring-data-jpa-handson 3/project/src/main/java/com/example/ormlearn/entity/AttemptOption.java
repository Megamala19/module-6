package com.example.ormlearn.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attempt_option")
public class AttemptOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "attempt_question_id")
    private AttemptQuestion attemptQuestion;

    // The option the user actually selected for this attempt question.
    @ManyToOne
    @JoinColumn(name = "option_id")
    private Options selectedOption;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AttemptQuestion getAttemptQuestion() {
        return attemptQuestion;
    }

    public void setAttemptQuestion(AttemptQuestion attemptQuestion) {
        this.attemptQuestion = attemptQuestion;
    }

    public Options getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Options selectedOption) {
        this.selectedOption = selectedOption;
    }
}
