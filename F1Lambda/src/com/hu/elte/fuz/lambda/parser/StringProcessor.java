package com.hu.elte.fuz.lambda.parser;

import java.security.NoSuchAlgorithmException;

public class StringProcessor {
	String text;
	public StringProcessor(String text) {
		this.text = text;
	}
	
	public boolean hasNext(){
		return text.length() > 0;
	}
	
	public String eatNext() throws NoSuchAlgorithmException{
		if(this.hasNext()){
			String token = text.substring(0, 1);
			text = text.substring(1, text.length());
			return token;
		}else{
			throw new NoSuchAlgorithmException();
		}
	}
	
	public String getRemainder(){
		return text;
	}

	public String getNext() throws NoSuchAlgorithmException {
		if(this.hasNext()){
			String token = text.substring(0, 1);
			return token;
		}else{
			throw new NoSuchAlgorithmException();
		}
	}
}
