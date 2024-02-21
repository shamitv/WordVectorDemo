package in.shamit.nlp.wordvec.demo.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Service;

@Service( "pingService" )
public class PingService {
    @GET
    @Path("ping/")
    @Produces("application/json")
    public String doPing(@QueryParam("ping") String ping){
    	return "Pong "+ping;
    }
}
