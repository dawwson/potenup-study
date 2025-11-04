package com.ohgiraffers.jpql.chap01.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;
    private String title;
    private String description;

    @Column(name = "instructor_id")
    private int instructorId;
    private double price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Lesson> lessons = new ArrayList<>();

    protected Course() {}

    public Course(String title, String description, int instructorId, double price, LocalDateTime createdAt, List<Lesson> lessons) {
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.price = price;
        this.createdAt = createdAt;
        this.lessons = lessons;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructorId=" + instructorId +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", lessons=" + lessons +
                '}';
    }
}
