package in.shamit.nlp.wordvec;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.shamit.nlp.wordvec.WebApp.demo.Config;
import in.shamit.nlp.wordvec.WebApp.demo.services.vo.DistResponse;
import in.shamit.nlp.wordvec.WebApp.demo.services.vo.Word;

public class VectorManager {
	

	static Logger log = Logger.getLogger("main");
	static Map<String, MathVector> vectors=new ConcurrentHashMap<>();

	static File dataDir=null;
	static File distFile=null;
	static Pattern invalidWordPtrn=Pattern.compile("[^a-zA-Z_0-9\\\\/. ]");
	static float wordDist(String word1,String word2){
		MathVector v1=vectors.get(word1);
		MathVector v2=vectors.get(word2);
		if(v1!=null && v2 != null){
			return v1.distanceTo(v2);
		}else{
			throw new RuntimeException("Null vector for a word");
		}
	}
	
	static void calcAllDist(){
		Set<String> vocab=vectors.keySet();
		vocab.stream()
		.parallel()
		.forEach(word->calcDists(word));
	}
	
	static AtomicLong distCount=new AtomicLong();
	static long distScale=(1000*1000);
	static void calcDists(String word) {
		Map <Long,List<String>> wordDists = new HashMap<>();
		if(distCount.incrementAndGet() %10000==0){
			log.info(distCount +" :: distances calc. Current word :: "+word);
		}
		Set<String> vocab=vectors.keySet();
		vocab.stream()
		.parallel()
		.forEach(w->{
			if(!w.equals(word)){
				float rawDist=calcDist(word, w);
				long dist=(long)(rawDist*(float)distScale);
				if(wordDists.containsKey(w)){
					List <String> neighbourList = wordDists.get(w);
					neighbourList.add(w);
				}else{
					List <String> neighbourList=new ArrayList<>();
					neighbourList.add(w);
					wordDists.put(dist, neighbourList);
				}				
			}			
		});
		
/*		for(String w:vocab){
			if(!w.equals(word)){
				float rawDist=calcDist(word, w);
				long dist=(long)(rawDist*(float)distScale);
				if(wordDists.containsKey(w)){
					List <String> neighbourList = wordDists.get(w);
					neighbourList.add(w);
				}else{
					List <String> neighbourList=new ArrayList<>();
					neighbourList.add(w);
					wordDists.put(dist, neighbourList);
				}				
			}
		}*/
		TreeSet<Long> distsSet=new TreeSet<>(wordDists.keySet());
		Long[] dists=distsSet.toArray(new Long[distsSet.size()]);
		//log.info(word +" :: nearest dist "+dists[0]+" words :: "+wordDists.get(dists[0]));
		List <String> lines=new ArrayList<>();
		for(int i=1;i<50 && i < dists.length;i++){
			//log.info(word +"\t\t :: next dist "+dists[i]+" words :: "+wordDists.get(dists[i]));
			long distance=dists[i];
			for(String w:wordDists.get(dists[i])){
				String line=word+"\t"+distance+"\t"+w;
				lines.add(line);
			}
		}
		synchronized(distFile){
			try {
				Files.write(distFile.toPath(), lines, StandardOpenOption.APPEND);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		//log.info(word +" :: farthest dist "+dists[dists.length-1]);
	}
	
	
	static float calcDist(String word1, String word2){
		MathVector v1=vectors.get(word1);
		MathVector v2=vectors.get(word2);
		if(v1!=null && v2 != null){
			float ret=v1.distanceTo(v2); 
			return ret;
		}else{
			if(v1==null){
				throw new RuntimeException("Missing vector for "+ word1);
			}else{
				throw new RuntimeException("Missing vector for "+ word2);
			}
		}
	}
	
	private static float calcDist(float[] vect, String w) {
		MathVector v1= new MathVector(vect);
		MathVector v2=vectors.get(w);
		if(v1!=null && v2 != null){
			float ret=v1.distanceTo(v2); 
			return ret;
		}else{
			if(vect==null){
				throw new RuntimeException("Vector can't be null");
			}else{
				throw new RuntimeException("Missing vector for "+ w);
			}
		}

	}	
	
	private static float calcDist(float[] vect1, float[] vect2) {
		if(vect1!=null && vect2 != null){
			MathVector v1= new MathVector(vect1);
			MathVector v2= new MathVector(vect2);
			float ret=v1.distanceTo(v2); 
			return ret;
		}else{
			if(vect1==null){
				throw new RuntimeException("Vector can't be null");
			}else{
				throw new RuntimeException("Vector can't be null");
			}
		}

	}	

	
	static long lCount=0;
	static void loadVectors(String vectorFilepath) throws IOException{
		File vecFile=new File(vectorFilepath);
		log.info("Loading vectors from "+vectorFilepath);
		Files.lines(vecFile.toPath()).parallel().forEach(l->{
			String cols[]=l.split("\\s");
			if(cols.length>2){
				String word=cols[0];
				if(isValidWord(word)){
					lCount++;
					float []vector=new float[cols.length-1];
					for(int i=1;i<cols.length;i++){
						vector[i-1]=Float.parseFloat(cols[i]);
					}
					vectors.put(word, new MathVector(vector));					
				}
			}
			if(lCount%100000==0){
				log.info(lCount + " lines processed ");
			}			
		});
		Set<String> vocab=null;
		vocab=vectors.keySet();
		log.info(vocab.size() + " words in vocab");		
	}
	
	
	private static boolean isValidWord(String word) {
		if(word.length()==1){
			return true;
		}else{
			Matcher m=invalidWordPtrn.matcher(word);
			if(m.find()){
				return false; 
			}else{
				if(word.length()>12){
					return false;	
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		File baseDir=new File("L:\\work\\nlp\\datasets\\Word2Vec\\StanFord_Wikipedia_Gigaword_5_Glove\\glove.6B\\");
		File vecFile=new File(baseDir,"glove.6B.300d.txt");
		dataDir=new File(vecFile.getAbsolutePath()+"_data");
		dataDir.mkdir();
		distFile = new File(dataDir,"wordDists.txt");
		Files.write(distFile.toPath(), new byte[0]);
		loadVectors(vecFile.getAbsolutePath());
		calcAllDist();
		log.info(" Calculated all distances ");
	}

	public static List<String> getTypeAheadSuggestions(String sead){ 
		List<String>ret=new ArrayList<>();
		Set<String> words=vectors.keySet();
		int maxSuggestions=100;
		int suggestions=0;
		for(String w:words){
			if(w.toLowerCase().startsWith(sead.toLowerCase())){
				ret.add(w);
				suggestions++;
				if(suggestions>=maxSuggestions){
					break;
				}
			}
		}
		return ret;
	}
	
	public static void startLoadProcess(){
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				String fileName=Config.VECTOR_FILE_GLOVE_42B;
				File vecFile=new File(fileName);
				System.out.println("Loading word vectors from :: "+vecFile.getAbsolutePath());
				try {
					loadVectors(vecFile.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});
		t.start();
	}

	public static float[] getVector(String word) {
		if(vectors.containsKey(word)){
			return vectors.get(word).getValues();
		}else{
			return new float[0];
		}
	}

	public static DistResponse[] getNearbyWords(float[] vect){
		List<DistResponse> ret=new ArrayList<>();
		Set<String> vocab=vectors.keySet();
		Map <Long,List<String>> wordDists = new HashMap<>();
		for(String w:vocab){
				float rawDist=calcDist(vect, w);
				long dist=(long)(rawDist*(float)distScale);
				if(wordDists.containsKey(w)){
					List <String> neighbourList = wordDists.get(w);
					neighbourList.add(w);
				}else{
					List <String> neighbourList=new ArrayList<>();
					neighbourList.add(w);
					wordDists.put(dist, neighbourList);
				}				

		}
		TreeSet<Long> distsSet=new TreeSet<>(wordDists.keySet());
		Long[] dists=distsSet.toArray(new Long[distsSet.size()]);
		for(int i=1;i<50 && i < dists.length;i++){
			for(String w:wordDists.get(dists[i])){
				Word wret = new Word();
				wret.setWord(w);
				wret.setVector(vectors.get(w).getValues());
				DistResponse dr = new DistResponse();
				dr.setDistance( new MathVector(vect).distanceTo(vectors.get(w)));
				dr.setWord(wret);
				ret.add(dr);
			}
		}
		return ret.toArray(new DistResponse[1]);		
	}
	


	public static DistResponse[] getNearbyWords(String word) {
		List<DistResponse> ret=new ArrayList<>();
		Set<String> vocab=vectors.keySet();
		Map <Long,List<String>> wordDists = new HashMap<>();
		for(String w:vocab){
			if(!w.equals(word)){
				float rawDist=calcDist(word, w);
				long dist=(long)(rawDist*(float)distScale);
				if(wordDists.containsKey(w)){
					List <String> neighbourList = wordDists.get(w);
					neighbourList.add(w);
				}else{
					List <String> neighbourList=new ArrayList<>();
					neighbourList.add(w);
					wordDists.put(dist, neighbourList);
				}				
			}
		}
		TreeSet<Long> distsSet=new TreeSet<>(wordDists.keySet());
		Long[] dists=distsSet.toArray(new Long[distsSet.size()]);
		for(int i=1;i<10 && i < dists.length;i++){
			for(String w:wordDists.get(dists[i])){
				Word wret = new Word();
				wret.setWord(w);
				wret.setVector(vectors.get(w).getValues());
				DistResponse dr = new DistResponse();
				dr.setDistance(vectors.get(word).distanceTo(vectors.get(w)));
				dr.setWord(wret);
				ret.add(dr);
			}
		}
		return ret.toArray(new DistResponse[1]);
	}
	
	public static DistResponse[] solveAnalogy(String w1,String w2, String w3){
		List<DistResponse> ret=new ArrayList<>();
		if((vectors.containsKey(w1))&&(vectors.containsKey(w2))&&(vectors.containsKey(w3))){
			MathVector v1=vectors.get(w1);
			MathVector v2=vectors.get(w2);
			MathVector v3=vectors.get(w3);
			
			MathVector minus= v1.minus(v2);
			MathVector target = v3.minus(minus);
			DistResponse[] candidates=getNearbyWords(target.getValues());
			for(DistResponse c:candidates){
				if(!(c.getWord().getWord().equals(w3))){
					ret.add(c);
				}
			}
		}
		return ret.toArray(new DistResponse[0]);
	}
	
	public static DistResponse[] solveEquation(String eq){
		EquationSolver solver = new EquationSolver();
		return solver.solveEuation(eq);
	}
}