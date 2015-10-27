package com.hu.elte.fuz.lambda.parser.elements;

import java.util.Set;

public abstract class LambdaExpression implements Cloneable{

	public abstract Set<Variable> getVariables();

	public abstract Set<Variable> getBoundVariables();
	
	public abstract String structuredToString(int level);
	
	public LambdaExpression clone(){
		try {
			return (LambdaExpression) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		assert(true);
		return null;
	}

	/**
	 * A kifejezés zárt, ha minden változója kötött
	 * @return
	 */
	public boolean isClosed() {
		return this.getVariables().size() == this.getBoundVariables().size();
	}

	public Set<Variable> getFreeVariables() {
		Set<Variable> all = this.getVariables();
		all.removeAll(this.getBoundVariables());
		return all;
	}
	
}
