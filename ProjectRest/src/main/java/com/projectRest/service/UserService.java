package com.projectRest.service;


import com.projectRest.constant.Message;
import com.projectRest.entity.EntityRole;
import com.projectRest.entity.EntityUserApp;
import com.projectRest.error.ErrorResponseEntity;
import com.projectRest.exception.FoundException;
import com.projectRest.exception.NotFoundException;
import com.projectRest.exception.RoleFoundException;
import com.projectRest.model.UserApp;
import com.projectRest.model.UserAppRequest;
import com.projectRest.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserAppRepository userAppRepository;

    @Autowired
    RoleService rolService;

    public UserService(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    public UserApp save(EntityUserApp entityUserApp) {
        EntityUserApp userApp = userAppRepository.save(entityUserApp);
        return new UserApp(userApp);
    }


    public UserApp save(UserApp userApp) {
        EntityUserApp entityUserApp = null;
        try {
            entityUserApp = new EntityUserApp(userApp);
            entityUserApp = userAppRepository.save(entityUserApp);
        } catch (Exception e) {
            throw new NotFoundException(Message.USER.getMessage(), userApp.getUser());
        }
        return new UserApp(entityUserApp);
    }

    public UserApp findByName(String name) {
        UserApp userApp = null;
        try {
            EntityUserApp entityUserApp = userAppRepository.findByUser_IgnoreCase(name);
            userApp = new UserApp(entityUserApp);
        } catch (IncorrectResultSizeDataAccessException | NonUniqueResultException e) {
            throw e;
        } catch (Exception e) {
            throw new NotFoundException(name);
        }

        return userApp;
    }

    public UserApp createUser(UserAppRequest entityBody) {
        boolean isOk = true;
        UserApp savedUser = null;
        EntityRole entityRole = rolService.findEntityByName(entityBody.getRoleName());
        if (entityRole == null) {
            throw new NotFoundException(Message.ROLE_WITH.getMessage(), entityBody.getRoleName());
        } else {
            EntityUserApp userServiceByName = userAppRepository.findByUser_IgnoreCase(entityBody.getUser());
            if (userServiceByName != null ) {
                throw new FoundException(Message.USER_WITH.getMessage(), entityBody.getUser());
            } else {
                UserApp requestEntityBody = new UserApp(entityBody, entityRole);
                savedUser = save(requestEntityBody);
            }
        }
        return savedUser;
    }


}

