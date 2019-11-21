package com.projectRest.repository;

import com.projectRest.entity.EntityRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<EntityRol, Long> {

    EntityRol findByName(String name);

    EntityRol findByName_IgnoreCase(String name);

}
