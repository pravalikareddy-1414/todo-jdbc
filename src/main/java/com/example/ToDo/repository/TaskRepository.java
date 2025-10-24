// com/example/ToDo/Repository/TasksRepository.java
package com.example.ToDo.repository;

import com.example.ToDo.Model.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    private final DataSource dataSource;
    public TaskRepository(DataSource dataSource) { this.dataSource = dataSource; }

    private Tasks mapRow(ResultSet rs) throws SQLException {
        Tasks t = new Tasks();
        t.setId(rs.getInt("id"));
        t.setTitle(rs.getString("title"));
        t.setDescription(rs.getString("description"));
        t.setStatus(Status.valueOf(rs.getString("status")));       // ENUM as String → Java enum
        t.setPriority(Priority.valueOf(rs.getString("priority"))); // same
        Timestamp c = rs.getTimestamp("created_at");
        Timestamp u = rs.getTimestamp("updated_at");
        t.setCreatedAt(c != null ? c.toLocalDateTime() : null);
        t.setUpdatedAt(u != null ? (Object) u.toLocalDateTime() : null);
        return t;
    }

    // CREATE
    public Tasks save(Tasks task) {
        String sql = "INSERT INTO tasks (title, description, status, priority) VALUES (?,?,?,?)";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setString(3, (task.getStatus()==null? Status.PENDING : task.getStatus()).name());
            ps.setString(4, (task.getPriority()==null? Priority.MEDIUM : task.getPriority()).name());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) task.setId(keys.getInt(1));
            }
            // fetch row back to populate timestamps (optional)
            return findById(task.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Error saving task", e);
        }
    }

    // READ ALL
    public List<Tasks> findAll() {
        String sql = "SELECT id,title,description,status,priority,created_at,updated_at FROM tasks ORDER BY id";
        List<Tasks> out = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(mapRow(rs));
            return out;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tasks", e);
        }
    }

    // READ BY ID
    public Tasks findById(int id) {
        String sql = "SELECT id,title,description,status,priority,created_at,updated_at FROM tasks WHERE id=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching task by id", e);
        }
    }

    // UPDATE (full replace). MySQL auto-updates updated_at.
    public boolean update(int id, Tasks t) {
        String sql = "UPDATE tasks SET title=?, description=?, status=?, priority=? WHERE id=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getStatus().name());
            ps.setString(4, t.getPriority().name());
            ps.setInt(5, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating task", e);
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM tasks WHERE id=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting task", e);
        }
    }

    // FILTERS
    public List<Tasks> findByStatus(Status status) {
        String sql = "SELECT id,title,description,status,priority,created_at,updated_at FROM tasks WHERE status=?";
        List<Tasks> out = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
            return out;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching by status", e);
        }
    }

    public List<Tasks> findByPriority(Priority priority) {
        String sql = "SELECT id,title,description,status,priority,created_at,updated_at FROM tasks WHERE priority=?";
        List<Tasks> out = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, priority.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
            return out;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching by priority", e);
        }
    }
}

