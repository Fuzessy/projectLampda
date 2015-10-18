package com.hu.elte.fuz.lambda.parser;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

public class TypeParser {
	/**
	 * Visszaadja a típust
	 * @param sp
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 */
	public Type typeParser(StringProcessor sp) throws NoSuchAlgorithmException, ParseException{
		// A -> B -> C esetén A, (A -> B) -> C esetén A -> B 
		//     *                         *
		//   A   *                    *     C
		//      B  C                A   B
		// 
		// 
		// legbaloldalibb ág meghatározása
		Type typeT = determineNextType(sp);
		// a típuskifejezés folytatódik?
		if(sp.getNext().equals(" ")){
			if(sp.eatNext().equals(" ") &&
					sp.eatNext().equals("-") &&
					sp.eatNext().equals(">") &&
					sp.eatNext().equals(" ")){
				// A típussal és 'B -> C' vagy '(B -> C)-vel hívódik'
				Type typeF = this.typeParser(sp);
				typeT = new TypeExpression(typeT, typeF);
			}
			else{
				throw new ParseException("Típus nem határozható meg, ' -> 'nek kellene következni " , 0);
			}
		}
		
		return typeT;
	}

	/**
	 * (A -> B) -> C esetén A -> B -t
	 *  A -> B esetén A-t ad vissza 
	 * @param sp
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 */
	private Type determineNextType(StringProcessor sp) throws NoSuchAlgorithmException, ParseException {
		Type nextType;
		// (A -> B) -> C esetén A -> B
		if(sp.getNext().equals("(")){
			sp.eatNext();
			nextType = typeParser(sp);
			if(!sp.eatNext().equals(")")){
				throw new ParseException("Typus nem határozható meg: hibás zárójelezés ", 0);
			}
		}else{
			// A -> B esetén A
			String sBaseTypeFrom = sp.eatNextWord();
			nextType = getBaseType(sBaseTypeFrom);
			if(nextType == null){
				throw new ParseException("Alaptípus nem határozható meg: " + sBaseTypeFrom , 0);	
			}
		}
		return nextType;
	}


	private TypeBase getBaseType(String sBaseType) {
		if(sBaseType.equals("Bool")){
			return new TypeBase(TypeBase.TypeValue.BOOL);
		}else if(sBaseType.equals("Nat")){
			return new TypeBase(TypeBase.TypeValue.NAT);
		}
		return null;
	}
}
