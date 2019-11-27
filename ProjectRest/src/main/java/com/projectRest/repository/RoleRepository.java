package com.projectRest.repository;

import com.projectRest.entity.EntityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<EntityRole, Long> {

    EntityRole findByName(String name);

    EntityRole findByName_IgnoreCase(String name);

}
