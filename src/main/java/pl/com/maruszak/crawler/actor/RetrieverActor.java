package pl.com.maruszak.crawler.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.com.maruszak.crawler.PageRetriver;
import pl.com.maruszak.crawler.PageRetriverInterface;
import pl.com.maruszak.crawler.PageRetriverInterface.Page;
import akka.actor.UntypedActor;

public class RetrieverActor extends UntypedActor {

	private PageRetriverInterface pageRetriever = PageRetriver.factory();
	
	private static Logger l = LoggerFactory.getLogger(RetrieverActor.class);
	
	
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String) {
			l.info("Retriever Actor got String message, url : {}", message);
			Page page = pageRetriever.retrivePageData((String)message);
			l.info("Page retrieved! Images found : {}", page.getImgUrls().size());
			l.info("Tell ManagerActor about page from url {}", page.getUrl());
			getSender().tell(page, getSelf());
			//l.info("Sender terminated? {}",getSender().isTerminated());
			//l.info("Should I do something more...? Waiting....");
		} else {
			l.info("Cannot handle the message {}", message);
			unhandled(message);
		}
		
	}

}
