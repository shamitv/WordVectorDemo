package in.shamit.nlp.wordvec.WebApp.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingService {
    @GetMapping("/ping/")
    public String doPing(){
    	return "Pong ";
    }
}
