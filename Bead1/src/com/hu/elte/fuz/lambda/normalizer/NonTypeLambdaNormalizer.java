package com.hu.elte.fuz.lambda.normalizer;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;

public class NonTypeLambdaNormalizer {
	private static final Logger log = Logger.getLogger( NonTypeLambdaNormalizer.class.getName() );
	
	private int steps = Integer.MAX_VALUE;
	
	public LambdaExpression normalize(LambdaExpression expression){
		//log.log(Level.INFO,"normalize :" + expression.toString());
		LambdaExpression ret = expression;
		EtaConversion eta = new EtaConversion();
		BetaReductiuon br = new BetaReductiuon();
		do{
			steps--;
			expression = ret;

			//log.log(Level.INFO, " -> ETA");
			ret = eta.doEtaConversion(ret);
			//log.log(Level.INFO, ret.toString());
				
			//log.log(Level.INFO, " -> BETA");
			ret = br.doBetaReduction(ret);
			//log.log(Level.INFO, ret.toString());
			
		}while(steps > 0 && !ret.toString().equals(expression.toString()));
		if(br.bWasReduction){
			// mivel nem különböztetjük meg a hibát, azér cselesen
			// -1-re állítom, mert volt redukció, de nem történt változás
			steps = -1;
		}
		return ret;
	}
	
	public int getSteps(){
		return steps;
	}

	public LambdaExpression normalize(LambdaExpression expr, int iStep) {
		steps = iStep;
		return normalize(expr);
		
	}
}
