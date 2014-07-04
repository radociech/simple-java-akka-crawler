package pl.com.maruszak.crawler;

import java.util.Date;
import java.util.Set;

public interface PageRetriverInterface {

	public Page retrivePageData(String url);
	
	public class Page {
		Set<String> imgUrls;
		Set<String> linkUrls;
		Date visitedAt;
		String url;
		
		Page(String url, Set<String> imgUrls, Set<String> linkUrls) {
			this.url = url;
			this.imgUrls = imgUrls;
			this.linkUrls = linkUrls;
			visitedAt = new Date();
		}
		
		public String getUrl() {
			return this.url;
		}
		
		public Set<String> getImgUrls() {
			return this.imgUrls;
		}
		
		public Set<String> getLinkUrls() {
			return this.linkUrls;
		}
	}
	
}
