package com.entity;

import java.util.Date;

/**
 * Video entity. @author MyEclipse Persistence Tools
 */

public class Video implements java.io.Serializable {

	// Fields

	private Integer videoId;
	private VideoTag videoTag;
	private String videoName;
	private String videoUploadEditer;
	private Date videoUploadTime;
	private Integer videoDownloadCount;

	// Constructors

	/** default constructor */
	public Video() {
	}

	/** full constructor */
	public Video(VideoTag videoTag, String videoName, String videoUploadEditer,
			Date videoUploadTime, Integer videoDownloadCount) {
		this.videoTag = videoTag;
		this.videoName = videoName;
		this.videoUploadEditer = videoUploadEditer;
		this.videoUploadTime = videoUploadTime;
		this.videoDownloadCount = videoDownloadCount;
	}

	// Property accessors

	public Integer getVideoId() {
		return this.videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public VideoTag getVideoTag() {
		return this.videoTag;
	}

	public void setVideoTag(VideoTag videoTag) {
		this.videoTag = videoTag;
	}

	public String getVideoName() {
		return this.videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoUploadEditer() {
		return this.videoUploadEditer;
	}

	public void setVideoUploadEditer(String videoUploadEditer) {
		this.videoUploadEditer = videoUploadEditer;
	}

	public Date getVideoUploadTime() {
		return this.videoUploadTime;
	}

	public void setVideoUploadTime(Date videoUploadTime) {
		this.videoUploadTime = videoUploadTime;
	}

	public Integer getVideoDownloadCount() {
		return this.videoDownloadCount;
	}

	public void setVideoDownloadCount(Integer videoDownloadCount) {
		this.videoDownloadCount = videoDownloadCount;
	}

}