package ast.statement;

import java.util.Vector;

import dynamic_analysis.Environment;
import dynamic_analysis.VariableNotDefinedException;
import ast.arith.ArithExpr;

/**
 * Unfinished evaluate method
 * write a;
 * @author zhenli
 *
 */
public class WriteStatement extends Statement{
	ArithExpr expression;
	
	public WriteStatement(ArithExpr expression) {
		this.expression = expression;
	}

	@Override
	public Vector<String> getVariables() {
		Vector<String> vars = new Vector<String>();
		try {
			vars.addAll(expression.getVariables());
		} catch (Exception e) {
		}
			if (!vars.isEmpty())
				return vars;
			else
				return null;
	}
	
	@Override
	public Vector<String> getArrays() {
		Vector<String> vars = new Vector<String>();
		vars.addAll(expression.getArrays());
		return vars;	
	}
	@Override
	public void evaluate(Environment env) throws VariableNotDefinedException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "write " + expression + ";";
	}

	// setter and getter
	public ArithExpr getExpression() {
		return expression;
	}

	public void setExpression(ArithExpr expression) {
		this.expression = expression;
	}

	@Override
	public int printWithLabels(int i) {
		System.out.println("[write "+expression +"]^"+i+";");
		return ++i;
	}
	
	

}
