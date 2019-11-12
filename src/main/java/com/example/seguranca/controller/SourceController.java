package com.example.seguranca.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/source")
public class SourceController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity findBy() {
        return ResponseEntity.ok("https://github.com/tassiomeneses/seguranca-api || https://github.com/tassiomeneses/seguranca-frontend");
    }

}
