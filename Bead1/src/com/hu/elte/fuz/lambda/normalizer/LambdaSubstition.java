package com.hu.elte.fuz.lambda.normalizer;

import com.hu.elte.fuz.lambda.parser.notypelambda.DotToken;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaAbstractor;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTAbstraction;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTApplication;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTypeVariable;
import com.hu.elte.fuz.lambda.parser.notypelambda.SpaceToken;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class LambdaSubstition {

	public boolean canSubVariable(Variable var, SubstitionPack subPack){
		return (var.equals(subPack.subIt));
	}
	
	public LambdaExpression doSubstition(LambdaExpression expr, SubstitionPack subPack){
		LambdaExpression ret;
		if(expr.getClass().equals(LambdaTypeVariable.class)){
			LambdaTypeVariable ltvar = (LambdaTypeVariable) expr;
			if(canSubVariable(ltvar.getVariable(), subPack)){
				// van csere
				ret = subPack.getToThis();
			}else{
				// nincs csere
				ret = expr;
			}
		}else if(expr.getClass().equals(LambdaTApplication.class)){
			LambdaTApplication application = (LambdaTApplication) expr;
			LambdaExpression le1 = doSubstition(application.getLambdaExpression(), subPack);
			LambdaExpression le2 = doSubstition(application.getLambdaExpression2(), subPack);
			ret = new LambdaTApplication(le1, new SpaceToken(), le2);
		}else if(expr.getClass().equals(LambdaTAbstraction.class)){
			LambdaTAbstraction abstraction = (LambdaTAbstraction) expr;
			// \x.E[y:=G]
			// ha x = y akkor \x.E
			if(abstraction.getVariable().equals(subPack.getSubIt())){
				ret = expr;
			}else if(!abstraction.getVariable().equals(subPack.getSubIt()) &&
					!subPack.getToThis().getFreeVariables().contains(abstraction.getVariable())){
				
				// ha x != y and x not element of FV(G)
				ret = new LambdaTAbstraction(
						new LambdaAbstractor(), 
						abstraction.getVariable(),
						new DotToken(),
						this.doSubstition(abstraction.getLambdaExpression(), subPack));
			}else{
				ret = expr;
			}
		}else{
			ret = expr;
		}
		return ret;
	}
}
