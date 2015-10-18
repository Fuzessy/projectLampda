package com.hu.elte.fuz.lambda.parser;

public class TypeBase implements Type{
	private TypeValue baseTypeValue;
	public enum TypeValue{
		BOOL, NAT;

		public String getName() {
			if(this == BOOL){
				return "Bool";
			}else if(this == NAT){
				return "Nat";
			}
			return "ERROR";
		}
	}
	
	public TypeBase(TypeValue baseTypeValue) {
		super();
		this.baseTypeValue = baseTypeValue;
	}
	public TypeValue getBaseTypeValue() {
		return baseTypeValue;
	}
	public void setBaseTypeValue(TypeValue baseTypeValue) {
		this.baseTypeValue = baseTypeValue;
	}
	
	public String toString(){
		return baseTypeValue.getName();
	}
		
}
