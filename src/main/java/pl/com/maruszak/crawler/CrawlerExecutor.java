package pl.com.maruszak.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.com.maruszak.crawler.PageRetriverInterface.Page;

public class CrawlerExecutor implements CrawlerExecutorInterface {

	private PageRetriverInterface retriever;
	
	private static Logger l = LoggerFactory.getLogger(CrawlerExecutor.class);
	
	public CrawlerExecutor(PageRetriverInterface pr) {
		l.info("Executor initialized...");
		retriever = pr;
	}
	
	public void execute(LinksStore linksStore, ImagesStore imagesStore) {
		l.info("Execute!");
		while (!linksStore.empty()) {
			l.info("Queue is not empty.... retrieveing data");
			Page page = retriever.retrivePageData(linksStore.getToVisit());
			linksStore.addToVisit(page.linkUrls);
			imagesStore.addSet(page.imgUrls);
			linksStore.addVisited(page.url);
			
			l.info("Images store has {} items",imagesStore.size());
		}
		l.info("Link Queue is empty! End of job");
	}
	
	public static CrawlerExecutor factory(PageRetriverInterface pr) {
		return new CrawlerExecutor(pr);
	}
	
}
