package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import com.hu.elte.fuz.lambda.normalizer.AlphaConversion;
import com.hu.elte.fuz.lambda.normalizer.BetaReductiuon;
import com.hu.elte.fuz.lambda.normalizer.LambdaSubstition;
import com.hu.elte.fuz.lambda.normalizer.SubstitionPack;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTAbstraction;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTApplication;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class MyTest {

	NonTypeLambdaParser parser = new NonTypeLambdaParser();
	BetaReductiuon br = new BetaReductiuon();
	AlphaConversion alpha = new AlphaConversion();
	
	
	@Test
	public void testApplicationLength() throws NoSuchAlgorithmException, ParseException{
		LambdaExpression expr = parser.parseText("a b c d e f");
		System.out.println(expr);
		LambdaTApplication appl = (LambdaTApplication) expr;
		System.out.println(appl.getApplicationSize(0));
		System.out.println(appl.getLambdExprOfApplication(0));
		System.out.println(appl.getLambdExprOfApplication(1));
		System.out.println(appl.getLambdExprOfApplication(2));
		System.out.println(appl.getLambdExprOfApplication(3));
		System.out.println(appl.getLambdExprOfApplication(4));
		System.out.println(appl.getLambdExprOfApplication(5));
		/**
		 *           0
		 *         1   f
		 *       2   e
		 *     3   d
		 *   4   c
		 * a  b      
		 */
		
		System.out.println(appl.getNode(0));
		System.out.println(appl.getNode(1));
		System.out.println(appl.getNode(2));
		System.out.println(appl.getNode(3));
		System.out.println(appl.getNode(4));
		// classcastexception
		// System.out.println(appl.getNode(5));
		appl.setLambdExprOfApplication(parser.parseText("x"), 2);
		System.out.println(appl);
	}
	public void testAlfaSub() throws NoSuchAlgorithmException, ParseException{
		LambdaExpression expr = parser.parseText("\\y.x");
		LambdaExpression toThis = parser.parseText("y");
		LambdaSubstition sub = new LambdaSubstition();
		SubstitionPack subPack = new SubstitionPack(
				new Variable("x"), toThis);
		System.out.println(sub.doSubstition(expr, subPack));
		
		AlphaConversion subA = new AlphaConversion();
		System.out.println(subA.doSubstition(expr, subPack));
	}
	
	@Test
	public void test() throws NoSuchAlgorithmException, ParseException {
		NonTypeLambdaParser p = new NonTypeLambdaParser();
		//System.out.println(expr.structuredToString(0));
		BetaReductiuon red = new BetaReductiuon();
		assertTrue(
			red.doBetaReduction(
			p.parseText("(\\x.x) y")).toString()
				.equals("y"));
		
		
		assertEquals(
				red.doBetaReduction(
					p.parseText("(\\y.(\\x.y x)) u y")).toString()
				,"u y");
		
		assertTrue(
				red.doBetaReduction(
				p.parseText("(\\f.f u) (\\x.x y)")).toString()
					.equals("u y"));
		

		assertTrue(
				red.doBetaReduction(
				p.parseText("(\\x.(\\x.x y) x) u")).toString()
					.equals("u y"));

		System.out.println(red.doBetaReduction(
				p.parseText("(\\x.\\y.x y) y")));
		
		System.out.println(red.doBetaReduction(
				p.parseText("(\\x.\\y.x) y")));
	}

}
