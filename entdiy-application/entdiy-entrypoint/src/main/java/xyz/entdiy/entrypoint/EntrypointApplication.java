package xyz.entdiy.entrypoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@SpringBootApplication
@RestController
@Slf4j
public class EntrypointApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntrypointApplication.class, args);
    }


    @GetMapping("/hello")
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("authentication: {}", authentication);
        return authentication.getName() + Arrays.toString(authentication.getAuthorities().toArray());
    }
}
