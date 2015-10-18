package com.hu.elte.fuz.lambda.parser;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

public class TypeParser {
	/**
	 * Visszaadja a t�pust
	 * @param sp
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 */
	public Type typeParser(StringProcessor sp) throws NoSuchAlgorithmException, ParseException{
		// A -> B -> C eset�n A, (A -> B) -> C eset�n A -> B 
		//     *                         *
		//   A   *                    *     C
		//      B  C                A   B
		// 
		// 
		// legbaloldalibb �g meghat�roz�sa
		Type typeT = determineNextType(sp);
		// a t�puskifejez�s folytat�dik?
		if(sp.getNext().equals(" ")){
			if(sp.eatNext().equals(" ") &&
					sp.eatNext().equals("-") &&
					sp.eatNext().equals(">") &&
					sp.eatNext().equals(" ")){
				// A t�pussal �s 'B -> C' vagy '(B -> C)-vel h�v�dik'
				Type typeF = this.typeParser(sp);
				typeT = new TypeExpression(typeT, typeF);
			}
			else{
				throw new ParseException("T�pus nem hat�rozhat� meg, ' -> 'nek kellene k�vetkezni " , 0);
			}
		}
		
		return typeT;
	}

	/**
	 * (A -> B) -> C eset�n A -> B -t
	 *  A -> B eset�n A-t ad vissza 
	 * @param sp
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 */
	private Type determineNextType(StringProcessor sp) throws NoSuchAlgorithmException, ParseException {
		Type nextType;
		// (A -> B) -> C eset�n A -> B
		if(sp.getNext().equals("(")){
			sp.eatNext();
			nextType = typeParser(sp);
			if(!sp.eatNext().equals(")")){
				throw new ParseException("Typus nem hat�rozhat� meg: hib�s z�r�jelez�s ", 0);
			}
		}else{
			// A -> B eset�n A
			String sBaseTypeFrom = sp.eatNextWord();
			nextType = getBaseType(sBaseTypeFrom);
			if(nextType == null){
				throw new ParseException("Alapt�pus nem hat�rozhat� meg: " + sBaseTypeFrom , 0);	
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
