package rpc;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;


/**
 * A calculator that uses reverse-polish notation
 * @author Matthew
 */
public class RPC {
	
	/**
	 * Exception for when the calculator is given an expression with 
	 * improper syntax, such as too many terms or operators
	 */
	//Inspired by page 173 of Data Structures 2nd Edition
	public static class SyntaxErrorException extends Exception{
		SyntaxErrorException(String message){
			super(message);
		}
	}
	
	//Fields//
	
	//String of operators
	private static final String OPERATORS = "+-*/";
	
	//stack that the operands will go on until an operator is read
	private Stack<Polynomial> operandStack;
	
	//Methods//
	
	/**
	 * Determines what the calculator must do when it reads a character 
	 * of an operator
	 * @param op The operator character
	 * @return The polynomial result of the operation
	 */
	//Inspired by page 174 of Data Structures 2nd Edition
	private Polynomial evalOp(char op) {
		//Right hand number
		Polynomial rhs = operandStack.pop();
		//Left hand number
		Polynomial lhs = operandStack.pop();
		//Polynomial result of the operation
		Polynomial result=new Polynomial();
		
		//Performs the operation based on whatever operator is given
		switch(op) {
			case '+' : result = lhs.addPol(rhs);
			break;
			case '-' : result = lhs.subtractPol(rhs);
			break;
			case '*' : result = lhs.multPol(rhs);
			break;
//			case '/' : result = lhs / rhs;
//			break;
		}
		return result;
	}
	
	/**
	 * Evaluates a string of an expression, first by converting the string into polynomial objects,
	 * then using evalOp to find the final polynomial solution
	 * @param expression
	 * @return
	 * @throws SyntaxErrorException
	 */
	//Inspired by page 174 of Data Structures 2nd Edition
	public Polynomial eval(String expression) throws SyntaxErrorException {
		operandStack = new Stack<Polynomial>();
		//Cuts string into parts made up of operators and polynomials
		String[] tokens = expression.split("\\s+");
		try {
			//Goes through each of the strings
			for(String nextToken : tokens) {
				char firstChar = nextToken.charAt(0);
				//Uses the operator on the previous two polynomials in the stack 
				//and adds to the string
				 if(isOperator(firstChar) && nextToken.length() == 1) {
					Polynomial result = evalOp(firstChar);
					operandStack.push(result);
				}
				 //Turns string polynomials into polynomial objects and adds them to a stack
				else if(Character.isDigit(firstChar) || isOperator(firstChar)) {
					Polynomial value = new Polynomial();
					value.toPol(nextToken);
					operandStack.push(value);
				}
				else {
					System.out.println("Invalid character encountered:  "+firstChar+"\n");
					throw new SyntaxErrorException("Invalid character encountered: "+firstChar);
					
				}
			}
			Polynomial answer = operandStack.pop();
			//Returns the answer when there are no more operators
			if(operandStack.empty()) {
				return answer;
			}
			else {
				System.out.println("Syntax Error: Too many operands\n");
				throw new SyntaxErrorException("Syntax Error: Stack should be empty");
			}
		}catch(EmptyStackException ex) {
			System.out.println("Too many operators\n");
			return null;
		}
		catch(SyntaxErrorException e) {
			System.out.println("You entered an expression with improper syntax.\n");
			return null;
		}
		
	}
	
	/**
	 * Checks if a given character is an operator
	 * @param ch The character being checked
	 * @return true if ch is an operator, false otherwise
	 */
	//Inspired by page 174 of Data Structures 2nd Edition
	private boolean isOperator(char ch) {
		return OPERATORS.indexOf(ch) != -1;
	}
	
	
	
	
}