package com.hu.elte.fuz.lambda.normalizer;

import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class SubstitionPack {
	Variable subIt;
	LambdaExpression toThis;
	public SubstitionPack(Variable subIt, LambdaExpression toThis) {
		super();
		this.subIt = subIt;
		this.toThis = toThis;
	}
	public Variable getSubIt() {
		return subIt;
	}
	public void setSubIt(Variable subIt) {
		this.subIt = subIt;
	}
	public LambdaExpression getToThis() {
		return toThis;
	}
	public void setToThis(LambdaExpression toThis) {
		this.toThis = toThis;
	}
	
	
}
