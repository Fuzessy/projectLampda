package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.junit.Test;

import com.hu.elte.fuz.lambda.parser.F1Parser;

public class ParserTester {

	F1Parser parser = new F1Parser();
	
	@Test
	public void f1lambdaTest() throws NoSuchAlgorithmException, ParseException {
		assertEquals("x", parser.parseText("x").toString());
		assertEquals("(x y)", parser.parseText("x y").toString());
		assertEquals("(x y)", parser.parseText("(x y)").toString());
		assertEquals("(\\y:Nat.x)", parser.parseText("\\y:Nat.x").toString());
		assertEquals("(\\y:Nat.x)", parser.parseText("(\\y:Nat.x)").toString());
		assertEquals("(\\x:(Bool -> Nat).y)", parser.parseText("\\x:Bool -> Nat.y").toString());
		assertEquals("(\\x:(Bool -> (Nat -> Bool)).y)", parser.parseText("\\x:Bool -> Nat -> Bool.y").toString());
		assertEquals("(\\w:(Bool -> Nat).(w v))", parser.parseText("\\w:Bool -> Nat.(w v)").toString());
	}

}
