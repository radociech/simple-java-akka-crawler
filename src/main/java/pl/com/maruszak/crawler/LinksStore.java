package pl.com.maruszak.crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinksStore {

	
	private static Logger logger = LoggerFactory.getLogger(LinksStore.class);
		
	
	private Set<String> visited = new HashSet<String>();
	
	private List<String> toVisit = new LinkedList<String>();
	
	public Set<String> getPortion(int size) {
		Set<String> preparedPortion = new HashSet<String>();
		for (int i = 0; i <= size; i++) {
			if(toVisit.isEmpty()) {
				break;
			}
			preparedPortion.add(toVisit.remove(0));
		}
		return preparedPortion;
	}
	public boolean empty() {
		return toVisit.isEmpty();
	}
	
	public String getToVisit() {
		if(toVisit.isEmpty()) {
			return null;
		}
		return this.toVisit.remove(0);
	}
	
	private boolean isVisited(String url) {
		if(visited.contains(url)) {
			return true;
		}
		return false;
	}
	
	public void addVisited(String url) {
		visited.add(url);
	}
	
	public void addVisitedSet(Set<String> urls) {
		this.visited.addAll(urls);
	}
	
	public void addToVisit(Set<String> urls) {
		for(String url : urls) {
			this.addToVisit(url);
		}
	}
	
	public void addToVisit(String url) {
		if(!isVisited(url)) {
			logger.info("Add url to visit {}", url);
			toVisit.add(url);
		} else {
			logger.info("Url is visited {}", url);
		}
	}
	
}
