package com.example.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Complaint {
    private int id;
    private String name;
    private String description;
    private String status;

    public Complaint(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
