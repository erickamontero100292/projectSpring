package com.projectRest.repository;

import com.projectRest.entity.EntityUserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserAppRepository   extends JpaRepository<EntityUserApp, Long> {


    EntityUserApp findFirstByUser(String email);

    EntityUserApp findByUser_IgnoreCase(String name);
}
