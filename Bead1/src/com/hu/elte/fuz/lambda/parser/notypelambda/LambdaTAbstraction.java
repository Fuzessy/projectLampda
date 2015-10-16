package com.hu.elte.fuz.lambda.parser.notypelambda;

import java.util.HashSet;
import java.util.Set;

public class LambdaTAbstraction extends LambdaExpression{
	LambdaAbstractor lambdaAbstractor;
	Variable variable;
	DotToken dot;
	LambdaExpression lambdaExpression;
	
	public LambdaTAbstraction(LambdaAbstractor lambdaAbstractor, Variable variable, DotToken dot, LambdaExpression lambdaExpression) {
		super();
		this.lambdaAbstractor = lambdaAbstractor;
		this.variable = variable;
		this.dot = dot;
		this.lambdaExpression = lambdaExpression;
	}
	
	public LambdaTAbstraction(Variable variable, LambdaExpression lambdaExpression) {
		super();
		this.lambdaAbstractor = new LambdaAbstractor();
		this.variable = variable;
		this.dot = new DotToken();
		this.lambdaExpression = lambdaExpression;
	}

	@Override
	public String toString() {
		return "("+lambdaAbstractor.toString() +
				variable.toString() +
				dot.toString() +
				lambdaExpression.toString() + ")";
	}

	@Override
	public Set<Variable> getVariables() {
		Set<Variable> listOfVariables = new HashSet<>();
		listOfVariables.add(variable);
		listOfVariables.addAll(lambdaExpression.getVariables());
		return listOfVariables;
	}
	
	@Override
	public Set<Variable> getBoundVariables() {
		HashSet<Variable> boundVariables = new HashSet<>();
		boundVariables.add(variable);
		boundVariables.addAll(lambdaExpression.getBoundVariables());
		return boundVariables;
	}

	public Variable getVariable() {
		return variable;
	}

	public LambdaExpression getLambdaExpression() {
		return lambdaExpression;
	}

	@Override
	public String structuredToString(int level) {
		return  "\nL" + level + ":" + lambdaAbstractor + variable + dot +
				"\nR" + level + ":" + lambdaExpression +
				lambdaExpression.structuredToString(++level);
	}
	
	
}
