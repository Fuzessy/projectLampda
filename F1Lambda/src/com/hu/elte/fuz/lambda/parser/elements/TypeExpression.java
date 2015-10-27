package com.hu.elte.fuz.lambda.parser.elements;

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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeFrom == null) ? 0 : typeFrom.hashCode());
		result = prime * result + ((typeTo == null) ? 0 : typeTo.hashCode());
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
		TypeExpression other = (TypeExpression) obj;
		if (typeFrom == null) {
			if (other.typeFrom != null)
				return false;
		} else if (!typeFrom.equals(other.typeFrom))
			return false;
		if (typeTo == null) {
			if (other.typeTo != null)
				return false;
		} else if (!typeTo.equals(other.typeTo))
			return false;
		return true;
	}
	
	@Override
	public TypeExpression clone(){
		try {
			TypeExpression clone = (TypeExpression) super.clone();
			clone.typeFrom = this.typeFrom.clone();
			clone.typeTo = this.typeTo.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			assert(true);
			e.printStackTrace();
			return null;
		}
	}
	
	
}
