package services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundService {
	
	private static ExecutorService exService;
	
	private BackgroundService() { }
	
	public static ExecutorService getExecuterService() {
		if(exService== null)
			exService = Executors.newCachedThreadPool();
		
		return exService;
	}

}
