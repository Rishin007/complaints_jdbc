package com.example;

import com.example.entity.Complaint;
import com.example.exception.ComplaintNotFoundException;
import com.example.services.ComplaintService;
import com.example.services.impl.ComplaintServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ComplaintDashboard extends JFrame {
    ComplaintService service = new ComplaintServiceImpl();
    JTextField nameField, descField, statusField, idField;
    JTextArea displayArea;

    public ComplaintDashboard() {
        setTitle("Complaint Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel (Form)
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        descField = new JTextField();
        panel.add(descField);
        panel.add(new JLabel("Status:"));
        statusField = new JTextField();
        panel.add(statusField);
        add(panel, BorderLayout.NORTH);
        // Center Display
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        // Buttons Panel
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton procBtn = new JButton("Update via Procedure");
        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(procBtn);
        add(btnPanel, BorderLayout.SOUTH);
        // Load Dummy Data
        // loadDummyData();

        // ================= EVENTS =================
        // Add Complaint
        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String desc = descField.getText();
            String status = statusField.getText();
            service.addComplaint(new Complaint(name, desc, status));
            showMessage("Complaint Added");
        });

        // View Complaints
        viewBtn.addActionListener(e -> {
            List<Complaint> list = service.getAllComplaints();
            displayArea.setText("");
            list.forEach(c -> displayArea.append(
                    c.getId() + " | " +
                            c.getName() + " | " +
                            c.getDescription() + " | " +
                            c.getStatus() + "\n"
            ));
        });

        // Update Complaint
        updateBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String description = descField.getText();
                String status = statusField.getText();
                service.updateComplaint(id, description, status);
                showMessage("Updated Successfully");
            } catch (ComplaintNotFoundException ex) {
                IO.println(ex);
            }
        });

        // Delete Complaint
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                service.deleteComplaint(id);
                showMessage("Deleted Successfully");
            } catch (ComplaintNotFoundException ex) {
                IO.println("Exception is: " + ex);
            }
        });

        // Procedure Call
        procBtn.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String description = descField.getText();
            String status = statusField.getText();
            service.updateStatusUsingProcedure(id, description, status);
            showMessage("Updated via Procedure");
        });
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComplaintDashboard().setVisible(true));
    }

    // Dummy Data Loader
    private void loadDummyData() {
        service.addComplaint(new Complaint("Sony", "TV not working", "OPEN"));
        service.addComplaint(new Complaint("Amit", "Internet slow", "OPEN"));
    }

    // Message Helper
    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}