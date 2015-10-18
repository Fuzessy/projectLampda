package com.hu.elte.fuz.lambda.parser;

public class TypeExpression implements Type{

	private Type typeFrom;
	private Type typeTo;
	
	public TypeExpression(Type typeFrom, Type typeTo) {
		super();
		this.typeFrom = typeFrom;
		this.typeTo = typeTo;
	}
	public Type getTypeFrom() {
		return typeFrom;
	}
	public Type getTypeTo() {
		return typeTo;
	}
	
	public String toString(){
		return "(" + typeFrom +
				" -> " +
				typeTo + ")";
	}
	
}
