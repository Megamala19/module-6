package com.example.ormlearn.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "attempt")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "attempted_date")
    private LocalDateTime attemptedDate;

    @OneToMany(mappedBy = "attempt")
    private List<AttemptQuestion> attemptQuestionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getAttemptedDate() {
        return attemptedDate;
    }

    public void setAttemptedDate(LocalDateTime attemptedDate) {
        this.attemptedDate = attemptedDate;
    }

    public List<AttemptQuestion> getAttemptQuestionList() {
        return attemptQuestionList;
    }

    public void setAttemptQuestionList(List<AttemptQuestion> attemptQuestionList) {
        this.attemptQuestionList = attemptQuestionList;
    }
}
