package com.projectRest.controller;

import com.projectRest.constant.Message;
import com.projectRest.error.RolNotFoundException;
import com.projectRest.helper.Validations;
import com.projectRest.model.Rol;
import com.projectRest.service.RolService;
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

import com.projectRest.error.ErrorResponseEntity;


@RestController
@RequestMapping("/api")
public class RolController {
    private static final Logger logger = LogManager.getLogger(RolController.class);

    @Autowired
    RolService rolService;
    @Autowired
    Validations validations;

    @PostMapping("/addrol")
    public ResponseEntity<?> addRol(RequestEntity<Rol> requestEntity) {
        ResponseEntity responseEntity = null;
        if (validations.validateBodyRequest(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.ROL_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());


        } else {
            Rol requestEntityBody = requestEntity.getBody();
            try {
                Rol rolServiceByName = rolService.findByName(requestEntityBody.getName());
                if (!rolServiceByName.emptyRol()){

                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.ROL_WITH.getMesage() + requestEntityBody.getName() +
                                    Message.EXIST.getMesage());

                }
            } catch (RolNotFoundException e) {
                responseEntity = new ResponseEntity<>(rolService.save(requestEntityBody), HttpStatus.CREATED);
            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.ROL_WITH.getMesage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMesage());
            }
        }
        return responseEntity;
    }

}
