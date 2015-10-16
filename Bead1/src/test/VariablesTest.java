package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.hu.elte.fuz.lambda.normalizer.AlphaConversion;
import com.hu.elte.fuz.lambda.normalizer.BetaReductiuon;
import com.hu.elte.fuz.lambda.normalizer.LambdaSubstition;
import com.hu.elte.fuz.lambda.normalizer.SubstitionPack;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTAbstraction;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTApplication;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTypeBrackets;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class VariablesTest {

	NonTypeLambdaParser parser = new NonTypeLambdaParser();
	BetaReductiuon br = new BetaReductiuon();
	AlphaConversion alpha = new AlphaConversion();
	
	@Test
	public void testNoNameConflict() throws NoSuchAlgorithmException, ParseException {
		LambdaExpression expr = parser.parseText("(\\x.y) y");
		System.out.println(expr);
		LambdaTAbstraction abstraction = 
				(LambdaTAbstraction)
				((LambdaTApplication) expr).getLambdaExpression();
				;
		LambdaExpression f = 
				((LambdaTApplication) expr).getLambdaExpression2();
		LambdaExpression e = abstraction.getLambdaExpression();
		System.out.println(e);
		System.out.println(f);
		
		assertFalse(alpha.hasNameConflict(e, f));
	}
	
	@Test
	public void testIsNameConflict() throws NoSuchAlgorithmException, ParseException {
		LambdaExpression expr = parser.parseText("(\\x.\\y.x) y");
		System.out.println(expr);
		LambdaTAbstraction abstraction = 
				(LambdaTAbstraction)
				
						((LambdaTApplication) expr).getLambdaExpression()
				;
				
		LambdaExpression f = 
				((LambdaTApplication) expr).getLambdaExpression2();
		LambdaExpression e = abstraction.getLambdaExpression();
		System.out.println(e);
		System.out.println(f);
		
		assertTrue(alpha.hasNameConflict(e, f));
	}
	
	@Test
	public void testIsNameConflict2() throws NoSuchAlgorithmException, ParseException {
		LambdaExpression expr = parser.parseText("(\\x.\\y.x y) y");
		System.out.println(expr);
		LambdaTAbstraction abstraction = 
				(LambdaTAbstraction)
						((LambdaTApplication) expr).getLambdaExpression();
				;
		LambdaExpression f = 
				((LambdaTApplication) expr).getLambdaExpression2();
		LambdaExpression e = abstraction.getLambdaExpression();
		System.out.println(e);
		System.out.println(f);
		
		assertTrue(alpha.hasNameConflict(e, f));
	}
	
	@Test
	public void testNewVariable(){
		Set<Variable> allvariable = new HashSet<>();
		allvariable.add(new Variable("x"));
		allvariable.add(new Variable("y"));
		
		assertTrue((new Variable("z"))
				.equals(alpha.getNewVariable(allvariable, new Variable("x"))));
	
		allvariable.add(new Variable("z"));
		
		assertTrue((new Variable("a"))
				.equals(alpha.getNewVariable(allvariable, new Variable("x"))));
	}
	
	
	


}
