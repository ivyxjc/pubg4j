package xyz.ivyxjc.pubg4j.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ivyxjc
 * @since 5/10/2018
 */
@RestController
public class SimpleController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
