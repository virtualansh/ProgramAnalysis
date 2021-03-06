import interval_analysis.IntervalAnalysis;
import free_variables.FreeVariableGenerator;
import graphs.fg.*;
import graphs.pg.*;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

import detectionOfSign_analysis.DSWorklist;
import SecurityAnalysis.SecLevelWorklist;
import ast.Program;
import parser.TheLangLexer;
import parser.TheLangParser;
import program_slicing.ProgramSlice;

/**
 * print the AST built
 * 
 * @author zhenli
 * 
 */
public class Main {

	public static void main(String args[]) throws Exception {

		// wrong inputs
		if (args.length <= 1) {
			printCommands();
			return;
		}
		// get cmd
		String cmd = args[1];
		int min = 0, max = 10;

		if (cmd.equals("3")) {
			// interval analysis require two additional inputs
			if (args.length < 4) {
				printCommands();
				return;
			}
			try {
				min = Integer.parseInt(args[2]);
				max = Integer.parseInt(args[3]);
			} catch (Exception e) {
				// the inputs are not two integers
				printCommands();
				return;
			}
		}

		// parsing
		TheLangLexer lex = new TheLangLexer(new ANTLRFileStream(args[0]));
		CommonTokenStream tokens = new CommonTokenStream(lex);

		// building ast
		TheLangParser parser = new TheLangParser(tokens);
		Program program = parser.program();
		// print the ast
		System.out.println(program.toString());

		
		ProgramGraph pg = new ProgramGraph(program.getStatement());
		if (cmd.equals("2") || cmd.equals("3") || cmd.equals("4")) {
			System.out.println("\nProgram graph: ");
			System.out.println(pg.toString());
		}

		
		FlowGraph fg = FlowGraphFactory.create(program.getStatement());
		if (cmd.equals("1")) {
			System.out.println("\nFlow graph:");
			System.out.println(fg.toString());
		}
		// ...
		FreeVariableGenerator.extractVariables();
		if (cmd.equals("1"))
				System.out.println(FreeVariableGenerator.printVariables());

		if (cmd.equals("1")) {
			// Program slicing
			ProgramSlice.getProgramSlice(fg, 6);
			program.printWithLabels();
			ProgramSlice.printProgramSlice();
		} else if (cmd.equals("2")) {
			// Detect of signs
			DSWorklist dsw = new DSWorklist(ProgramGraph.edges,
					FreeVariableGenerator.getAllVariables());
			dsw.printSolutionsTable();

			System.out.println("\nLow boundary violations for array indexing:");
			System.out.println(dsw.toString(dsw
					.findLowBoundaryViolations(ProgramGraph.edges)));
		} else if (cmd.equals("3")) {
			// interval_analysis
			IntervalAnalysis
					.analyze(min, max, FreeVariableGenerator.getAllVariables(),
							ProgramGraph.edges);
			IntervalAnalysis.printSolutionTable();
			IntervalAnalysis.printViolatedEdges();
		} else if (cmd.equals("4")) {
			// SecAnalysis
			SecLevelWorklist slw = new SecLevelWorklist(ProgramGraph.edges,
					ProgramGraph.boolEndingedges, program);
			slw.printSolutionsTable();
			System.out.println("\nSecurity level violations:");

			System.out.println(slw.toString(slw
					.findSecurityLevelViolations(ProgramGraph.edges)));
		}
	}

	public static void printCommands() {
		System.out.println("Usage: File cmd [min max]");
		System.out
				.println("cmd:\t1 - program slice\n\t2 - detection of signs\n\t3 - interval analysis"
						+ "\n\t4 - security analysis");
		System.out.println("Example:\tfile1 1\n\t\tfile1 3 0 4");
	}
}
