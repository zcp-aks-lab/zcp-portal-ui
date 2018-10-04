package com.skcc.cloudz.zcp.portal.management.namespace.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretTlsVO {
	
	@JsonIgnore
	private byte[] crt;
	
	@JsonIgnore
	private byte[] key;
	
	@JsonProperty("file")
    private String crtFileName;
	
	@JsonProperty("file")
    private String keyFileName;

	public String getKeyFileName() {
		return keyFileName;
	}

	public void setKeyFileName(String keyFileName) {
		this.keyFileName = keyFileName;
	}

	public String getCrtFileName() {
		return crtFileName;
	}

	public void setCrtFileName(String crtFileName) {
		this.crtFileName = crtFileName;
	}

	public byte[] getCrt() {
		return crt;
	}

	public void setCrt(byte[] crt) {
		this.crt = crt;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}


	
}
