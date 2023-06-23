package controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = "*")
public class TestController {
    @GetMapping("/msg")
    public String getMessa()
    {
        return "Hello";
    }
}
