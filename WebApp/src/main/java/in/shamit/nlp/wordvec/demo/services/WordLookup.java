package in.shamit.nlp.wordvec.demo.services;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Service;

import in.shamit.nlp.wordvec.VectorManager;
import in.shamit.nlp.wordvec.demo.services.vo.DistResponse;
import in.shamit.nlp.wordvec.demo.services.vo.Word;

@Service( "wordLookupService" )
public class WordLookup {
	String defaultWords[]={"Cathedral","Clear","Android","Apple","Windows","iOS"};
    @GET
    @Path("lookup/")
    @Produces("application/json")
    public String[] lookupWord(@QueryParam("sead") String sead){
    	return VectorManager.getTypeAheadSuggestions(sead).toArray(new String[1]);
    }
    @GET
    @Path("defaultWords/")
    @Produces("application/json")
    public String[] defaultWord(){
    	return defaultWords;
    }
    @GET
    @Path("vector/")
    @Produces("application/json")
    public float[] lookupVector(@QueryParam("word") String word){
    	return VectorManager.getVector(word);
    }
    @GET
    @Path("nearWords/")
    @Produces("application/json")
    public DistResponse[] nearWords(@QueryParam("word") String word){
    	return VectorManager.getNearbyWords(word);
    }
    @GET
    @Path("solvePuzzle/")
    @Produces("application/json")
    public DistResponse[] solvePuzzle(@QueryParam("word1") String word1,@QueryParam("word2") String word2,@QueryParam("word3") String word3){
    	return VectorManager.solveAnalogy(word1, word2, word3);
    }

    @GET
    @Path("solveEquation/")
    @Produces("application/json")
    public DistResponse[] solveEquation(@QueryParam("equation") String eq){
    	return VectorManager.solveEquation(eq);
    }    
    
}
