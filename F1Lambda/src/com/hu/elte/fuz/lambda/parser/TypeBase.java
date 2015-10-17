package com.hu.elte.fuz.lambda.parser;

public class TypeBase implements Type{
	private TypeValue baseTypeValue;
	private enum TypeValue{
		BOOL, NAT;
	}
	public TypeValue getBaseTypeValue() {
		return baseTypeValue;
	}
	public void setBaseTypeValue(TypeValue baseTypeValue) {
		this.baseTypeValue = baseTypeValue;
	}
		
}
