package com.buaa.man.Controller;

import org.springframework.web.bind.annotation.*;

//import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
public class GreetingController {
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello DevCloud.";
    }

//    @PostMapping("/greeting")
//    public String greeting(@RequestBody HashMap<String, String> params) {
//        return "Hello DevOps. Welcome " + params.get("name");
    //}
}