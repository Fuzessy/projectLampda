package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.NonTypeLambdaParser;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class FreeBoundedVariablesTest {

	@Test
	public void variableTest() throws NoSuchAlgorithmException, ParseException {
		NonTypeLambdaParser parser = new NonTypeLambdaParser();
		LambdaExpression expr =
				parser.parseText("\\x.(\\x.x y) \\y.x");
		Set<Variable> listOfVariables = expr.getVariables();
		
		for(Variable v : listOfVariables){
			System.out.println(v);
		}
		
		Set<Variable> listOfBoundVariables = expr.getBoundVariables();
		
		System.out.println("kötött változók: ");
		for(Variable v : listOfBoundVariables){
			System.out.println(v);
		}
		
		if(expr.isClosed()){
			System.out.println("Zárt kifejezés: " + expr);
		}
		
	}
	
	@Test
	public void closedVariablesTest() throws NoSuchAlgorithmException, ParseException{
		NonTypeLambdaParser parser = new NonTypeLambdaParser();
		LambdaExpression expr =
				parser.parseText("\\x.(\\x.x y) \\y.x");
		Set<Variable> setOfVariables = expr.getBoundVariables();
		Set<Variable> solution = new HashSet<>();
		solution.add(new Variable("x"));
		solution.add(new Variable("y"));
		assertTrue(solution.size() == setOfVariables.size());
		solution.addAll(setOfVariables);
		assertTrue(solution.size() == setOfVariables.size());
		
		expr = parser.parseText("\\y.(\\y.x y)");
		setOfVariables = expr.getBoundVariables();
		solution = new HashSet<>();
		solution.add(new Variable("y"));
		assertTrue(solution.size() == setOfVariables.size());
		solution.addAll(setOfVariables);
		assertTrue(solution.size() == setOfVariables.size());
	}
	
	@Test
	public void closedExpressionTest() throws NoSuchAlgorithmException, ParseException{
		NonTypeLambdaParser parser = new NonTypeLambdaParser();
		LambdaExpression expr =
				parser.parseText("\\x.(\\x.x y) \\y.x");
		assertTrue(expr.isClosed());
		
		expr =
				parser.parseText("\\x.(\\x.x y) \\y.z");
		assertTrue(!expr.isClosed());
	}

}
