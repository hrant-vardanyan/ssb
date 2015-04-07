package ch.di_vendo.model;

public class ListViewData {

	private String detailViewUrl;
	private String title;
	private String city;
	private String state;
	private String jobType;
	private String fieldOfJob;
	private String publishedDate;
	private String description;

	public ListViewData(String detailViewUrl, String title, String city, String jobType, String fieldOfJob,
			String publishedDate, String description, String state) {
		this.detailViewUrl = detailViewUrl;
		this.title = title;
		this.city = city;
		this.jobType = jobType;
		this.fieldOfJob = fieldOfJob;
		this.publishedDate = publishedDate;
		this.description = description;
		this.state = state;
	}

	public String getDetailViewUrl() {
		return detailViewUrl;
	}

	public void setDetailViewUrl(String detailViewUrl) {
		this.detailViewUrl = detailViewUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getFieldOfJob() {
		return fieldOfJob;
	}

	public void setFieldOfJob(String fieldOfJob) {
		this.fieldOfJob = fieldOfJob;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
