package com.projectRest.controller;

import com.projectRest.constant.Message;
import com.projectRest.entity.EntityRole;
import com.projectRest.error.ErrorResponseEntity;
import com.projectRest.exception.NotFoundException;
import com.projectRest.helper.Validations;
import com.projectRest.model.Role;
import com.projectRest.model.UserApp;
import com.projectRest.model.UserAppRequest;
import com.projectRest.service.RoleService;
import com.projectRest.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api")
public class UserAppController {
    private static final Logger logger = LogManager.getLogger(UserAppController.class);

    @Autowired
    UserService userService;
    @Autowired
    RoleService rolService;
    @Autowired
    Validations validations;

    @PostMapping("/adduser")
    public ResponseEntity<?> addRol(RequestEntity<UserAppRequest> requestEntity) {
        ResponseEntity responseEntity = null;
        UserAppRequest entityBody = requestEntity.getBody();
        if (isCorrectValidations(requestEntity, entityBody)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.USER_WITH.getMessage() + Message.FORMAT_REQUEST_WRONG.getMessage());
        } else {
            EntityRole entityRole = rolService.findEntityByName(entityBody.getRoleName());
            if (entityRole != null) {
                UserApp requestEntityBody = new UserApp(entityBody,entityRole);
                try {
                    UserApp userServiceByName = userService.findByName(requestEntityBody.getUser());
                    if (userServiceByName.emptyUser()) {
                    } else {
                        responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                                Message.USER_WITH.getMessage() + requestEntityBody.getUser() +
                                        Message.EXIST.getMessage());
                    }
                } catch (NotFoundException e) {
                    responseEntity = new ResponseEntity<>(userService.save(requestEntityBody), HttpStatus.CREATED);
                } catch (NoSuchElementException e) {
                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.USER_WITH.getMessage() + requestEntityBody.getUser() +
                                    Message.NOT_EXIST.getMessage());
                }

            } else {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.ROLE_WITH.getMessage() + entityBody.getRoleName() +
                                Message.NOT_EXIST.getMessage());
            }


        }
        return responseEntity;
    }

    private boolean isCorrectValidations(RequestEntity<UserAppRequest> requestEntity, UserAppRequest entityBody) {
        return validations.isNullBody(requestEntity) || validations.isNullOrEmptyParameter(entityBody.getRoleName()) ||
                validations.isNullOrEmptyParameter(entityBody.getPassword()) ||
                validations.isNullOrEmptyParameter(entityBody.getUser());
    }


}
