package com.example.ToDo.Service;

import com.example.ToDo.Model.Priority;
import com.example.ToDo.Model.Status;
import com.example.ToDo.Model.Tasks;
import com.example.ToDo.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {
    private final TaskRepository repo;   // ✅ instead of list, we depend on DB repository

    public TasksService(TaskRepository repo) {
        this.repo = repo;                 // ✅ injected by Spring
    }

    public Tasks createTask(Tasks task) {
        if (task.getStatus() == null)   task.setStatus(Status.PENDING);
        if (task.getPriority() == null) task.setPriority(Priority.MEDIUM);
        return repo.save(task);          // ✅ INSERT into MySQL (no ArrayList anymore)
    }

    public List<Tasks> getAllTasks() {
        return repo.findAll();           // ✅ SELECT * FROM tasks
    }

    public Tasks getTaskById(int id) {
        Tasks t = repo.findById(id);     // ✅ SELECT * FROM tasks WHERE id=?
        if (t == null) throw new RuntimeException("TaskId not found");
        return t;
    }

    public Tasks updateTask(int id, Tasks updatedTask) {
        Tasks existing = getTaskById(id);
        // ✅ same partial update logic as before
        if (updatedTask.getTitle() != null)       existing.setTitle(updatedTask.getTitle());
        if (updatedTask.getDescription() != null) existing.setDescription(updatedTask.getDescription());
        if (updatedTask.getStatus() != null)      existing.setStatus(updatedTask.getStatus());
        if (updatedTask.getPriority() != null)    existing.setPriority(updatedTask.getPriority());

        boolean ok = repo.update(id, existing);   // ✅ UPDATE tasks SET ... WHERE id=?
        if (!ok) throw new RuntimeException("Update failed");
        return repo.findById(id);                 // ✅ re-fetch to get DB’s updated_at
    }

    public List<Tasks> getTasksByStatus(Status status) {
        return repo.findByStatus(status);         // ✅ SELECT * FROM tasks WHERE status=?
    }

    public List<Tasks> getTasksByPriority(Priority priority) {
        return repo.findByPriority(priority);     // ✅ SELECT * FROM tasks WHERE priority=?
    }

    public boolean deleteTask(int id) {
        return repo.delete(id);                   // ✅ DELETE FROM tasks WHERE id=?
    }
}


