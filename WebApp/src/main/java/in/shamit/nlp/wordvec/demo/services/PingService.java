package in.shamit.nlp.wordvec.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service( "pingService" )
public class PingService {
    @GetMapping("ping/")
    public String doPing(@RequestParam String ping){
    	return "Pong "+ping;
    }
}
