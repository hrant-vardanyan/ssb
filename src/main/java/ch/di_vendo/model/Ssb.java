package ch.di_vendo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
// @Table(indexes = { @Index(name = "kkkkk", columnList = "url") })
public class Ssb {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String pensum;
	private String city;
	private String state;
	@Column(length = 1000)
	private String description;
	private String company;
	private String detailUrl;
	private String publishedDate;
	private long crawledTime;
	private String jobType;
	@Column(length = 1000)
	private String detailText;
	private String fieldOfJob;
	private int isRemovedPost;

	public int getId() {
		return id;
	}

	public Ssb() {

	}

	public Ssb(String title, String pensum, String city, String state, String description, String company,
			String detailUrl, String publishedDate, long crawledTime, String jobType, String fieldOfJob,
			int isRemovedPost, String detailText) {
		this.title = title;
		this.pensum = pensum;
		this.city = city;
		this.description = description;
		this.company = company;
		this.detailUrl = detailUrl;
		this.publishedDate = publishedDate;
		this.crawledTime = crawledTime;
		this.jobType = jobType;
		this.fieldOfJob = fieldOfJob;
		this.isRemovedPost = isRemovedPost;
		this.detailText = detailText;
		this.state = state;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPensum() {
		return pensum;
	}

	public void setPensum(String pensum) {
		this.pensum = pensum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public long getCrawledTime() {
		return crawledTime;
	}

	public void setCrawledTime(long crawledTime) {
		this.crawledTime = crawledTime;
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

	public int isRemovedPost() {
		return isRemovedPost;
	}

	public void setRemovedPost(int isRemovedPost) {
		this.isRemovedPost = isRemovedPost;
	}

	public String getDetailText() {
		return detailText;
	}

	public void setDetailText(String detailText) {
		this.detailText = detailText;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
