package com.hu.elte.fuz.lambda.parser;

import java.util.Set;

public abstract class LambdaExpression {

	public abstract Set<Variable> getVariables();

	public abstract Set<Variable> getBoundVariables();
	
	public abstract String structuredToString(int level);
	

	/**
	 * A kifejez�s z�rt, ha minden v�ltoz�ja k�t�tt
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
