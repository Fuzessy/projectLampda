package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.junit.Test;

import com.hu.elte.fuz.lambda.normalizer.EtaConversion;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;

public class EtaConversionTest {
	NonTypeLambdaParser parser = new NonTypeLambdaParser();
	EtaConversion eta = new EtaConversion();
	@Test
	public void etaConvTest() throws NoSuchAlgorithmException, ParseException{
		LambdaExpression expr = parser.parseText("\\x.y x");
		assertEquals(eta.doEtaConversion(expr).toString(), "y");
		
		expr = parser.parseText("(\\x.((y z) x))");
		assertEquals(eta.doEtaConversion(expr).toString(), "(y z)");
		

		expr = parser.parseText("\\x.(\\x.x y y z) x");
		assertEquals(eta.doEtaConversion(expr).toString(), "(\\x.(((x y) y) z))");
		
		expr = parser.parseText("\\x.x x");
		assertEquals(eta.doEtaConversion(expr).toString(), "(\\x.(x x))");
	}
}
