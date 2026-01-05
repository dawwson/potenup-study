package org.ohgiraffers.security.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControllerTest {

    @RequestMapping(value = "/api/users/test", method = RequestMethod.GET)
    public ResponseEntity<String> userTest() {
        return ResponseEntity.ok("User Test Successful");
    }

    @RequestMapping(value = "/api/admin/test", method = RequestMethod.GET)
    public ResponseEntity<String> adminTest() {
        return ResponseEntity.ok("Admin Test Successful");
    }
}
