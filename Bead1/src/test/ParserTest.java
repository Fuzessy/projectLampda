package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;

public class ParserTest {

	@Test
	public void test() throws NoSuchAlgorithmException, ParseException {
		NonTypeLambdaParser parser = new NonTypeLambdaParser();
		
		LambdaExpression lambdaExpression =
				parser.parseText("z");
		System.out.println(lambdaExpression);
		
		lambdaExpression =
				parser.parseText("\\x.x");
		System.out.println(lambdaExpression);
		
		lambdaExpression =
				parser.parseText("(\\x.x)");
		System.out.println(lambdaExpression);
		
		lambdaExpression =
				parser.parseText("(((x)))");
		System.out.println(lambdaExpression);
		
		lambdaExpression =
				parser.parseText("x \\x.(((x)))");
		System.out.println(lambdaExpression);
		
		lambdaExpression =
				parser.parseText("x y z");
		System.out.println(lambdaExpression);
	}
	
	@Test
	public void testOfficalCases() throws NoSuchAlgorithmException, ParseException{
		List<String> testCases = new ArrayList<>();
		testCases.add("z");
		testCases.add("(((x)))");
		testCases.add("\\x.x");
		testCases.add("(\\x.x) y");
		testCases.add("(\\x.x) (\\x.x)");
		testCases.add("\\x.\\y.x");
		testCases.add("(\\x.x x) (\\x.x x)");
		testCases.add("(((\\x.(\\y.y)) z) u)");
		testCases.add("\\x.\\y.\\z.x z (y z)");
		testCases.add("(\\x.(\\y.\\z.x y z)) z y x");
		
		NonTypeLambdaParser parser = new NonTypeLambdaParser();
		
		for(String s : testCases){
			LambdaExpression lambdaExpression =
					parser.parseText(s);
			System.out.println(lambdaExpression);	
		}
		
	}
	
	@Test
	public void testWrongCases(){
		List<String> testCases = new ArrayList<>();
		testCases.add("x:x");
		testCases.add("\\");
		testCases.add("\\.");
		testCases.add("\\x.");    
		testCases.add("x:x");    
		testCases.add("\\\\x.x");  
		testCases.add("\\x.\\y."); 
		testCases.add("()");     
		testCases.add("x)");     
		testCases.add("((\\x.x)");
		testCases.add("((\\x.x y z)");
		
		NonTypeLambdaParser parser = new NonTypeLambdaParser();
		for(String s : testCases){
			LambdaExpression lambdaExpression;
			try {
				lambdaExpression = parser.parseText(s);
				System.err.println("Hiba: " + s);
				assertTrue(false);
			} catch (NoSuchAlgorithmException | ParseException e) {
				
			}	
		}
	}
}
