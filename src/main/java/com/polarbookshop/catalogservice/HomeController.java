package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.config.PolarProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final PolarProperties props;

    public HomeController(PolarProperties props) {
        this.props = props;
    }

    @GetMapping("/")
    public String getGreeting() {
        return props.getGreeting();
    }
}
