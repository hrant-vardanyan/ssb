package ch.di_vendo.crawler;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ch.di_vendo.model.DetailViewData;
import ch.di_vendo.model.ListViewData;
import ch.di_vendo.utils.Constants;

public class SsbCrawler {

	private static final Logger LOGGER = Logger.getLogger(SsbCrawler.class);

	/**
	 * Gets data from job entries detail page.
	 * 
	 * @param detailViewUrl
	 *            URL of job's detail page
	 * @return DetailViewData Contains detail data about job
	 * @throws IOException
	 */
	public DetailViewData getDetailViewData(String detailViewUrl) throws IOException {
		// Retrieve detail page HTML
		Document docOfPage = Jsoup.connect(detailViewUrl).ignoreContentType(true).userAgent(Constants.BROWSER)
				.timeout(Constants.TIMEOUT).get();
		// Collecting data from HTML
		String company = "";

		return null;
	}

	/**
	 * Gets the list of ListViewData. ListViewData contains detail page URL and
	 * data from search results for job entry.
	 * 
	 * @param pageUrl
	 *            URL of page to crawl
	 * @return List of ListViewData
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<ListViewData> getListViewDataInPage(String pageUrl) throws IOException, ParseException {

		List<ListViewData> listViewDataList = new ArrayList<>();

		// Retrieve HTML content for URL
		Document docOfPage = Jsoup.connect(pageUrl).ignoreContentType(true).userAgent(Constants.BROWSER)
				.timeout(Constants.TIMEOUT).get();

		

		return listViewDataList;
	}

	

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public int endPage() throws IOException {

		int endPage = 1;
		
			

		return endPage;

	}

}
