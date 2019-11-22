package com.projectRest.controller;


import com.projectRest.constant.Message;
import com.projectRest.entity.EntityWorkday;
import com.projectRest.error.*;
import com.projectRest.helper.Validations;
import com.projectRest.helper.WorkdayHelper;
import com.projectRest.model.Workday;
import com.projectRest.repository.WorkDayRepository;
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

import com.projectRest.error.ErrorResponseEntity;

@RestController
@RequestMapping("/api")
public class WorkDayController {
    private static final Logger logger = LogManager.getLogger(WorkDayController.class);
    @Autowired
    WorkDayRepository workDayRepository;
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
        } catch (WorkdayNotFoundException e) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), e.getMessage());
            logger.debug("WorkdayNotFoundException - -");

        } catch (BadRequestException e) {

            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.WORKDAY_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());
            logger.debug("BadRequestException - -");
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
        } catch (WorkdayNotFoundException e) {

            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                    Message.WORKDAY_WITH.getMesage() + name + Message.NOT_EXIST.getMesage());
        }

        return responseEntity;
    }

    @PostMapping("/addworkday")
    public ResponseEntity<?> addWorkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        if (validations.validateBodyRequest(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.WORKDAY_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());


        } else {
            Workday requestEntityBody = requestEntity.getBody();
            try {
                Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());
                if (!workDayServiceByName.emptyWorkday()) {

                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() +
                                    Message.EXIST.getMesage());

                }
            } catch (WorkdayNotFoundException e) {
                responseEntity = new ResponseEntity<>(workDayService.save(requestEntityBody), HttpStatus.CREATED);
            } catch (NoSuchElementException e) {
                responseEntity =ErrorResponseEntity. getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMesage());
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
                    Message.WORKDAY_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());


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
                    Message.NO_EXIST_WORKDAY.getMesage());
        } else {
            responseEntity = new ResponseEntity<>(workdayList, HttpStatus.OK);
        }

        return responseEntity;
    }


    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateWorkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        Workday requestEntityBody = requestEntity.getBody();
        if (validations.validateBodyRequest(requestEntity) || requestEntityBody.isEmptyName(requestEntityBody)) {
            responseEntity =ErrorResponseEntity. getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.WORKDAY_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());

        } else {
            try {
                Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());
                if (!workdayHelper.isWorkdayNotNull(workDayServiceByName)) {

                    responseEntity = new ResponseEntity<>(workDayService.update(requestEntityBody), HttpStatus.OK);
                } else {
                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() +
                                    Message.NOT_UPDATE.getMesage());

                }
            } catch (WorkdayNotFoundException e) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() +
                                Message.NOT_EXIST.getMesage());

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

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteWorkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        if (validations.validateBodyRequest(requestEntity)) {
            responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.FORMAT_REQUEST_WRONG.getMesage());
        } else {
            Workday requestEntityBody = requestEntity.getBody();
            if (requestEntityBody.isEmptyName(requestEntityBody)) {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                        Message.FORMAT_REQUEST_WRONG.getMesage());

            } else {
                try {
                    responseEntity = processDeleteWorkday(requestEntityBody);
                } catch (WorkdayNotFoundException e) {
                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() +
                                    Message.NOT_EXIST.getMesage());

                } catch (NoSuchElementException e) {
                    responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                            Message.FORMAT_REQUEST_WRONG.getMesage());

                }
            }
        }
        return responseEntity;
    }

    private ResponseEntity<?> processDeleteWorkday(Workday requestEntityBody) {
        Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());
        ResponseEntity responseEntity = null;
        if (!workdayHelper.isWorkdayNotNull(workDayServiceByName)) {
            boolean isDelete = workDayService.delete(workDayServiceByName);
            if (isDelete) {
                ResponseRest responseRest = new ResponseRest(Message.WORKDAY_DELETE.getMesage(), HttpStatus.OK);
                responseEntity = new ResponseEntity<>(responseRest, HttpStatus.OK);
            } else {
                responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                        Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() +
                                Message.NOT_DELETE.getMesage());

            }

        } else {

            responseEntity =ErrorResponseEntity.getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(),
                    Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() +
                            Message.NOT_DELETE.getMesage());

        }
        return responseEntity;
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        ResponseEntity responseEntity = null;
        responseEntity = ErrorResponseEntity.getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                Message.FORMAT_REQUEST_WRONG.getMesage());

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


