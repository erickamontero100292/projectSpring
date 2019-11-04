package com.projectRest.repository;

import com.projectRest.entity.EntityEmployee;
import com.projectRest.entity.EntityUserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EntityEmployee, Long> {


    EntityEmployee findByUser(EntityUserApp userApp);
}
