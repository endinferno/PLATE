package com.buaa.man.Controller;

import org.springframework.web.bind.annotation.*;

//import java.util.HashMap;

@RestController
@RequestMapping("/api/v2")
public class HelloWordController {
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello DevCloud1.";
    }
}
