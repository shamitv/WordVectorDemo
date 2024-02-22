package in.shamit.nlp.wordvec.WebApp;

import in.shamit.nlp.wordvec.VectorManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class WordVectorDemo {

	public static void main(String[] args) {
		SpringApplication.run(WordVectorDemo.class, args);
		try{
			VectorManager.startLoadProcess();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

}
