package com.projectRest.repository;

import com.projectRest.entity.EntityWorkday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDayRepository extends JpaRepository<EntityWorkday, Long> {


    EntityWorkday findByName_IgnoreCase(String name);

}
