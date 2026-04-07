package com.example.services;

import com.example.entity.Complaint;
import com.example.exception.ComplaintNotFoundException;

import java.util.List;

public interface ComplaintService {
    void addComplaint(Complaint complaint);

    Complaint findById(int id) throws ComplaintNotFoundException;

    List<Complaint> getAllComplaints();

    void updateComplaint(int id, String description, String status) throws ComplaintNotFoundException;

    void deleteComplaint(int id) throws ComplaintNotFoundException;

    void updateStatusUsingProcedure(int id, String description, String status);
}
