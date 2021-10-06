package com.cognixia.jump.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.ToDos;
import com.cognixia.jump.repository.ToDosRepository;
import com.cognixia.jump.service.ToDosService;


@RequestMapping("/api")
@RestController
public class ToDosController {
	
	@Autowired
	ToDosRepository toDosRepository;
	
	@Autowired
	ToDosService toDosService;
	
	@GetMapping("/todos")
	public Set<ToDos> getAllToDosByUser(Authentication user) throws ResourceNotFoundException{
		return toDosService.getTodosByUser(user);
	}
	
	@PostMapping("create/todo")
	public ResponseEntity<ToDos> createNewTodo(@RequestBody ToDos todo, Authentication user) throws ResourceNotFoundException{
		
		ToDos created = toDosService.createNewTodo(todo, user);
		
		return new ResponseEntity<ToDos>(created, HttpStatus.CREATED);
		
	}
	
	@PatchMapping("/complete/todo/{id}")
	public Optional<ToDos> updateCompletedById(@PathVariable int id, Authentication user) throws ResourceNotFoundException{
		return toDosService.updateTodoCompletedById(id, user);
	}
	
	@PatchMapping("/complete/todos")
	public Set<ToDos> updateAllTodoAsCompleted(Authentication user) throws ResourceNotFoundException{
		return toDosService.updateAllTodoAsCompleted(user);
	}
	
	@PatchMapping("/update/todo/duedate")
	public Optional<ToDos> updateDueDateByID(@RequestBody Map <String, String> updateInfo, Authentication req) throws ResourceNotFoundException{
		return toDosService.updateDueDate(updateInfo, req);
	}
	
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<ToDos> deleteTodo(@PathVariable int id, Authentication req) throws ResourceNotFoundException{
       ToDos deleted = toDosService.deleteTodo(id, req);
       
       return new ResponseEntity<ToDos> (deleted,HttpStatus.OK );
    }
	
	@DeleteMapping("/delete/todos")
	public ResponseEntity<?> deleteAllTodos(Authentication req) throws ResourceNotFoundException{
       
		toDosService.deleteAllTodo(req);
       
       return new ResponseEntity<String> ("All ToDo List Deleted for user " +req.getName() +".",HttpStatus.OK );
    }
	

}
