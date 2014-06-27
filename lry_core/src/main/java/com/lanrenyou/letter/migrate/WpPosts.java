package com.lanrenyou.letter.migrate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WpPosts {

	private Integer id;
	private Integer post_author;
	private String post_author_name;
	private Integer post_author_id_new;
	private String post_content;
	private String post_title;
	private String post_excerpt;
	private Timestamp post_date;
	private Timestamp post_modified;
	private int post_parent;
	private List<WpPosts> childlists = new ArrayList<WpPosts>();
	private int post_views_count;
	private String post_attc_url;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPost_author() {
		return post_author;
	}
	public void setPost_author(Integer post_author) {
		this.post_author = post_author;
	}
	public String getPost_author_name() {
		return post_author_name;
	}
	public void setPost_author_name(String post_author_name) {
		this.post_author_name = post_author_name;
	}

	public Integer getPost_author_id_new() {
		return post_author_id_new;
	}
	public void setPost_author_id_new(Integer post_author_id_new) {
		this.post_author_id_new = post_author_id_new;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_excerpt() {
		return post_excerpt;
	}
	public void setPost_excerpt(String post_excerpt) {
		this.post_excerpt = post_excerpt;
	}
	public Timestamp getPost_modified() {
		return post_modified;
	}
	public void setPost_modified(Timestamp post_modified) {
		this.post_modified = post_modified;
	}
	public int getPost_parent() {
		return post_parent;
	}
	public void setPost_parent(int post_parent) {
		this.post_parent = post_parent;
	}
	public List<WpPosts> getChildlists() {
		return childlists;
	}
	public void setChildlists(List<WpPosts> childlists) {
		this.childlists = childlists;
	}
	public Timestamp getPost_date() {
		return post_date;
	}
	public void setPost_date(Timestamp post_date) {
		this.post_date = post_date;
	}
	public int getPost_views_count() {
		return post_views_count;
	}
	public void setPost_views_count(int post_views_count) {
		this.post_views_count = post_views_count;
	}
	public String getPost_attc_url() {
		return post_attc_url;
	}
	public void setPost_attc_url(String post_attc_url) {
		this.post_attc_url = post_attc_url;
	}
	
}
