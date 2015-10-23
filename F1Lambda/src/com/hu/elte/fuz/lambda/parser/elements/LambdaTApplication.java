package com.hu.elte.fuz.lambda.parser.elements;

import java.util.HashSet;
import java.util.Set;

public class LambdaTApplication extends LambdaExpression{
	LambdaExpression lambdaExpression;
	SpaceToken spaceToken;
	LambdaExpression lambdaExpression2;
	public LambdaTApplication(LambdaExpression lambdaExpression, SpaceToken spaceToken, LambdaExpression lambdaExpression2) {
		super();
		this.lambdaExpression = lambdaExpression;
		this.spaceToken = spaceToken;
		this.lambdaExpression2 = lambdaExpression2;
	}
	
	public LambdaTApplication(LambdaExpression lambdaExpression, LambdaExpression lambdaExpression2) {
		super();
		this.lambdaExpression = lambdaExpression;
		this.spaceToken = new SpaceToken();
		this.lambdaExpression2 = lambdaExpression2;
	}
	
	public void setLeftLambdaExpression(LambdaExpression expr){
		this.lambdaExpression = expr;
	}
	
	/**
	 * Applikáció megadott elemét adja vissza
	 *  (((A B) C) D)
	 *        *
	 *      C   D
	 *     A
	 *  D = 0
	 *  C = 1
	 *  A = 2
	 * @return
	 */
	public LambdaExpression getLambdExprOfApplication(int i){
		if(i == 0){
			return getLambdaExpression2();
		}else{
			if(lambdaExpression instanceof LambdaTApplication){
				return ((LambdaTApplication) lambdaExpression).getLambdExprOfApplication(--i);
			}else{
				return lambdaExpression;
			}
		}
		
	}
	
	public void setLambdExprOfApplication(LambdaExpression expr, int i) {
		if(i == 0){
			lambdaExpression2 = expr;
		}else{
			if(lambdaExpression instanceof LambdaTApplication){
				((LambdaTApplication) lambdaExpression).setLambdExprOfApplication(expr,--i);
			}else{
				lambdaExpression = expr;
			}
		}
	}
	
	/**
	 *  (((A B) C) D)
	 *        0
	 *      1   D
	 *    2  C 
	 *  A  B
	 * @param length
	 * @return
	 */
	public LambdaTApplication getNode(int depth){
		if(depth == 0){
			return this;
		}else{
			return ((LambdaTApplication) lambdaExpression).getNode(--depth);
		}
	}
	
	public int getApplicationSize(int i){
		if(lambdaExpression instanceof LambdaTApplication){
			return ((LambdaTApplication) lambdaExpression).getApplicationSize(++i);
		}
		return i+1;
	}
	
	
	@Override
	public String toString() {
		return "(" + lambdaExpression.toString()
				+ spaceToken.toString()
				+ lambdaExpression2.toString() + ")";
	}
	
	@Override
	public Set<Variable> getVariables() {
		Set<Variable> listOfVariables = new HashSet<>();
		listOfVariables.addAll(lambdaExpression.getVariables());
		listOfVariables.addAll(lambdaExpression2.getVariables());
		return listOfVariables;
	}
	
	@Override
	public Set<Variable> getBoundVariables() {
		HashSet<Variable> boundVariables = new HashSet<>();
		boundVariables.addAll(lambdaExpression.getBoundVariables());
		boundVariables.addAll(lambdaExpression2.getBoundVariables());
		return boundVariables;
	}
	public LambdaExpression getLambdaExpression() {
		return lambdaExpression;
	}
	public SpaceToken getSpaceToken() {
		return spaceToken;
	}
	public LambdaExpression getLambdaExpression2() {
		return lambdaExpression2;
	}
	
	@Override
	public String structuredToString(int level) {
		return "\nL" + level + ":" + lambdaExpression +
			   "\nR" + level + ":" + lambdaExpression2 +
			   lambdaExpression.structuredToString(level + 1) +
			   lambdaExpression2.structuredToString(level + 1);
	}

	
	
	
}
