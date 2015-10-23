package com.hu.elte.fuz.lambda.parser.elements;

import java.util.HashSet;
import java.util.Set;

public class LambdaTypeVariable extends LambdaExpression {

	private Variable variable;

	public LambdaTypeVariable(Variable var) {
		this.variable = var;
	}

	@Override
	public String toString() {
		return variable.toString();
	}
	
	

	public Variable getVariable() {
		return variable;
	}

	@Override
	public Set<Variable> getVariables() {
		Set<Variable> listOfVariables = new HashSet<>();
		listOfVariables.add(variable);
		return listOfVariables;
	}

	@Override
	public Set<Variable> getBoundVariables() {
		return new HashSet<Variable>();
	}
	
	@Override
	public String structuredToString(int level) {
		return "";
	}
}
