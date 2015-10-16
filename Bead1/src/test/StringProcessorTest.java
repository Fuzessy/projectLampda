package test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.hu.elte.fuz.lambda.parser.StringProcessor;

public class StringProcessorTest {

	@Test
	public void testEat() throws NoSuchAlgorithmException {
		StringProcessor proc = new StringProcessor("alma");
		assertTrue(proc.eatNext().equals("a"));
		assertTrue(proc.eatNext().equals("l"));
		assertTrue(proc.eatNext().equals("m"));
		assertTrue(proc.eatNext().equals("a"));
		assertFalse(proc.hasNext());
	}

	@Test
	public void testRemainder() throws NoSuchAlgorithmException {
		StringProcessor proc = new StringProcessor("alma");
		proc.eatNext();
		proc.eatNext();
		assertTrue(proc.hasNext());
		assertTrue(proc.getRemainder().equals("ma"));
		
	}
}
