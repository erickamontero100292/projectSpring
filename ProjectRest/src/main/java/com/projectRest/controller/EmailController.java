package com.projectRest.controller;

import com.projectRest.service.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailController {
    @Autowired
    SendEmail sendEmail;
    @GetMapping("/email/{message}")
    public ResponseEntity getWorkdayById(@PathVariable String message) {

        ResponseEntity responseEntity = null;
        sendEmail.sendEmail("@gmail.com","Prueba envio de correo",message);
        responseEntity = new ResponseEntity<>("OK", HttpStatus.OK);
        return responseEntity;
    }
}
