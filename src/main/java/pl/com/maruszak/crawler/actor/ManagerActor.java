package pl.com.maruszak.crawler.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.com.maruszak.akka.tutorial.first.Pi.Worker;
import pl.com.maruszak.crawler.AkkaCrawlerExecutor;
import pl.com.maruszak.crawler.ImagesStore;
import pl.com.maruszak.crawler.LinksStore;
import pl.com.maruszak.crawler.PageRetriverInterface.Page;
import scala.Option;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class ManagerActor extends UntypedActor {

	private ImagesStore imagesStore;
	private LinksStore linksStore;
	
	private final ActorRef workerRouter;
	
	private static Logger l = LoggerFactory.getLogger(ManagerActor.class);
	
	private int numberOfWorkers = 100;
	
	public ManagerActor(LinksStore linksStore, ImagesStore imagesStore) {
		this.imagesStore = imagesStore;
		this.linksStore = linksStore;
		
		l.info("Creating ManagerActor");
		
		workerRouter = this.getContext().actorOf(new Props(RetrieverActor.class)
			.withRouter(new RoundRobinRouter(this.numberOfWorkers))
			,
		        "workerRouter");
		l.info("Round robin router with {} workers created!",this.numberOfWorkers);
	}
	
	@Override
	public void preStart() {
		// TODO Auto-generated method stub
		super.preStart();
		l.info("Im in preStart method...");
	}
	
	@Override
	public void preRestart(Throwable reason, Option<Object> message) {
		// TODO Auto-generated method stub
		super.preRestart(reason, message);
		l.info("Im in preRestart");
	}
	
	@Override
	public void postStop() {
		// TODO Auto-generated method stub
		super.postStop();
		l.info("postStop");
		l.info("Found {} image", imagesStore.size());
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		
		l.info("onReceive...");
		if(message instanceof Page) {
			l.info("Got message about Page!");
			Page p = (Page)message;
			linksStore.addVisited(p.getUrl());
			linksStore.addToVisit(p.getLinkUrls());
			imagesStore.addSet(p.getImgUrls());
			l.info("Urls indexed, tell sender to visit another url");
			
			if(imagesStore.size() > 1000) {
				l.info("system shutdown!|");
				context().system().shutdown();
			} else {
				l.info("Total images found: {}", imagesStore.size());
				for (int i = 0; i <= this.numberOfWorkers; i++) {
					workerRouter.tell(linksStore.getToVisit(), getSelf());
				}
			}
			
			
		} else if(message instanceof StartMessage) {
			l.info("ManagerActor got StartMessage!");
			l.info("Sending Worker url");
			for (int i = 0; i <= this.numberOfWorkers; i++) {
				workerRouter.tell(linksStore.getToVisit(), getSelf());
			}
		} else {
			l.info("Cannot handle the message!");
			unhandled(message);
		}
	}
	
	public static class StartMessage {}

}
