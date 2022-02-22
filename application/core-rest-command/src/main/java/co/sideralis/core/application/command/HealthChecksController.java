package co.sideralis.core.application.command;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthChecksController {

    @GetMapping(value = "/")
    public String find() {
        return "OK";
    }

}