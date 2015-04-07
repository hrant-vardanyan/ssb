package ch.di_vendo.model;


public class DetailViewData {

	private String company;
	private String pensum;
	private String detailText;
	private String detailUrl;
	private long crawledTime;

	public DetailViewData(String company, long crawledTime, String pensum, String detailText, String detailUrl) {
		this.company = company;
		this.pensum = pensum;
		this.detailText = detailText;
		this.detailUrl = detailUrl;
		this.crawledTime = crawledTime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPensum() {
		return pensum;
	}

	public void setPensum(String pensum) {
		this.pensum = pensum;
	}

	public String getDetailText() {
		return detailText;
	}

	public void setDetailText(String detailText) {
		this.detailText = detailText;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public long getCrawledTime() {
		return crawledTime;
	}

	public void setCrawledTime(long crawledTime) {
		this.crawledTime = crawledTime;
	}

}
