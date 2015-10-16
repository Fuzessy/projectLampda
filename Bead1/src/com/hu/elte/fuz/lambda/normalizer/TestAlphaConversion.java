package com.hu.elte.fuz.lambda.normalizer;

import java.util.HashSet;
import java.util.Set;

import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTAbstraction;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTypeVariable;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class TestAlphaConversion {
	
	/**
	 * Alfa konverzió
	 * @param _E
	 * @param _F
	 * @return
	 */
	public LambdaTAbstraction solveNameConflict(LambdaTAbstraction abstraction, LambdaExpression _F) {
		while(hasNameConflict(abstraction, _F)){
			Set<Variable> fvE = _F.getFreeVariables();
			Set<Variable> bcF = abstraction.getBoundVariables();
			Variable conflictVar = null;
			for(Variable fv : fvE){
				if(bcF.contains(fv)){
					conflictVar = fv;
				}
			}
			
			Set<Variable> allvariable = new HashSet<>();
			allvariable.addAll(fvE);
			allvariable.addAll(bcF);
			Variable newVariable = 
					getNewVariable(allvariable,conflictVar);
			
			SubstitionPack substitionPack = 
					new SubstitionPack(conflictVar, 
							new LambdaTypeVariable(newVariable));
			abstraction = doAlphaConverisonAbstraction(abstraction,substitionPack);
			
		}
		return abstraction;
	}

	private LambdaTAbstraction doAlphaConverisonAbstraction(LambdaTAbstraction abstraction, SubstitionPack substitionPack) {
		LambdaTAbstraction retAbstraction = null;
		Variable x = abstraction.getVariable();
		Variable y = substitionPack.getSubIt();
		LambdaExpression _G = substitionPack.getToThis();
		// \x.E[y=G]
		// \x.E ha x=y
		if(x.equals(y)){
			retAbstraction = abstraction;
		}else if(!x.equals(y)
				&& !_G.getFreeVariables().contains(x)
				){
			// \x.E[y:=G] ha x!=y és x nemEleme G szabad váltizóinak
			
		}
		
		
		return abstraction;
	}

	/**
	 * A conflictVar változó utáni legelsõ szabad változónevet
	 * adja vissza abc sorrendben (ciklikus: z után a jön)
	 * @param allvariable
	 * @param conflictVar
	 * @return
	 */
	public Variable getNewVariable(Set<Variable> allvariable, Variable conflictVar) {
		Variable newVar = null;
		char ch = conflictVar.getValue().charAt(0);
		boolean bFound = false;
		for(int i = 0; i<26 && !bFound; i++){
			int j = ((ch - 'a' + 1) % 26) + 'a';
			ch = (char) j;
			System.out.println(j + "-"+ ch);
			newVar = new Variable(ch+ "");
			if(!allvariable.contains(newVar)){
				bFound = true;
			}
		}
		return newVar;
	}

	/**
	 * Névkonfliktus ellenõrzés
	 * Az F szabad változói nem válnak E kötött változõivá a E[x:=F] során
	 * @param _E
	 * @param _F
	 */
	public boolean hasNameConflict(LambdaExpression _E, LambdaExpression _F) {
		boolean bNameClonflict = false;
		Set<Variable> fvE = _F.getFreeVariables();
		Set<Variable> bcF = _E.getBoundVariables();
		for(Variable fv : fvE){
			if(bcF.contains(fv)){
				bNameClonflict = true;
				break;
			}
		}
		return bNameClonflict;
	}
}
