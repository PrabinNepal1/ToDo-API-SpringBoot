package com.cognixia.jump.repository;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cognixia.jump.model.ToDos;


public interface ToDosRepository extends JpaRepository<ToDos, Integer> {
	 
	
	 @Transactional
	 @Modifying
	 @Query("UPDATE ToDos c SET c.completed = :completed WHERE c.id = :id")
	 public void updatCompleted(@Param("completed")  boolean complete, @Param("id") int id);
	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE ToDos c SET c.completed = :completed")
	 public void updatAllAsCompleted(@Param("completed")  boolean complete);
	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE ToDos c SET c.dueDate = :dueDate WHERE c.id = :id")
	 public void updateDueDate(@Param("dueDate") LocalDate dueDate, @Param("id") int id);

}
