package com.projectRest.repository;

import com.projectRest.entity.EntityEmployee;
import com.projectRest.entity.EntityRegistry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistryRepository extends JpaRepository<EntityRegistry, Long> {
	
	List<EntityRegistry> findByEmployee(EntityEmployee employee);

	List<EntityRegistry> findByEmployee(EntityEmployee employee, Sort sort);

	List<EntityRegistry> findAllByOrderByDateRegistryAsc();

	List<EntityRegistry> findEntityRegistryByEmployee_NameContains(String name);

	List<EntityRegistry> findEntityRegistryByEmployee_NameContainsIgnoreCase(String name);


	List<EntityRegistry> findByEmployeeContaining(EntityEmployee entityEmployee);


}
