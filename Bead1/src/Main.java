

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import com.hu.elte.fuz.lambda.normalizer.NonTypeLambdaNormalizer;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;

public class Main {
	public static void main(String[] args) {
		int iStep = Integer.parseInt(args[0]);
		String exprString = args[1];
		
		NonTypeLambdaParser parser = new NonTypeLambdaParser();
		LambdaExpression expr = null;
		try {
			expr = parser.parseText(exprString);
		} catch (NoSuchAlgorithmException | ParseException e) {
			System.err.println("ERROR: "+exprString);
			System.exit(1);
		}
		if(!expr.isClosed()){
			System.err.println("ERROR: "+exprString);
			System.exit(2);
		}
		
		NonTypeLambdaNormalizer normalizer = new NonTypeLambdaNormalizer();
		LambdaExpression result = normalizer.normalize(expr,iStep);
		if(normalizer.getSteps() > 0){
			System.err.println("ERROR: "+exprString);
		}else{
			System.out.println("OK: "+ result.toString());
		}
		
	}
}
