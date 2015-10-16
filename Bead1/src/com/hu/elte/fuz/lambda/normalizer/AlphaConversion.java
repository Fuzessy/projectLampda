package com.hu.elte.fuz.lambda.normalizer;

import java.util.HashSet;
import java.util.Set;

import com.hu.elte.fuz.lambda.parser.notypelambda.DotToken;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaAbstractor;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaExpression;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTAbstraction;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTApplication;
import com.hu.elte.fuz.lambda.parser.notypelambda.LambdaTypeVariable;
import com.hu.elte.fuz.lambda.parser.notypelambda.SpaceToken;
import com.hu.elte.fuz.lambda.parser.notypelambda.Variable;

public class AlphaConversion {

	public boolean canSubVariable(Variable var, SubstitionPack subPack){
		return (var.equals(subPack.subIt));
	}
	
	public LambdaExpression doSubstition(LambdaExpression expr, SubstitionPack subPack){
		LambdaExpression ret;
		
		
		if(expr.getClass().equals(LambdaTypeVariable.class)){
			LambdaTypeVariable ltvar = (LambdaTypeVariable) expr;
			if(canSubVariable(ltvar.getVariable(), subPack)){
				// van csere
				ret = subPack.getToThis();
			}else{
				// nincs csere
				ret = expr;
			}
		}else if(expr.getClass().equals(LambdaTApplication.class)){
			LambdaTApplication application = (LambdaTApplication) expr;
			LambdaExpression le1 = doSubstition(application.getLambdaExpression(), subPack);
			LambdaExpression le2 = doSubstition(application.getLambdaExpression2(), subPack);
			ret = new LambdaTApplication(le1, new SpaceToken(), le2);
		}else if(expr.getClass().equals(LambdaTAbstraction.class)){
			LambdaTAbstraction abstraction = (LambdaTAbstraction) expr;
			Variable y = subPack.getSubIt();
			Variable x = abstraction.getVariable();
			LambdaExpression _G = subPack.getToThis();
			LambdaExpression _E = abstraction.getLambdaExpression();
			// \x.E[y:=G]
			// ha x = y akkor \x.E
			if(x.equals(y)){
				ret = expr;
			}else if(!x.equals(y) &&
					!_G.getFreeVariables().contains(x)){
				
				// ha x != y and x not element of FV(G)
				ret = new LambdaTAbstraction(
						new LambdaAbstractor(), 
						x,
						new DotToken(),
						this.doSubstition(_E, subPack));
			}else if(!x.equals(y) 
					&& _G.getFreeVariables().contains(x)){
				// x != y és x eleme FV(G) akkor \z.E[x:=z][y:=G]
				Set<Variable> allvariable = new HashSet<>();
				allvariable.addAll(expr.getVariables());
				allvariable.addAll(_G.getVariables());
				Variable newVar = getNewVariable(allvariable, x);
				SubstitionPack alphaSubPack = new SubstitionPack(x, 
						new LambdaTypeVariable(
								newVar));
				ret = doSubstition(_E, alphaSubPack);
				ret = doSubstition(ret, subPack);
				ret = new LambdaTAbstraction(newVar, ret);
			}else{
				throw new RuntimeException("Hiba!!!");
			}
		}else{
			ret = expr;
		}
		return ret;
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
			//System.out.println(j + "-"+ ch);
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
