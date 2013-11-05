package ast.bool;

import java.util.Vector;

import dynamic_analysis.Environment;
import dynamic_analysis.VariableNotDefinedException;

public class NotExpr extends BoolExpr {

	private BoolExpr expression;
	
	public NotExpr(BoolExpr expression) {
		this.expression = expression;
	}
	
	@Override
	public boolean evaluate(Environment env) throws VariableNotDefinedException {
		return !expression.evaluate(env);
	}
	@Override
	public Vector<String> getVariables() {
		Vector<String> vars = new Vector<String>();
		try {
		vars.addAll(expression.getVariables());
			return vars;
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public String toString() {
		return  "!" + expression;
	}
}
