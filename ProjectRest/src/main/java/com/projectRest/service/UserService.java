package com.projectRest.service;


import com.projectRest.constant.Message;
import com.projectRest.entity.EntityUserApp;
import com.projectRest.exception.NotFoundException;
import com.projectRest.model.UserApp;
import com.projectRest.repository.UserAppRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;

@Service
public class UserService {

    private UserAppRepository userAppRepository;

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
}
