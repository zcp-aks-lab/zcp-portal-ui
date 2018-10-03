package com.skcc.cloudz.zcp.portal.management.namespace.vo;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretTlsVO {
	private String name;

	private String type = "kubernetes.io/tls";

	@JsonIgnore
	private MultipartFile crt;
	
	@JsonIgnore
	private MultipartFile key;
	
	@JsonProperty("file")
    private String crtFileName;
	
	@JsonProperty("file")
    private String keyFileName;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MultipartFile getKey() {
		return key;
	}

	public void setKey(MultipartFile key) {
		this.key = key;
	}

	public String getKeyFileName() {
		return keyFileName;
	}

	public void setKeyFileName(String keyFileName) {
		this.keyFileName = keyFileName;
	}

	public MultipartFile getCrt() {
		return crt;
	}

	public void setCrt(MultipartFile crt) {
		this.crt = crt;
	}

	public String getCrtFileName() {
		return crtFileName;
	}

	public void setCrtFileName(String crtFileName) {
		this.crtFileName = crtFileName;
	}
	
	
}
