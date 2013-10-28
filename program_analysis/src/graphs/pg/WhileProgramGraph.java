package graphs.pg;

import ast.statement.WhileStatement;

public class WhileProgramGraph extends ProgramGraph {
	public WhileProgramGraph (WhileStatement st, int initialNode, int finalNode) {   //constructor 
		String boolBlock = st.getCondition().toString();
		
		if (edges.isEmpty()== false)
			edges.add(new Edge(initialNode, boolBlock, edges.get(edges.size()-1).qt +1)); 
		else edges.add( new Edge(1, boolBlock, 2) ); 
		
		// graph is created recursively
		ProgramGraphFactory.create(st.getBody (), edges.get(edges.size()-1).qt, initialNode);
		edges.add(new Edge(initialNode, boolBlock = '!'+ boolBlock, finalNode > 0 ? finalNode : edges.get(edges.size()-1).qs +1 ) ); 
		// TODO Nikita Check what is written below, since I think ternary operator does this
		//add exit from the loop: if this While is the last statement in the else branch than finalNode will be set and 
		//we use it, otherwise we use edges.last().qs +1 since edges.last().qt is the initial node of the enter condition for 
		//the loop.
	}
}
