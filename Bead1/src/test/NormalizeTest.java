package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.junit.Test;

import com.hu.elte.fuz.lambda.normalizer.BetaReductiuon;
import com.hu.elte.fuz.lambda.normalizer.NonTypeLambdaNormalizer;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;

public class NormalizeTest {
	
	NonTypeLambdaParser parser = new NonTypeLambdaParser();
	NonTypeLambdaNormalizer normalizer = new NonTypeLambdaNormalizer();
	@Test
	public void normalizeTest1() throws NoSuchAlgorithmException, ParseException{
		/*


(\y.(\x.\y.(x y)) y)
OK: (\x.x)
(\f.\x.f (f x)) (\f.\x.f (f x))
OK: (\x.(\y.(x (x (x (x y))))))
\x.
ERROR: \x.
\x.y
ERROR: \x.y
(\x.x x) (\x.x x)
ERROR: (\x.x x) (\x.x x)
		 */
		LambdaExpression expr = parser.parseText("\\x.x");
		expr = normalizer.normalize(expr);
		assertTrue(expr.toString().equals("(\\x.x)"));

		expr = parser.parseText("(\\x.x) (\\x.x)");
		expr = normalizer.normalize(expr);
		assertTrue(expr.toString().equals("(\\x.x)"));

		expr = parser.parseText("(\\z.(\\x.\\y.y) z)");
		expr = normalizer.normalize(expr);
		assertTrue(expr.toString().equals("(\\x.(\\y.y))"));
		
		expr = parser.parseText("(\\f.f u) (\\x.x y)");
		expr = normalizer.normalize(expr);
		assertEquals(expr.toString(),("(u y)"));
		
		expr = parser.parseText("(\\x.(\\x.x y) x) u");
		expr = normalizer.normalize(expr);
		assertTrue(expr.toString().equals("(u y)"));

		expr = parser.parseText("(\\x.\\y.x y) y");
		expr = normalizer.normalize(expr);
		assertTrue(expr.toString().equals("y"));
		
		expr = parser.parseText("(\\y.(\\x.\\y.(x y)) y)");
		expr = normalizer.normalize(expr);
		System.out.println(expr);
		
		expr = parser.parseText("(\\f.\\x.f (f x)) (\\f.\\x.f (f x))");
		expr = normalizer.normalize(expr);
		System.out.println(expr);
		//assertTrue(expr.toString().equals("\\x.\\y.x x x x y"));
		
		expr = parser.parseText("(\\x.x x) (\\x.x x)");
		expr = normalizer.normalize(expr);
		System.out.println(expr);
		
		/**

(\x.x x) (\x.x x)

//\x.
//ERROR: \x.
//\x.y
//ERROR: \x.y
//(\x.x x) (\x.x x)
//ERROR: (\x.x x) (\x.x x)
		 */
	}
}
