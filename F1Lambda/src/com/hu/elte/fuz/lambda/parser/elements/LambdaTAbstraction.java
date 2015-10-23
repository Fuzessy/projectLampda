package com.hu.elte.fuz.lambda.parser.elements;

import java.util.HashSet;
import java.util.Set;

public class LambdaTAbstraction extends LambdaExpression{
	// \x:Bool -> Nat -> Bool.y
	Variable variable;
	Type type;
	LambdaExpression lambdaExpression;
	
	public LambdaTAbstraction(Variable variable, Type type, LambdaExpression lambdaExpression) {
		super();
		this.variable = variable;
		this.type = type;
		this.lambdaExpression = lambdaExpression;
	}

	@Override
	public String toString() {
		return "("+ "\\" +
				variable.toString() +
				":" +
				type.toString() +
				"." +
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
		return  "\nL" + level + ":" + "\\" + variable + 
				":" + type + "." +
				"\nR" + level + ":" + lambdaExpression +
				lambdaExpression.structuredToString(++level);
	}
	
	
}
