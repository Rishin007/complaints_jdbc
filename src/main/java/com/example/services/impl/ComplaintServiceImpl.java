package com.example.services.impl;

import com.example.config.DBUtil;
import com.example.entity.Complaint;
import com.example.exception.ComplaintNotFoundException;
import com.example.services.ComplaintService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComplaintServiceImpl implements ComplaintService {
    @Override
    public void addComplaint(Complaint complaint) {
        try (Connection con = DBUtil.getNewConnection()) {
            String sql = "insert into complaints(name,description,status) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, DBUtil.handleNull(complaint.getName()));
            ps.setString(2, DBUtil.handleNull(complaint.getDescription()));
            ps.setString(3, DBUtil.handleNull(complaint.getStatus()));
            ps.executeUpdate();
            System.out.println("Complaint Added!");
        } catch (Exception e) {
            IO.println("The error is " + e);
        }
    }

    @Override
    public Complaint findById(int id) throws ComplaintNotFoundException {
        return getAllComplaints().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found"));
    }

    @Override
    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        try (Connection con = DBUtil.getNewConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM complaints");
            while (rs.next()) {
                list.add(new Complaint(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            IO.println("The error is " + e);
        }
        // Stream + Lambda Example
        return list.stream()
                .filter(c -> c.getStatus().equalsIgnoreCase("OPEN"))
                .collect(Collectors.toList());

    }

    @Override
    public void updateComplaint(int id, String description, String status) throws ComplaintNotFoundException {
        try (Connection con = DBUtil.getNewConnection()) {
            String sql = "UPDATE complaints SET status=?, description=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, description);
            ps.setInt(3, id);
            if (ps.executeUpdate() == 0) {
                throw new ComplaintNotFoundException("No complaint with the following id found");
            }
        } catch (Exception e) {
            IO.println(e);
        }
    }

    @Override
    public void deleteComplaint(int id) throws ComplaintNotFoundException {
        try (Connection con = DBUtil.getNewConnection()) {
            String sql = "DELETE FROM complaints WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new ComplaintNotFoundException("Delete failed");
            } else
                IO.println("Deleted successfully complaint with id " + id);
        } catch (Exception e) {
            IO.println(e);
        }
    }

    @Override
    public void updateStatusUsingProcedure(int id, String description, String status) {
        try (Connection con = DBUtil.getNewConnection()) {
            CallableStatement cs = con.prepareCall("{call update_status_proc(?, ?, ?)}");
            cs.setInt(1, id);
            cs.setString(2, description);
            cs.setString(3, status);
            cs.execute();
            System.out.println("Updated via procedure!");
        } catch (Exception e) {
            IO.println(e);
        }
    }
}
