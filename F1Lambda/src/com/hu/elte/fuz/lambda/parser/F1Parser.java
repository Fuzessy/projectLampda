package com.hu.elte.fuz.lambda.parser;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hu.elte.fuz.lambda.parser.elements.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.elements.LambdaTAbstraction;
import com.hu.elte.fuz.lambda.parser.elements.LambdaTApplication;
import com.hu.elte.fuz.lambda.parser.elements.LambdaTypeVariable;
import com.hu.elte.fuz.lambda.parser.elements.SpaceToken;
import com.hu.elte.fuz.lambda.parser.elements.Type;
import com.hu.elte.fuz.lambda.parser.elements.Variable;

public class F1Parser {
		private VariableParser vp = new VariableParser();
		public LambdaExpression parseText(String text) throws NoSuchAlgorithmException, ParseException{
			text = text.replaceAll(" +"," ");
			StringProcessor sp = new StringProcessor(text);
			LambdaExpression expr = lambdaExpressionParser(sp,true);
			if(sp.hasNext()){
				throw new ParseException("hib�s kifejez�s, nem dolgoztuk fel teljesen! ",0);
			}
			return expr;
		}

		private LambdaExpression lambdaExpressionParser(StringProcessor sp, boolean recursive) throws NoSuchAlgorithmException, ParseException {
			LambdaExpression le;
			if(sp.hasNext()){
				String token = sp.eatNext();
				// \\x:Bool -> Nat -> Bool.y
				if(vp.isVariable(token)){
					le = new LambdaTypeVariable(new Variable(token));
				}else if(isLambdaAbstrastor(token)){
					String token2 = sp.eatNext();
					if(vp.isVariable(token2)){
						if(sp.eatNext().equals(":")){
							TypeParser tp = new TypeParser();
							Type type = tp.typeParser(sp);
							if(sp.eatNext().equals(".")){
								le = new LambdaTAbstraction(
										new Variable(token2),
										type,
										this.lambdaExpressionParser(sp,true));
							}else{
								throw new ParseException("hib�s absztrakci�: \\x ut�n '.'-nak kell j�nnie! ",0);
							}
						}else{
							throw new ParseException("hib�s absztrakci�: \\x ut�n ':'-nak kell j�nnie! ",0);
						}
					}else{
						throw new ParseException("hib�s absztrakci�: \\ ut�n v�ltoz�nak kell j�nnie! ",0);
					}
					
				}else if(token.equals("(")){
					LambdaExpression innerExp = 
							this.lambdaExpressionParser(sp,true);
					if(sp.eatNext().equals(")")){
						le = innerExp;
//						le = new LambdaTypeBrackets(
//								new LeftBracket(), 
//								innerExp, 
//								new RightBracket());
					}else{
						throw new ParseException("hib�s z�r�jeles lamda kifejez�s",0);
					}
					
				}else{
					throw new ParseException("fel nem ismert kifejez�s",0);
				}
			}else{
				throw new ParseException("hib�s kifejez�s",0);
			}
			if(recursive){
				while(sp.hasNext() && sp.getNext().equals(" ")){
					sp.eatNext();
					LambdaExpression left = le;
					LambdaExpression rigth = lambdaExpressionParser(sp, false);
					le = new LambdaTApplication(left, new SpaceToken(), rigth);
				}
			}
			
			return le;
		}

		private boolean isDot(String token) {
			return token.equals(".");
		}

		private boolean isLambdaAbstrastor(String token) {
			return token.equals("\\");
		}

		
}
