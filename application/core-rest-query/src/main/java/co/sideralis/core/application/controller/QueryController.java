package co.sideralis.core.application.controller;

import co.com.sofka.application.ApplicationQueryExecutor;
import co.com.sofka.domain.generic.ViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class QueryController {

    @Autowired
    private ApplicationQueryExecutor queryHandleExecutor;

    @GetMapping(value = "find/{path}")
    public List<ViewModel> find(@PathVariable("path") String path, @RequestParam Map<String, String> allParams) {
        return queryHandleExecutor.find(path, allParams);
    }

    @GetMapping(value = "get/{path}")
    public ViewModel get(@PathVariable("path") String path, @RequestParam Map<String, String> allParams) {
        return queryHandleExecutor.get(path, allParams);
    }
}