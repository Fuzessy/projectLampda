package test;

import static org.junit.Assert.assertFalse;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.junit.Test;

import com.hu.elte.fuz.lambda.normalizer.AlphaConversion;
import com.hu.elte.fuz.lambda.normalizer.BetaReductiuon;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;

public class BetaReductionTest {
	NonTypeLambdaParser parser = new NonTypeLambdaParser();
	BetaReductiuon br = new BetaReductiuon();
	AlphaConversion alpha = new AlphaConversion();
	
	@Test
	public void testBetaReductionTest1() throws NoSuchAlgorithmException, ParseException {
		LambdaExpression expr = parser.parseText("(\\x.x) y");
		System.out.println(expr);
		System.out.println(br.doBetaReduction(expr));
		
		expr = parser.parseText("(\\y.(\\x.y x)) u y");
		System.out.println(expr);
		expr = br.doBetaReduction(expr);
		System.out.println(expr);
		expr = br.doBetaReduction(expr); // null
		System.out.println(expr);
		expr = br.doBetaReduction(expr);
		System.out.println(expr);
		
	}
	
	@Test
	public void testNoNameConflict() throws NoSuchAlgorithmException, ParseException {
		LambdaExpression expr = parser.parseText("(\\x.x) y");
		
		expr = parser.parseText("\\x.y x");
		System.out.println(expr);		
	}
}
