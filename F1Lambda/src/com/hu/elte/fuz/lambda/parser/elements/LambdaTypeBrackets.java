package com.hu.elte.fuz.lambda.parser.elements;

import java.util.HashSet;
import java.util.Set;

public abstract class LambdaTypeBrackets extends LambdaExpression{
	private LeftBracket leftBracket;
	private LambdaExpression lambdaExpression;
	private RightBracket rightBracket;
	public LambdaTypeBrackets(LeftBracket leftBracket, LambdaExpression lambdaExpression, RightBracket rightBracket) {
		super();
		this.leftBracket = leftBracket;
		this.lambdaExpression = lambdaExpression;
		this.rightBracket = rightBracket;
	}
	
	public LambdaTypeBrackets(LambdaExpression lambdaExpression) {
		super();
		this.leftBracket = new LeftBracket();
		this.lambdaExpression = lambdaExpression;
		this.rightBracket = new RightBracket();
	}
	
	@Override
	public String toString() {
		return ""+
				leftBracket + 
				lambdaExpression + 
				rightBracket;
	}
	
	@Override
	public Set<Variable> getVariables() {
		Set<Variable> listOfVariables = new HashSet<>();
		listOfVariables.addAll(lambdaExpression.getVariables());
		return listOfVariables;
	}
	
	@Override
	public Set<Variable> getBoundVariables() {
		HashSet<Variable> boundVariables = new HashSet<>();
		boundVariables.addAll(lambdaExpression.getBoundVariables());
		return boundVariables;
	}
	public LambdaExpression getLambdaExpression() {
		return lambdaExpression;
	}
	public void setLambdaExpression(LambdaExpression lambdaExpression) {
		this.lambdaExpression = lambdaExpression;
	}
	
	@Override
	public String structuredToString(int level) {
		return "\n" + level + ":" + lambdaExpression.toString() +
				lambdaExpression.structuredToString(level + 1);
	}

	/**
	 * Visszaadja a következõ igazai, nem zárójeles lambda kifejezést
	 * @return
	 */
	public LambdaExpression getLambdaNotBracketsExpression() {
		if(lambdaExpression instanceof LambdaTypeBrackets){
			return ((LambdaTypeBrackets) lambdaExpression).getLambdaNotBracketsExpression();
		}
		return lambdaExpression;
	}
	
	
}
