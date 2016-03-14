package com.spring.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class Util {
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	
	public static ResponseEntity<String> getResponseEntity(
			int statusCode, String statusMessage, String data) {
		StatusResponse status = new StatusResponse(statusCode, statusMessage);
		return getResponseEntity(status, data);
	}
	
	public static ResponseEntity<String> getResponseEntity(
			StatusResponse status, String data) {
		return getResponseEntity("{\"status\":" + status.toJson() + ", \"data\":"
				+ data + "}");
	}
	
	public static ResponseEntity<String> getResponseEntity(String json) {
		return getResponseEntity(json, new MediaType("application", "json", UTF_8));
	}
	
	public static ResponseEntity<String> getResponseEntity(String data,
			MediaType type) {
		return getResponseEntity(data, type, HttpStatus.OK);
	}
	
	public static ResponseEntity<String> getResponseEntity(String data,
			MediaType type, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(type);
		return new ResponseEntity<String>(data, headers, status);
	}
	
	public static byte[] compress(String data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
		QUYZipOutputStream gzip = new QUYZipOutputStream(bos);
		gzip.write(data.getBytes());
		gzip.close();
		byte[] compressed = bos.toByteArray();
		bos.close();
		return compressed;
	}
	
	public static String decompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		QUYZipInputStream gis = new QUYZipInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString();
	}
}
