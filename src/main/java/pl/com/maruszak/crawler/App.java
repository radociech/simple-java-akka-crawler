package pl.com.maruszak.crawler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		
		
		String initUrl = "http://audiovis.nac.gov.pl";
		
		LinksStore linksStore = new LinksStore();
		ImagesStore imagesStore = new ImagesStore();
		
		//init url;
		linksStore.addToVisit(initUrl);
		
		CrawlerExecutorInterface e = new AkkaCrawlerExecutor();
		e.execute(linksStore, imagesStore);
		
		
	}

}
