package in.shamit.nlp.wordvec.WebApp.demo.services;

import in.shamit.nlp.wordvec.WebApp.demo.services.vo.DistResponse;
import org.springframework.stereotype.Service;

import in.shamit.nlp.wordvec.VectorManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wordLookupService/")
public class WordLookup {
	String defaultWords[]={"Cathedral","Clear","Android","Apple","Windows","iOS"};
    
    @GetMapping("lookup")
    public String[] lookupWord(@RequestParam String sead){
    	return VectorManager.getTypeAheadSuggestions(sead).toArray(new String[1]);
    }
    
    @GetMapping("defaultWords")
    public String[] defaultWord(){
    	return defaultWords;
    }
    
    @GetMapping("vector")
    public float[] lookupVector(@RequestParam String word){
    	return VectorManager.getVector(word);
    }
    
    @GetMapping("nearWords")
    public DistResponse[] nearWords(@RequestParam String word){
    	return VectorManager.getNearbyWords(word);
    }
    
    @GetMapping("solvePuzzle")
    public DistResponse[] solvePuzzle(@RequestParam String word1,@RequestParam String word2,@RequestParam String word3){
    	return VectorManager.solveAnalogy(word1, word2, word3);
    }

    
    @GetMapping("solveEquation")
    public DistResponse[] solveEquation(@RequestParam String eq){
    	return VectorManager.solveEquation(eq);
    }    
    
}
