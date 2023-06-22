package com.example.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
