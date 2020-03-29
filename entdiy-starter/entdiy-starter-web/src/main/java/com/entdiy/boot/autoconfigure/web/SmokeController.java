package com.entdiy.boot.autoconfigure.web;

import com.entdiy.common.model.ViewResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmokeController {

    @GetMapping("/ping")
    public ViewResult<String> ping() {
        return ViewResult.success("Pong");
    }

}
