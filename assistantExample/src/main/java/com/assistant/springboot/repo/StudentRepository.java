package com.assistant.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assistant.springboot.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	

}
