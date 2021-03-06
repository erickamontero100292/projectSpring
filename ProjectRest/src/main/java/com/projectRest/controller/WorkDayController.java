package com.projectRest.controller;


import com.projectRest.constant.Message;
import com.projectRest.entity.EntityWorkday;
import com.projectRest.error.BadRequestException;
import com.projectRest.error.ErrorResponse;
import com.projectRest.error.ErrorResponseEntity;
import com.projectRest.exception.NotFoundException;
import com.projectRest.helper.Validations;
import com.projectRest.helper.WorkdayHelper;
import com.projectRest.model.Workday;
import com.projectRest.response.ResponseRest;
import com.projectRest.service.WorkDayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class WorkDayController {
    private static final Logger logger = LogManager.getLogger(WorkDayController.class);

    @Autowired
    WorkDayService workDayService;

    @Autowired
    WorkdayHelper workdayHelper;
    @Autowired
    Validations validations;


    @GetMapping("/workday/{id}")
    public ResponseEntity getWorkdayById(@PathVariable Long id) {
        Workday entityWorkday = null;
        ResponseEntity responseEntity = null;
        try {
            if (id > 0) {
                entityWorkday = workDayService.findById(id);
                responseEntity = new ResponseEntity<>(entityWorkday, HttpStatus.OK);
            } else {
                if (id < 0) {
                    throw new BadRequestException("id", "Jornada", "no puede ser negativo");
                } else {

                    throw new BadRequestException("Jornada", id);
                }
            }
        } catch (NotFoundException e) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), e.getMessage());
            logger.debug(e.getMessage());

        } catch (BadRequestException e) {

            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.WORKDAY_WITH.getMessage() + Message.FORMAT_REQUEST_WRONG.getMessage());
            logger.debug(e.getMessage());
        }
        return responseEntity;
    }

    @GetMapping("/workday/findname/{name}")
    public ResponseEntity getWorkdayByName(@PathVariable String name) {
        Workday workday = null;
        ResponseEntity responseEntity = null;
        try {
            if (name != null) {
                workday = workDayService.findByName(name);

                responseEntity = new ResponseEntity<>(workday, HttpStatus.OK);

            }
        } catch (NotFoundException e) {

            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                    Message.WORKDAY_WITH.getMessage() + name + Message.NOT_EXIST.getMessage());
        }

        return responseEntity;
    }

    @PostMapping("/addworkday")
    public ResponseEntity<?> addWorkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        if (validations.isNullBody(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.WORKDAY_WITH.getMessage() + Message.FORMAT_REQUEST_WRONG.getMessage());


        } else {
            Workday requestEntityBody = requestEntity.getBody();
            try {
                Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());
                if (!workDayServiceByName.emptyWorkday()) {

                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.WORKDAY_WITH.getMessage() + requestEntityBody.getName() +
                                    Message.EXIST.getMessage());

                }
            } catch (NotFoundException e) {
                responseEntity = new ResponseEntity<>(workDayService.save(requestEntityBody), HttpStatus.CREATED);
            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.WORKDAY_WITH.getMessage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMessage());
            }
        }
        return responseEntity;
    }


    @PostMapping("/addListworkday")
    public ResponseEntity<?> addWorkdays(RequestEntity<List<Workday>> requestEntity) {
        ResponseEntity responseEntity = null;
        List<Workday> workday = requestEntity.getBody();
        if (workday == null) {

            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.WORKDAY_WITH.getMessage() + Message.FORMAT_REQUEST_WRONG.getMessage());


        } else {
            List<ErrorResponse> responseList = workdayHelper.validateWorkdayDuplicate(workday);
            if (responseList.isEmpty()) {
                List<ErrorResponse> errorResponses = workDayService.validateList(workday);
                if (errorResponses.isEmpty()) {
                    responseEntity = new ResponseEntity<>(workDayService.saveList(workday), HttpStatus.CREATED);
                } else {
                    responseEntity = new ResponseEntity<>(errorResponses, HttpStatus.CONFLICT);
                }
            } else {
                responseEntity = new ResponseEntity<>(responseList, HttpStatus.CONFLICT);

            }
        }
        return responseEntity;
    }


    @GetMapping("/listworkday")
    public ResponseEntity<?> listWorkdays() {
        List<Workday> workdayList = workDayService.findAll();
        ResponseEntity responseEntity = null;

        if (workdayList == null || workdayList.isEmpty()) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.OK, HttpStatus.OK.value(),
                    Message.NO_EXIST_WORKDAY.getMessage());
        } else {
            responseEntity = new ResponseEntity<>(workdayList, HttpStatus.OK);
        }

        return responseEntity;
    }


    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateWorkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        Workday requestEntityBody = requestEntity.getBody();
        if (validations.isNullBody(requestEntity) || requestEntityBody.isEmptyName(requestEntityBody)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.WORKDAY_WITH.getMessage() + Message.FORMAT_REQUEST_WRONG.getMessage());

        } else {
            try {
                Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());
                if (!workDayServiceByName.isEmptyName(workDayServiceByName)) {

                    responseEntity = new ResponseEntity<>(workDayService.update(requestEntityBody), HttpStatus.OK);
                } else {
                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.WORKDAY_WITH.getMessage() + requestEntityBody.getName() +
                                    Message.NOT_UPDATE.getMessage());

                }
            } catch (NotFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.WORKDAY_WITH.getMessage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMessage());

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

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteWorkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        Workday requestEntityBody = requestEntity.getBody();
        if (validations.isNullBody(requestEntity) || requestEntityBody.isEmptyName(requestEntityBody)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.FORMAT_REQUEST_WRONG.getMessage());
        } else {
            try {
                workDayService.delete(requestEntityBody);
                ResponseRest responseRest = new ResponseRest(Message.WORKDAY_DELETE.getMessage(), HttpStatus.OK);
                responseEntity = new ResponseEntity<>(responseRest, HttpStatus.OK);
            } catch (NotFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.WORKDAY_WITH.getMessage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMessage());

            } catch (NoSuchElementException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.FORMAT_REQUEST_WRONG.getMessage());

            }
        }
        return responseEntity;
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        ResponseEntity responseEntity = null;
        responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                Message.FORMAT_REQUEST_WRONG.getMessage());

        return responseEntity;
    }


    @PostConstruct
    private void init() {
        EntityWorkday workday8EntityWorkdayH = new EntityWorkday(1L, "Jornada laboral 8H",
                8L, 40L);
        EntityWorkday workday12EntityWorkdayH = new EntityWorkday(2L, "Jornada laboral 12H",
                12L, 60L);
        workDayService.save(workday8EntityWorkdayH);
        workDayService.save(workday12EntityWorkdayH);
    }


}


