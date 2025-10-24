package com.example.ToDo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ToDo.Model.Priority;
import com.example.ToDo.Model.Status;
import com.example.ToDo.Model.Tasks;
import com.example.ToDo.Service.TasksService;

@RestController
@RequestMapping("/tasks")
public class TasksController {
	
	@Autowired
	TasksService service ;
	
	@PostMapping
    public ResponseEntity<Tasks> createTask(@RequestBody Tasks tasks) {
        Tasks saved = service.createTask(tasks);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

	@GetMapping
	public ResponseEntity<List<Tasks>> getAllTasks(){
		return new ResponseEntity<List<Tasks>>(service.getAllTasks(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tasks> GetTaskById(@PathVariable int id)
	{
		return new ResponseEntity<Tasks>(service.getTaskById(id),HttpStatus.OK);
	}
	
	@PutMapping(
			  path = "/{id}",
			  consumes = "application/json",
			  produces = "application/json"
			)
	public Tasks updateTask(@PathVariable int id,@RequestBody Tasks updatedTask)
	{
		return service.updateTask(id, updatedTask);
	}
	
	
	 @GetMapping("/status/{status}")
	 public  List<Tasks> getTasksByStatus(@PathVariable Status status) {
		 
		 return service.getTasksByStatus(status);
	 }
	 
	 @GetMapping("/priority/{priority}")
	 public List<Tasks> getTasksByPriority(@PathVariable Priority priority) {
		 return service.getTasksByPriority(priority);
	 }
	
	 @DeleteMapping("/{id}")
	 public boolean deleteTask(@PathVariable int id)
	 {
		 return service.deleteTask(id);
	 }
		
	
}
