package com.projectRest.controller;

import com.projectRest.constant.Message;
import com.projectRest.error.BadRequestException;
import com.projectRest.error.ErrorResponseEntity;
import com.projectRest.error.RolNotFoundException;
import com.projectRest.error.RoleFoundException;
import com.projectRest.helper.Validations;
import com.projectRest.model.Rol;
import com.projectRest.model.RolRequest;
import com.projectRest.response.ResponseRest;
import com.projectRest.service.RolService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


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
        if (validations.isNullBody(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.ROLE_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());
        } else {
            Rol requestEntityBody = requestEntity.getBody();
            try {
                Rol rolServiceByName = rolService.findByName(requestEntityBody.getName());
                if (!rolServiceByName.emptyRol()) {

                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.ROLE_WITH.getMesage() + requestEntityBody.getName() +
                                    Message.EXIST.getMesage());

                }
            } catch (RolNotFoundException e) {
                responseEntity = new ResponseEntity<>(rolService.save(requestEntityBody), HttpStatus.CREATED);
            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.ROLE_WITH.getMesage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMesage());
            }
        }
        return responseEntity;
    }

    @GetMapping("/listroles")
    public ResponseEntity<?> listRoles() {
        List<Rol> rolList = rolService.findAll();
        ResponseEntity responseEntity = null;
        if (validations.isNullOrEmpty(rolList)) {

            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.OK, HttpStatus.OK.value(),
                    Message.NO_EXIST_ROL.getMesage());
        } else {
            responseEntity = new ResponseEntity<>(rolList, HttpStatus.OK);
        }
        return responseEntity;
    }

    @PutMapping("/updateRol/{name}")
    public ResponseEntity<?> updateRol(RequestEntity<RolRequest> requestEntity) {
        ResponseEntity responseEntity = null;
        RolRequest requestEntityBody = requestEntity.getBody();
        if (validations.isNullBody(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.FORMAT_REQUEST_WRONG.getMesage());

        } else {
            try {
                boolean existRoleByName = rolService.existRoleByName(requestEntityBody.getName());
                if (existRoleByName) {
                    responseEntity = new ResponseEntity<>(rolService.update(requestEntityBody), HttpStatus.OK);
                } else {
                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.ROLE_WITH.getMesage() + requestEntityBody.getName() +
                                    Message.NOT_EXIST.getMesage());

                }
            } catch (RolNotFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        e.getMessage());

            } catch (RoleFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        e.getMessage());

            } catch (BadRequestException e) {

                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                        Message.FORMAT_REQUEST_WRONG.getMesage());


            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.NOT_UPDATE.getMesage());

            }
        }
        return responseEntity;
    }

    @DeleteMapping("/deleterole/{name}")
    public ResponseEntity<?> deleteRole(RequestEntity<Rol> requestEntity) {
        ResponseEntity responseEntity = null;
        if (validations.isNullBody(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.FORMAT_REQUEST_WRONG.getMesage());
        } else {
            Rol requestEntityBody = requestEntity.getBody();
            try {
                rolService.delete(requestEntityBody);
                ResponseRest responseRest = new ResponseRest(Message.ROLE_DELETE.getMesage(), HttpStatus.OK);
                responseEntity = new ResponseEntity<>(responseRest, HttpStatus.OK);
            } catch (RolNotFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.ROLE_WITH.getMesage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMesage());

            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.FORMAT_REQUEST_WRONG.getMesage());

            }

        }
        return responseEntity;
    }


}
