package com.opl.cbdc.utils.pdf;

/**
 * @author Khyati
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfRequest {

	private String              template;
	private String              author;
	private String              creator;
	private List<String>        keywords;
	private String              producer;
	private String              subject;
	private String              title;
	private Map<String, Object> parameterMap;
	private String	baseUrl;

	public PdfRequest() {
		parameterMap = new HashMap<String, Object>();
		keywords = new ArrayList<String>();
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String value) {
		template = value;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String value) {
		author = value;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String value) {
		creator = value;
	}

	public List<String> keywords() {
		return keywords;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String value) {
		producer = value;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String value) {
		subject = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		title = value;
	}

	public Map<String, Object> parameters() {
		return parameterMap;
	}
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
}
