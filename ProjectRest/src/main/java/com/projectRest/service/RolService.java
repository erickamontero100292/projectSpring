package com.projectRest.service;


import com.projectRest.constant.Message;
import com.projectRest.entity.EntityRol;
import com.projectRest.error.RolNotFoundException;
import com.projectRest.error.RoleFoundException;
import com.projectRest.model.Rol;
import com.projectRest.model.RolRequest;
import com.projectRest.repository.RolRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RolService {

    private RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Rol save(EntityRol entityRol) {
        EntityRol rol = rolRepository.save(entityRol);
        return new Rol(rol);
    }

    public Rol save(Rol rol) {
        EntityRol entityRol = null;
        try {
            entityRol = new EntityRol(rol);
            entityRol = rolRepository.save(entityRol);
        } catch (Exception e) {
            throw new RolNotFoundException(rol.getName());
        }
        return new Rol(entityRol);
    }

    public Rol update(RolRequest role) {
        EntityRol entityRol = null;
        try {
            entityRol = rolRepository.findByName_IgnoreCase(role.getName());
            entityRol.updateRol(role);
            entityRol = rolRepository.save(entityRol);
        } catch (NullPointerException exception) {
            throw new RolNotFoundException( Message.ROLE_WITH.getMesage() + role.getName() +
                    Message.NOT_EXIST.getMesage());
        } catch (DataIntegrityViolationException e) {
            throw new RoleFoundException(role.getNameChange());
        } catch (Exception e) {
            throw new RolNotFoundException(role.getName(), " no se logro actualizar");
        }
        return new Rol(entityRol);
    }

    public boolean delete(Rol rol) {
        EntityRol entityRol = null;
        boolean isDelete;
        try {
            entityRol = rolRepository.findByName_IgnoreCase(rol.getName());
            rolRepository.delete(entityRol);
            isDelete = true;
        } catch (Exception e) {
            throw new RolNotFoundException(rol.getName(), " no se logro eliminar");
        }
        return isDelete;
    }

    public List<Rol> findAll() {
        List<EntityRol> rolRepositoryAll = rolRepository.findAll();
        List<Rol> rols = new ArrayList<>();
        for (EntityRol rol : rolRepositoryAll) {
            rols.add(new Rol(rol));
        }

        return rols;
    }

    public Rol findById(Long id) {
        Rol rol = null;
        try {
            EntityRol entityRol = rolRepository.findById(id).orElse(null);
            rol = new Rol(entityRol);
        } catch (Exception e) {
            throw new RolNotFoundException(id);
        }

        return rol;

    }

    public Rol findByName(String name) {
        Rol rol = null;
        try {
            EntityRol entientityRolyWorkday = rolRepository.findByName_IgnoreCase(name);
            rol = new Rol(entientityRolyWorkday);
        } catch (IncorrectResultSizeDataAccessException | NonUniqueResultException e) {
            throw e;
        } catch (Exception e) {
            throw new RolNotFoundException(name);
        }

        return rol;

    }

    public boolean existRoleByName(String name) {
        boolean existRole = false;
        try {
            EntityRol entityRole = rolRepository.findByName_IgnoreCase(name);
            if(entityRole != null){
                existRole = true;
            }
        } catch (IncorrectResultSizeDataAccessException | NonUniqueResultException e) {
            throw e;
        } catch (Exception e) {
            throw new RolNotFoundException(name);
        }

        return existRole;

    }

    private boolean processDeleteRol(Rol requestEntityBody) {
        boolean isDelete =false;
        Rol roleByName = findByName(requestEntityBody.getName());
        ResponseEntity responseEntity = null;

        try {
            if(roleByName != null){
                delete(roleByName);
                isDelete=true;
            }
        } catch (RolNotFoundException e) {
           throw e;
        }


        return isDelete;
    }
}
