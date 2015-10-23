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
	
	/**
	 * A k�vetkez� sz�k�z vagy pont karakterig olvas, �s visszaadja a sz�veget,
	 * fogyaszt� t�pus�
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String eatNextWord() throws NoSuchAlgorithmException{
		String word = "";
		while(this.hasNext() && 
				!this.getNext().equals(" ") &&
				!this.getNext().equals(",") && 
				!this.getNext().equals(".") && 
				!this.getNext().equals(")")){
			word += this.eatNext();
		}
		return word;
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
