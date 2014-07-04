package pl.com.maruszak.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.com.maruszak.crawler.actor.ManagerActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

public class AkkaCrawlerExecutor implements CrawlerExecutorInterface {

	
	private static Logger l = LoggerFactory.getLogger(AkkaCrawlerExecutor.class);
	
	@SuppressWarnings("serial")
	@Override
	public void execute(final LinksStore linksStore, final ImagesStore imagesStore) {
		l.info("Start execution!");
		ActorSystem system = ActorSystem.create("Crawler");
		l.info("Akka system Crawler created");
		ActorRef managerActor = system.actorOf(new Props(new UntypedActorFactory() {
		      public UntypedActor create() {
		        return new ManagerActor(linksStore, imagesStore);
		      }
		    }), "manager");
		
		
		l.info("Manager Actor created!");
		//start actor with message
		l.info("Telling managerActor to star!");
		managerActor.tell(new ManagerActor.StartMessage());
		
		
		l.info("ManagerActor told to start");
		
	}

}
