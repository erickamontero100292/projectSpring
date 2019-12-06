package com.projectRest.controller;

import com.projectRest.constant.Message;
import com.projectRest.error.BadRequestException;
import com.projectRest.error.ErrorResponseEntity;
import com.projectRest.exception.RoleFoundException;
import com.projectRest.exception.NotFoundException;
import com.projectRest.helper.Validations;
import com.projectRest.model.Role;
import com.projectRest.model.RoleRequest;
import com.projectRest.response.ResponseRest;
import com.projectRest.service.RoleService;
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
public class RoleController {
    private static final Logger logger = LogManager.getLogger(RoleController.class);

    @Autowired
    RoleService rolService;
    @Autowired
    Validations validations;

    @PostMapping("/addrol")
    public ResponseEntity<?> addRol(RequestEntity<Role> requestEntity) {
        ResponseEntity responseEntity = null;
        if (validations.isNullBody(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.ROLE_WITH.getMessage() + Message.FORMAT_REQUEST_WRONG.getMessage());
        } else {
            Role requestEntityBody = requestEntity.getBody();
            try {
                Role roleServiceByName = rolService.findByName(requestEntityBody.getName());
                if (!roleServiceByName.emptyRol()) {

                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.ROLE_WITH.getMessage() + requestEntityBody.getName() +
                                    Message.EXIST.getMessage());

                }
            } catch (NotFoundException e) {
                responseEntity = new ResponseEntity<>(rolService.save(requestEntityBody), HttpStatus.CREATED);
            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.ROLE_WITH.getMessage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMessage());
            }
        }
        return responseEntity;
    }

    @GetMapping("/listroles")
    public ResponseEntity<?> listRoles() {
        List<Role> roleList = rolService.findAll();
        ResponseEntity responseEntity = null;
        if (validations.isNullOrEmpty(roleList)) {

            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.OK, HttpStatus.OK.value(),
                    Message.NO_EXIST_ROL.getMessage());
        } else {
            responseEntity = new ResponseEntity<>(roleList, HttpStatus.OK);
        }
        return responseEntity;
    }

    @PutMapping("/updateRol/{name}")
    public ResponseEntity<?> updateRol(RequestEntity<RoleRequest> requestEntity) {
        ResponseEntity responseEntity = null;
        RoleRequest requestEntityBody = requestEntity.getBody();
        if (validations.isNullBody(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.FORMAT_REQUEST_WRONG.getMessage());

        } else {
            try {
                boolean existRoleByName = rolService.existRoleByName(requestEntityBody.getName());
                if (existRoleByName) {
                    responseEntity = new ResponseEntity<>(rolService.update(requestEntityBody), HttpStatus.OK);
                } else {
                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.ROLE_WITH.getMessage() + requestEntityBody.getName() +
                                    Message.NOT_EXIST.getMessage());

                }
            } catch (NotFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        e.getMessage());

            } catch (RoleFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        e.getMessage());

            } catch (BadRequestException e) {

                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                        Message.FORMAT_REQUEST_WRONG.getMessage());


            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.NOT_UPDATE.getMessage());

            }
        }
        return responseEntity;
    }

    @DeleteMapping("/deleterole/{name}")
    public ResponseEntity<?> deleteRole(RequestEntity<Role> requestEntity) {
        ResponseEntity responseEntity = null;
        Role requestEntityBody = requestEntity.getBody();
        if (validations.isNullBody(requestEntity) || requestEntityBody.isEmptyName(requestEntityBody)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.FORMAT_REQUEST_WRONG.getMessage());
        } else {
            try {
                rolService.delete(requestEntityBody);
                ResponseRest responseRest = new ResponseRest(Message.ROLE_DELETE.getMessage(), HttpStatus.OK);
                responseEntity = new ResponseEntity<>(responseRest, HttpStatus.OK);
            } catch (NotFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.ROLE_WITH.getMessage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMessage());

            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.FORMAT_REQUEST_WRONG.getMessage());

            }

        }
        return responseEntity;
    }


}
