package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.junit.Test;

import com.hu.elte.fuz.lambda.normalizer.LambdaSubstition;
import com.hu.elte.fuz.lambda.normalizer.SubstitionPack;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class AlfaSubTest {

	@Test
	public void testChangeVariable() throws NoSuchAlgorithmException, ParseException {
		NonTypeLambdaParser parser = new NonTypeLambdaParser();
		LambdaExpression expr = parser.parseText("\\x.y");
		LambdaExpression exprOrig = parser.parseText("\\b.x");
		SubstitionPack pack = new SubstitionPack(new Variable("x"), expr);
		LambdaSubstition alfa = new LambdaSubstition();
		assertTrue(alfa.canSubVariable(new Variable("x"), pack));
		
		System.out.println(alfa.doSubstition(exprOrig, pack));
		
		exprOrig = parser.parseText("\\x.y");
		pack = new SubstitionPack(
				new Variable("x"), 
				parser.parseText("\\x.y"));
		assertEquals("(\\x.y)", alfa.doSubstition(exprOrig, pack).toString());
		
		exprOrig = parser.parseText("\\x.y");
		pack = new SubstitionPack(
				new Variable("y"), 
				parser.parseText("\\x.y"));
		assertEquals("(\\x.(\\x.y))", alfa.doSubstition(exprOrig, pack).toString());
		
		exprOrig = parser.parseText("\\x.y");
		pack = new SubstitionPack(
				new Variable("y"), 
				parser.parseText("\\y.x"));
		assertEquals("(\\x.y)", alfa.doSubstition(exprOrig, pack).toString());
		
		exprOrig = parser.parseText("\\x.y");
		pack = new SubstitionPack(
				new Variable("y"), 
				parser.parseText("\\x.x"));
		assertEquals("(\\x.(\\x.x))", alfa.doSubstition(exprOrig, pack).toString());
		
		exprOrig = parser.parseText("\\x.y");
		pack = new SubstitionPack(
				new Variable("y"),
				parser.parseText("\\y.y"));
		assertEquals("(\\x.(\\y.y))", alfa.doSubstition(exprOrig, pack).toString());
		
		exprOrig = parser.parseText("\\x.y \\x.y");
		pack = new SubstitionPack(
				new Variable("y"),
				parser.parseText("\\y.y"));
		assertEquals("(\\x.((\\y.y) (\\x.(\\y.y))))", alfa.doSubstition(exprOrig, pack).toString());
	}

}
