package in.shamit.nlp.wordvec.demo.services.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import in.shamit.nlp.wordvec.VectorManager;

public class VectorInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		VectorManager.startLoadProcess();
	}

}
