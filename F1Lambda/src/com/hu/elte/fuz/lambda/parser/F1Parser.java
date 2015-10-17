package com.hu.elte.fuz.lambda.parser;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class F1Parser {
	// 5+((a+b)*2)
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
				
				if(isVariable(token)){
					le = new LambdaTypeVariable(new Variable(token));
				}else if(isLambdaAbstrastor(token)){
					String token2 = sp.eatNext();
					if(isVariable(token2)){
						String token3 = sp.eatNext();
						if(isDot(token3)){
							le = new LambdaTAbstraction(
									new LambdaAbstractor(),
									new Variable(token2),
									new DotToken(),
									this.lambdaExpressionParser(sp,true));
						}else{
							throw new ParseException("hib�s absztrakci�: \\x ut�n '.'-nak kell j�nnie! ",0);
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

		private boolean isVariable(String token) {
			Pattern p = Pattern.compile("[a-zA-Z]");
			Matcher m = p.matcher(token+"");
			return m.find();
		}
}
