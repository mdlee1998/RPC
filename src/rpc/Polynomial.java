package rpc;

import java.util.ListIterator;
import java.util.Stack;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Creates a polynomial that is made of a number of terms, that
 * are then added together in order of descending exponent
 * @author Matthew
 *
 */
public class Polynomial{
	//List of the terms
	private LinkedList<Term> theList = new LinkedList<Term>();

	/**
	 * Creates a new polynomial object, with an empty linked list
	 */
	public Polynomial() {
		theList = new LinkedList<Term>();
	}
	public void setList(LinkedList<Term> list) {
		theList = list;
	}
	
	/**
	 * Gets the list of the polynomial
	 * @return The list
	 */
	public LinkedList<Term> getList(){
		return theList;
	}
	
	/**
	 * Adds a term to the polynomial
	 * @param newTerm
	 */
	public void add(Term newTerm) {
		//Does nothing if the coefficient does not exist
		if(newTerm.getCo() == 0) {
			return;
		}
		ListIterator<Term> iter = theList.listIterator();
		while(iter.hasNext()) {
			Term thisTerm = iter.next();
			//If exponents of new term and existing are equal, add coefficients
			if(thisTerm.getExp() == newTerm.getExp()) {
				double co = thisTerm.getCo()+newTerm.getCo();
				thisTerm.setCo(co);
				return;
			}
			//Adds the term where it should go in order of descending exponent.
			else if(thisTerm.getExp()<newTerm.getExp()) {
				iter.previous();
				iter.add(newTerm);
				return;
			}
			
		}
		iter.add(newTerm);
	}
	
	/**
	 * Returns a string of the polynomial, in descending order of the exponents
	 */
	public String toString() {
		String polStr = "";
		ListIterator<Term> iter = theList.listIterator();
		if(iter.hasNext()) {
			Term first=iter.next();
			polStr+=first;
			while(iter.hasNext()) {
				Term temp = iter.next();
				if(!temp.isNegative() && temp.getCo() !=0) {
					polStr+="+"+ temp.toString();
				}
				else {
					polStr+=temp.toString();
				}
			}
			
		}
		return polStr;
	}

	/**
	 * Adds a polynomial to the this polynomial
	 * @param addend The polynomial being added
	 * @return A new polynomial that is the sum of these two
	 */
	public Polynomial addPol(Polynomial addend) {
		Polynomial sum = new Polynomial();
		LinkedList<Term> tempList = new LinkedList<Term>();
		ListIterator<Term> tempIter = addend.getList().listIterator();
		//Creates a copy of the addend
		while(tempIter.hasNext()) {
			tempList.add(tempIter.next().clone());
		}
		ListIterator<Term> oldIter = theList.listIterator();
		//Creates a copy of this polynomial
		while(oldIter.hasNext()) {
			sum.add(oldIter.next().clone());
		}
		ListIterator<Term> newIter = tempList.listIterator();
		//adds the two copies together
		while(newIter.hasNext()) {
			sum.add(newIter.next());
		}
		return sum;
	}
	
	/**
	 * Subtracts a polynomial from the one being called
	 * @param subtrahend The polynomial that is being subtracted
	 * @return The difference of the two polynomials
	 */
	public Polynomial subtractPol(Polynomial subtrahend) {
		Polynomial difference = new Polynomial();
		LinkedList<Term> tempList = new LinkedList<Term>();
		ListIterator<Term> tempIter = subtrahend.getList().listIterator();
		//Copies the subtrahend
		while(tempIter.hasNext()) {
			Term temp = tempIter.next().clone();
			temp.makeNeg();
			tempList.add(temp);
		}
		//Copies this polynomial
		ListIterator<Term> oldIter = theList.listIterator();
		while(oldIter.hasNext()) {
			difference.add(oldIter.next().clone());
		}
		//Subtracts the copies
		ListIterator<Term> newIter = tempList.listIterator();
		while(newIter.hasNext()) {
			difference.add(newIter.next());
		}
		return difference;
		
	}
	
	/**
	 * Multiplies a polynomial with the one being called
	 * @param factor The polynomial being multiplied 
	 * @return The product of the two polynomials
	 */
	public Polynomial multPol(Polynomial factor) {
		Polynomial product = new Polynomial();
		LinkedList<Term> tempList = new LinkedList<Term>();
		ListIterator<Term> tempIter = factor.getList().listIterator();
		while(tempIter.hasNext()) {
			ListIterator<Term> oldIter = theList.listIterator();
			Term facTemp=tempIter.next().clone();
			//Iterates through each term of this polynomial, each time
			//multiplying the coefficients of each term and adding the exponents
			while(oldIter.hasNext()) {
				Term oldTemp = oldIter.next().clone();
				double newCo = oldTemp.getCo()*facTemp.getCo();
				
				int newExp = oldTemp.getExp()+facTemp.getExp();
				Term newTerm = new Term(newCo,newExp);
				product.add(newTerm);
			}
		}
		return product;
	}
	
	/**
	 * Turns a string version of a polynomial into a polynomial object
	 * @param pol The string polynomial
	 * @throws NumberFormatException Throws if an incorrect character is detected in the
	 * string
	 */
	public void toPol(String pol) throws NumberFormatException {
		char exception = ' ';
		try {
			Polynomial comPol = new Polynomial();
			pol = pol.replaceAll("\\s", "");
			Stack<Character> operators = new Stack<Character>();
			ArrayList<String> terms = new ArrayList<String>();
			Stack<Character> tempStack = new Stack<Character>();
			while(pol.length() !=0) {
				char tempChar = pol.charAt(pol.length()-1);
				//Throws the exception if a character is wrong
				if(!(tempChar == '+' || tempChar == '-' || Character.isDigit(tempChar) || tempChar == '.' || tempChar == 'x' || tempChar == '^')) {
					exception = tempChar;
					throw new NumberFormatException();
				}
				//Adds the last term of the string to a stack
				tempStack.add(tempChar);
				if(pol.length()>1) {
				pol = pol.substring(0, pol.length()-1);
				}
				else {
					pol="";
				}
				//When an operator is found, or the string is empty, turns the new string into an
				//element of a list
				if(tempChar == '+' || tempChar == '-' || pol.equals("")) {
					if(tempStack.peek() == '+' || tempStack.peek() == '-') {
						tempStack.pop();
					}
					if(tempChar == '+' || tempChar == '-') {
						operators.push(tempChar);
					}
					String temp = "";
					while(!tempStack.empty()) {
						temp+=tempStack.pop();
					}
					terms.add(temp);
				}
				
			}
			if(operators.size() == terms.size()-1) {
				operators.push('+');
			}
			while(!terms.isEmpty()) {
				//Runs through each of the term strings in the arraylist
				// and uses toTerm to make it a term
				String strTerm = terms.remove(terms.size()-1);
				Term term = new Term(0,0);
				term.toTerm(strTerm);
				Polynomial tempPol = new Polynomial();
				tempPol.add(term);
				char operator;
				if(!operators.empty()) {
					operator = operators.pop();
				}
				else {
					operator = '+';
				}
				//Adds or subtracts all the terms
				if(operator == '+') {
					comPol = comPol.addPol(tempPol);
				}
				else if(operator == '-'){
					comPol = comPol.subtractPol(tempPol);
				}
			}
			setList(comPol.getList());
		}
		catch(NumberFormatException ex) {
			System.out.println("You inputted an illegal character:  " + exception);
		}
	}
	
	
}