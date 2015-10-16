package com.hu.elte.fuz.lambda.normalizer;

import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTAbstraction;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTApplication;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTypeVariable;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class EtaConversion {
	/**
	 * Tudjuk használni az éta konverziót?
	 * @param expr
	 * @return
	 */
	private boolean canUseEtaConversion(LambdaExpression expr){
		try{
			LambdaTAbstraction abstraction = (LambdaTAbstraction) expr;
			// \x.E x -> E
			// de csak akkor, ha az x nem eleme FV(E)
			Variable x = abstraction.getVariable();
			
			LambdaExpression ex = abstraction.getLambdaExpression();
			
			LambdaExpression _E = ((LambdaTApplication) ex)
					.getLambdaExpression();
			Variable x2 = ((LambdaTypeVariable) 
					(((LambdaTApplication) ex)
							.getLambdaExpression2())).getVariable();
			return x.equals(x2) && !_E.getFreeVariables().contains(x);
		}catch(ClassCastException ex){
			return false;
		}
		
	}
	
	public LambdaExpression doEtaConversion(LambdaExpression expr){
		if(expr instanceof LambdaTAbstraction){
			LambdaTAbstraction abstraction = (LambdaTAbstraction) expr;
			if(canUseEtaConversion(expr)){
				// \x.E x -> E
				// de csak akkor, ha az x nem eleme FV(E)
				LambdaExpression ex = abstraction.getLambdaExpression();
				
				LambdaExpression _E = ((LambdaTApplication) ex)
						.getLambdaExpression();
				return _E;
			}else{
				return new LambdaTAbstraction(abstraction.getVariable(),
						doEtaConversion(abstraction.getLambdaExpression()));
			}
		}else if(expr instanceof LambdaTApplication){
			LambdaTApplication application = (LambdaTApplication) expr;
			expr = new LambdaTApplication(
					doEtaConversion(application.getLambdaExpression()),
					doEtaConversion(application.getLambdaExpression2()));
			return expr;
		}else{
			return expr;
		}
	}
}
