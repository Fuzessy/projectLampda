package com.hu.elte.fuz.lambda.parser.elements;

public class TypeBase implements Type, Cloneable{
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseTypeValue == null) ? 0 : baseTypeValue.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeBase other = (TypeBase) obj;
		if (baseTypeValue != other.baseTypeValue)
			return false;
		return true;
	}
	
	@Override
	public TypeBase clone(){
		try {
			TypeBase clone = (TypeBase) super.clone();
			clone.baseTypeValue = this.baseTypeValue;
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		assert(true);
		return null;
		
	}
	
		
}
