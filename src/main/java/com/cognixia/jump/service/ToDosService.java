package com.cognixia.jump.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.ToDos;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.ToDosRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class ToDosService {

	@Autowired
	ToDosRepository toDosRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Set < ToDos > getTodosByUser(Authentication req) throws ResourceNotFoundException {
        Optional <User> user =  userRepository.findByUsername(req.getName());
		
        if(user.isPresent()) {
        	
        	Set <ToDos> todoList = user.get().getTodos();
        	
        	return todoList;
        }
        
        throw new ResourceNotFoundException("ToDos");
    }
	
	
	public ToDos createNewTodo(ToDos todo, Authentication req) throws ResourceNotFoundException {
		

		Optional<User> user = userRepository.findByUsername(req.getName());
		
		if(user.isPresent()) {
			todo.setUser(user.get());
			
			ToDos created = toDosRepository.save(todo);
			
			return created;
		}
		
		throw new ResourceNotFoundException(req.getName());
		
		
		
	}
	
	public Optional<ToDos> updateTodoCompletedById(int id, Authentication req) throws ResourceNotFoundException {
		
		 Optional <User> user =  userRepository.findByUsername(req.getName());
			
	     if(user.isPresent()) {
	    	 User updateComplete = user.get();
	    	 
	    	 if(toDosRepository.existsById(id)) {
	    		 updateComplete.getTodos().stream()
	    		 						  .filter(a -> id == a.getId())
	    		 						  .forEach(a -> a.setCompleted(true));
	    		 						  
	    		 toDosRepository.updatCompleted(true, id);
	    		 
	    		 return toDosRepository.findById(id);
	    	 }
	        	
	        	
	        	
	     }
		
		
		throw new ResourceNotFoundException("ToDo");
		 
    }
	
	public Set <ToDos> updateAllTodoAsCompleted(Authentication req) throws ResourceNotFoundException {
		
		 Optional <User> user =  userRepository.findByUsername(req.getName());
			
	     if(!user.isPresent()) {
	    	 throw new ResourceNotFoundException("User with Username: " + req.getName()+ " not found");
	     }
     	
	     User updateComplete = user.get();
	 
		 updateComplete.getTodos().stream()
		 						  .forEach(a -> a.setCompleted(true));
		 						  
		 toDosRepository.updatAllAsCompleted(true);
		 
		
		 Set <ToDos> todoList =  getTodosByUser(req);
		 
		 return todoList;
		 
   }
	
	public ToDos deleteTodo(int id, Authentication req) throws ResourceNotFoundException {
		
		Optional<User> user = userRepository.findByUsername(req.getName());
		
		if(user.isPresent()) {
			Optional < ToDos > todo = toDosRepository.findById(id);
	        if (!todo.isPresent()) {
	        	
	        	 throw new ResourceNotFoundException("ToDo with ID " + id + " not found.");
	        	
	        }
	        
	        user.get().getTodos().remove(todo.get());
        	
        	toDosRepository.saveAll(user.get().getTodos());
        	
        	toDosRepository.delete(todo.get());
        	
        	return todo.get();
			
		}
        
        throw new ResourceNotFoundException("User with Username: " + req.getName()+ " not found");
    }
	
	
	public void deleteAllTodo(Authentication req) throws ResourceNotFoundException {
		
		Optional<User> user = userRepository.findByUsername(req.getName());
		
		if(!user.isPresent()) {
	        
			  throw new ResourceNotFoundException("User with Username: " + req.getName()+ " not found");
	    }
			
		user.get().getTodos().clear();
    	
    	toDosRepository.saveAll(user.get().getTodos());
    	
    	toDosRepository.deleteAll();
      
	
	}
	
	public Optional<ToDos> updateDueDate(Map<String, String> updateInfo, Authentication req) throws ResourceNotFoundException {
		
		//extract data
		int id = Integer.parseInt(updateInfo.get("id"));
		String date = updateInfo.get("dueDate");
		DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dueDate = LocalDate.parse(date, FORMATTER); 
		
		Optional<User> user = userRepository.findByUsername(req.getName());
		
		if(user.isPresent()) {
			Optional < ToDos > todo = toDosRepository.findById(id);
	        if (!todo.isPresent()) {
	        	
	        	 throw new ResourceNotFoundException("ToDo with ID " + id + " not found.");
	        }
	        
	        user.get().getTodos().stream()
	        					.filter(i -> i.getId() == id)
	        					.forEach(i -> i.setDueDate(dueDate));
	        
	        toDosRepository.updateDueDate(dueDate, id);
	        
	        return toDosRepository.findById(id);
	        
        }
		throw new ResourceNotFoundException("ToDo with ID " + id + " not found.");
    	
	}
}
