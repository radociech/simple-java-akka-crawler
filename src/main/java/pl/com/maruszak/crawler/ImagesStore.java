package pl.com.maruszak.crawler;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ImagesStore {
	
	private static Logger logger = LoggerFactory.getLogger(ImagesStore.class);

	private Set<String> urls = new HashSet<String>();
	
	public void add(String url) {
		logger.info("Adding image {}", url);
		this.urls.add(url);
	}
	
	public void addSet(Set<String> urls) {
		logger.info("Adding {} new images", urls.size());
		this.urls.addAll(urls);
	}
	
	public int size() {
		return urls.size();
	}
	
}
