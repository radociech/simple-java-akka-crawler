package pl.com.maruszak.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PageRetriver implements PageRetriverInterface {
	
	private final Logger logger = LoggerFactory.getLogger(PageRetriver.class);
	
	 
	public static PageRetriverInterface factory() {
		return new PageRetriver();
	}
	
	private String getHtml(String url) {
		org.jsoup.Connection.Response response;
		try {
			response = Jsoup.connect(url)
					.header("Content-Type", "text/html")
					.execute();
			logger.info("Response from url {} with status {}", url, response.statusCode());
			return response.body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			return "";
		}
	}
	 
	
	private Document parseHtml(String html) {
		logger.info("Parsing html...");
		return Jsoup.parse(html);
	}
	 

	private Set<String> getUniqueAttributeValues(
			Document document, 
			String tagName, 
			String attributeKey
			) {
		logger.info("Trying to find tags <{}> and attributes {}", tagName, attributeKey);
		Set<String> out = new HashSet<String>();
		for(Element e : document.getElementsByTag(tagName)) {
			String url = e.attr("abs:"+attributeKey);
			if(!url.isEmpty()) {
				logger.info("Found url {}", url);
				out.add(url);
			}
		}
		return out;
	}
	
	
	private Set<String> getImagesUrls(Document document) {
		return getUniqueAttributeValues(document, "img", "src");
	}
	
	private Set<String> getLinksUrls(Document document) {
		return getUniqueAttributeValues(document, "a", "href");
	}

	@Override
	public Page retrivePageData(String url) {
		logger.info("Start retrevieing data from url {}", url);
		Document document = parseHtml(getHtml(url));
		return new Page(
				url,
				getImagesUrls(document),
			    getLinksUrls(document)
			    );
	}

}
