package in.shamit.nlp.wordvec.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingService {
    @GetMapping("/api/ping/")
    public String doPing(){
    	return "Pong ";
    }
}
