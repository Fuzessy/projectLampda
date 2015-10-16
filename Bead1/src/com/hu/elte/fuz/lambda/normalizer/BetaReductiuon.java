package com.hu.elte.fuz.lambda.normalizer;

import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTAbstraction;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTApplication;

import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;


public class BetaReductiuon {
	public boolean bWasReduction = false;
	
	public LambdaExpression doBetaReductionStratAppl(LambdaTApplication application){
		
		// applikációk sorozatát kell végrehajtani
		int size = application.getApplicationSize(0);
		LambdaExpression result = null;
		
		for(int i = size; i >= 0; i--){
			LambdaExpression expr = 
					application.getLambdExprOfApplication(i);
			// csak az elsõ esetben, ha az A B  esetén A egy absztrakció
			if(expr instanceof LambdaTAbstraction && size == i){
				LambdaExpression betaResult = doRealBetaReduction(
						(LambdaTAbstraction)expr, application.getLambdExprOfApplication(i-1));
				if(size > 1){
					LambdaTApplication parent = application.getNode(size-2);
					parent.setLeftLambdaExpression(betaResult);
					result = parent;
				}else{
					result = betaResult;
				}
				i--;
			}else{
				application.setLambdExprOfApplication(doBetaReduction(expr),i);
				result = application;
			}
		}
		return result;
	}
	
	public LambdaExpression doRealBetaReduction(
			LambdaTAbstraction lxE, LambdaExpression _F) {
		bWasReduction = false;
		// (\x.E)F
		Variable x = lxE.getVariable();
		LambdaExpression _E = lxE.getLambdaExpression();
		
		// (\x.E)F -> E[x:=F]
		SubstitionPack subPack = new SubstitionPack(x, _F);
		AlphaConversion alphaConversion = new AlphaConversion();
		return alphaConversion.doSubstition(_E, subPack);
	}

	public LambdaExpression doBetaReduction(LambdaExpression expr){
		bWasReduction = false;
		LambdaExpression ret = expr;
		if(expr instanceof LambdaTApplication){
			ret = doBetaReductionStratAppl((LambdaTApplication) expr);
		}else if(expr instanceof LambdaTAbstraction){
			
			ret = new LambdaTAbstraction(
					((LambdaTAbstraction) expr).getVariable(),
					doBetaReduction(((LambdaTAbstraction) expr).getLambdaExpression()));
		}
		return ret;
	}
	
//	public LambdaExpression doBateReduction2(LambdaExpression expr){
//		LambdaExpression ret = expr;
//				
//		if(expr instanceof LambdaTApplication){
//			LambdaTApplication application = ((LambdaTApplication) expr);
//			LambdaExpression _F = application.getLambdaExpression2();
//			/*if(_F instanceof LambdaTypeBrackets){
//				_F = ((LambdaTypeBrackets) _F).getLambdaNotBracketsExpression();
//			}*/
//			// (LE)F
//			if( application.getLambdaExpression() instanceof LambdaTAbstraction){
//				LambdaExpression le = ((LambdaTAbstraction) application.getLambdaExpression());
//				if(le instanceof LambdaTAbstraction){
//					// (\x.E)F
//					LambdaTAbstraction abstarction = (LambdaTAbstraction) le;
//					Variable x = abstarction.getVariable();
//					LambdaExpression _E = abstarction.getLambdaExpression();
//					
//					// (\x.E)F -> E[x:=F]
//					SubstitionPack subPack = new SubstitionPack(x, _F);
//					AlphaConversion alphaConversion = new AlphaConversion();
//					ret = alphaConversion.doSubstition(_E, subPack);
//				 
//				}
//			}else if(application.getLambdaExpression() instanceof LambdaTApplication){
//				// ((\x.y) x) y
//				// zárójelet nem tudok kezelni
//				ret = new LambdaTApplication(
//						doBateReduction(application.getLambdaExpression()),
//						application.getLambdaExpression2());
//			}
//		}else if(expr instanceof LambdaTAbstraction){
//			ret = new LambdaTAbstraction(
//					((LambdaTAbstraction) expr).getVariable(), 
//					doBateReduction(((LambdaTAbstraction) expr).getLambdaExpression()));
//		}
//		
//		return ret;
//	}
}
