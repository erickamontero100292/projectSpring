package com.projectRest.service;


import com.projectRest.constant.Message;
import com.projectRest.entity.EntityRole;
import com.projectRest.exception.RoleFoundException;
import com.projectRest.exception.NotFoundException;
import com.projectRest.model.Role;
import com.projectRest.model.RoleRequest;
import com.projectRest.repository.RoleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(EntityRole entityRole) {
        EntityRole role = roleRepository.save(entityRole);
        return new Role(role);
    }

    public Role save(Role role) {
        EntityRole entityRole = null;
        try {
            entityRole = new EntityRole(role);
            entityRole = roleRepository.save(entityRole);
        } catch (Exception e) {
            throw new NotFoundException(Message.ROLE.getMessage(),role.getName());
        }
        return new Role(entityRole);
    }

    public Role update(RoleRequest role) {
        EntityRole entityRole = null;
        try {
            entityRole = roleRepository.findByName_IgnoreCase(role.getName());
            entityRole.updateRole(role);
            entityRole = roleRepository.save(entityRole);
        } catch (NullPointerException exception) {
            throw new NotFoundException( Message.ROLE_WITH.getMessage() + role.getName() +
                    Message.NOT_EXIST.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new RoleFoundException(role.getNameChange());
        } catch (Exception e) {
            throw new NotFoundException(role.getName(), " no se logro actualizar");
        }
        return new Role(entityRole);
    }

    public boolean delete(Role role) {
        EntityRole entityRole = null;
        boolean isDelete;
        try {
            entityRole = roleRepository.findByName_IgnoreCase(role.getName());
            roleRepository.delete(entityRole);
            isDelete = true;
        } catch (Exception e) {
            throw new NotFoundException(role.getName(), Message.NOT_DELETE.getMessage());
        }
        return isDelete;
    }

    public List<Role> findAll() {
        List<EntityRole> roleRepositoryAll = roleRepository.findAll();
        List<Role> roles = new ArrayList<>();
        for (EntityRole role : roleRepositoryAll) {
            roles.add(new Role(role));
        }

        return roles;
    }

    public Role findById(Long id) {
        Role role = null;
        try {
            EntityRole entityRole = roleRepository.findById(id).orElse(null);
            role = new Role(entityRole);
        } catch (Exception e) {
            throw new NotFoundException(Message.ROLE.getMessage(),id);
        }

        return role;

    }

    public EntityRole findEntityByName(String name) {
        EntityRole entientityRolyWorkday = null;
        try {
             entientityRolyWorkday = roleRepository.findByName_IgnoreCase(name);

        } catch (IncorrectResultSizeDataAccessException | NonUniqueResultException e) {
            throw e;
        } catch (Exception e) {
            throw new NotFoundException(name);
        }

        return entientityRolyWorkday;

    }
    public Role findByName(String name) {
        Role role = null;
        try {
            EntityRole entientityRolyWorkday = roleRepository.findByName_IgnoreCase(name);
            role = new Role(entientityRolyWorkday);
        } catch (IncorrectResultSizeDataAccessException | NonUniqueResultException e) {
            throw e;
        } catch (Exception e) {
             role = new Role();
        }

        return role;

    }

    public boolean existRoleByName(String name) {
        boolean existRole = false;
        try {
            EntityRole entityRole = roleRepository.findByName_IgnoreCase(name);
            if(entityRole != null){
                existRole = true;
            }
        } catch (IncorrectResultSizeDataAccessException | NonUniqueResultException e) {
            throw e;
        } catch (Exception e) {
            throw new NotFoundException(name);
        }

        return existRole;

    }
}
