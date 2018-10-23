package rpc;

import java.util.Scanner;

import rpc.RPC.SyntaxErrorException;

public class RPCMain {
	
	public static void main(String args[]) throws SyntaxErrorException {
		RPC r = new RPC();
		Polynomial answer;
		while(true) {	
			answer(answer=r.eval(getInput()));
		}
	}
	
	
	public static String getInput() {
		Scanner in = new Scanner(System.in);
		//Prints instructions
		System.out.println("Enter an expression in postfix into the console. For polynomials\n"
				+ " put no spaces between the terms or operators.  Put spaces between each polynomial\n"
				+ " as well as each operator.  For example:  (2x^3+3x 4x^3-2x+4 *) is the same as\n"
				+ "(2x^3+3x)*(4x^3-2x+4).  Enter q to quit game.  Only enter numbers, x's\n"
				+ " carots and operators");
		String expression = in.nextLine();
		//Quit game
		if(expression.toLowerCase().equals("q")) {
			in.close();
			System.exit(0);
		}
		return expression;
	}
	
	//Reads answer if there is one
	public static void answer(Polynomial answer) {
		if(answer != null) {
			System.out.println("Your answer is:   "+answer.toString()+"\n\n");
		}
		else {
			System.out.println("Try again");
		}
	}

}

