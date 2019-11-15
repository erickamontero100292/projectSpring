package com.projectRest.controller;


import com.projectRest.constant.Message;
import com.projectRest.entity.EntityWorkday;
import com.projectRest.error.*;
import com.projectRest.helper.WorkdayHelper;
import com.projectRest.model.Workday;
import com.projectRest.repository.WorkDayRepository;
import com.projectRest.response.ResponseRest;
import com.projectRest.service.WorkDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @Autowired
    WorkDayRepository workDayRepository;
    @Autowired
    WorkDayService workDayService;

    @Autowired
    WorkdayHelper workdayHelper;


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
            responseEntity = getErrorResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), e.getMessage());

        } catch (BadRequestException e) {

            responseEntity = getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.WORKDAY_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());

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

            responseEntity = getErrorResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), Message.WORKDAY_WITH.getMesage() + name + Message.NOT_EXIST.getMesage());
        }

        return responseEntity;
    }

    @PostMapping("/addworkday")
    public ResponseEntity<?> addworkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        if (workdayHelper.validateBodyRequest(requestEntity)) {
            ErrorResponse errorResponse = ErrorResponse.generateError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.WORKDAY_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());
            responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        } else {
            Workday requestEntityBody = requestEntity.getBody();
            try {
                Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());
                if (!workDayServiceByName.emptyWorkday()) {

                    responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() + Message.EXIST.getMesage());

                }
            } catch (WorkdayNotFoundException e) {
                responseEntity = new ResponseEntity<>(workDayService.save(requestEntityBody), HttpStatus.CREATED);
            } catch (NoSuchElementException e) {
                responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() + Message.NOT_EXIST.getMesage());
            }
        }
        return responseEntity;
    }


    @PostMapping("/addListworkday")
    public ResponseEntity<?> addworkdays(RequestEntity<List<Workday>> requestEntity) {
        ResponseEntity responseEntity = null;
        List<Workday> workday = requestEntity.getBody();
        if (workday == null) {

            responseEntity = getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.WORKDAY_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());


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

        if (workdayList == null) {
            return new ResponseEntity<>(new ErrorRestBuilder(Message.NOT_GET_INFORMATION_LIST.getMesage()),
                    HttpStatus.CONFLICT);
        }
        if (workdayList.isEmpty()) {
            return new ResponseEntity<>(new ErrorRestBuilder(Message.NO_EXIST_WORKDAY.getMesage()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(workdayList, HttpStatus.OK);
        }
    }


    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateWorkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        Workday requestEntityBody = requestEntity.getBody();
        if (workdayHelper.validateBodyRequest(requestEntity) || requestEntityBody.isEmptyName(requestEntityBody)) {
            responseEntity = getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    Message.WORKDAY_WITH.getMesage() + Message.FORMAT_REQUEST_WRONG.getMesage());

        } else {
            try {
                Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());
                if (!workdayHelper.isWorkdayNotNull(workDayServiceByName)) {

                    responseEntity = new ResponseEntity<>(workDayService.update(requestEntityBody), HttpStatus.OK);
                } else {
                    responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() + Message.NOT_UPDATE.getMesage());

                }
            } catch (WorkdayNotFoundException e) {
                responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() + Message.NOT_EXIST.getMesage());

            } catch (BadRequestException e) {

                responseEntity = getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.FORMAT_REQUEST_WRONG.getMesage());


            } catch (NoSuchElementException e) {
                responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.NOT_UPDATE.getMesage());

            }
        }
        return responseEntity;
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteWorkday(RequestEntity<Workday> requestEntity) {
        ResponseEntity responseEntity = null;
        if (workdayHelper.validateBodyRequest(requestEntity)) {
            responseEntity = getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.FORMAT_REQUEST_WRONG.getMesage());
        } else {
            Workday requestEntityBody = requestEntity.getBody();
            if (requestEntityBody.isEmptyName(requestEntityBody)) {
                responseEntity = getErrorResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.FORMAT_REQUEST_WRONG.getMesage());

            } else {
                try {
                    responseEntity = processDeleteWorkday(requestEntityBody);
                } catch (WorkdayNotFoundException e) {
                    responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() + Message.NOT_EXIST.getMesage());

                } catch (NoSuchElementException e) {
                    responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.FORMAT_REQUEST_WRONG.getMesage());

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
                responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() + Message.NOT_DELETE.getMesage());

            }

        } else {

            responseEntity = getErrorResponseEntity(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), Message.WORKDAY_WITH.getMesage() + requestEntityBody.getName() + Message.NOT_DELETE.getMesage());

        }
        return responseEntity;
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.generateError(ex, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), Message.FORMAT_REQUEST_WRONG.getMesage());
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }


    @PostConstruct
    private void init() {
        EntityWorkday workday8EntityWorkdayH = new EntityWorkday(1L, "Jornada laboral 8H", 8L, 40L);
        EntityWorkday workday12EntityWorkdayH = new EntityWorkday(2L, "Jornada laboral 12H", 12L, 60L);
        workDayService.save(workday8EntityWorkdayH);
        workDayService.save(workday12EntityWorkdayH);
    }

    private ResponseEntity getErrorResponseEntity(HttpStatus httpStatus, int code, String message) {
        ResponseEntity responseEntity;
        ErrorResponse errorResponse = ErrorResponse.generateError(httpStatus, code, message);
        responseEntity = new ResponseEntity<>(errorResponse, httpStatus);
        return responseEntity;
    }

}


