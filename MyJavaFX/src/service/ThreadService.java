package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 所有的 Thread 服務/申請區
public class ThreadService {
	private static ThreadService _instance = new ThreadService();
	
	private ThreadService() {
		
	}
	
	public static ThreadService getInstance() {
		return _instance;
	}
	
	public ExecutorService socketThreadPool = Executors.newFixedThreadPool(10);
	
}
