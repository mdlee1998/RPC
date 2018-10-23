package rpc;


/**
 * Creates a term of a polynomial, with a coefficient and an exponent.  These
 * terms use the variable of x in the string form.
 * @author Matthew
 *
 */
public class Term {
	
	
	//Fields//
	
	//Coefficient of the term
	private double coefficient;
	//Exponent of the term
	private int exponent;
	
	/**
	 * Sets the coefficient and exponent fields of the object
	 * @param coefficiant The term's coefficient
	 * @param exponent The term's exponent
	 */
	public Term(double coefficient,int exponent) {
		this.coefficient = coefficient;
		this.exponent = exponent;
	}
	
	/**
	 * Returns a string of the term, using x as a variable and a ^ as an indicator of an
	 * exponent.  
	 */
	public String toString() {
		double coefficiant = this.coefficient;
		String string = "";
		//Does nothing if the coefficient is 0
		if(coefficiant == 0) {
			return string;
		}
		//Only return coefficient
		if(exponent == 0) {
			string+=coefficiant;
			return string;
		}
		//Only returns coefficient and x
		else if(exponent == 1) {
			if(coefficiant == 1) {
				string+="x";
				return string;
			}
			else {
				string+=coefficiant+"x";
				return string;
			}
		}
		//Only returns x and exponent
		if(coefficiant == 1) {
			string+="x^"+exponent;
			return string;
		}
		//Returns coefficient, x and exponent
		else {
			string +=coefficiant+"x^"+exponent;
			return string;
		}
	}
	
	/**
	 * Tells whether or not the term is negative
	 * @return False if positive, true if negative
	 */
	public boolean isNegative() {
		if(coefficient<0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Makes a term negative
	 */
	public void makeNeg() {
		coefficient*=-1;
	}
	/**
	 * Sets the coefficient of the term
	 * @param co The new coefficient of the term
	 */
	public void setCo(double co) {
		coefficient = co;
	}
	
	/**
	 * Sets the exponent of the term
	 * @param ex The new exponent
	 */
	public void setExp(int ex) {
		exponent = ex;
	}
	
	/**
	 * Gets the coefficient of the term
	 * @return The coefficient
	 */
	public double getCo() {
		return coefficient;
	}
	
	/**
	 * Gets the exponent of the term
	 * @return The exponent
	 */
	public int getExp() {
		return exponent;
	}
	
	/**
	 * Clones the term
	 */
	@Override
	public Term clone() {
		Term temp = new Term(getCo(),getExp());
		return temp;	
	}
	
	/**
	 * Takes a string of a term and sets this term to be equal to the string
	 * @param strTerm The string term
	 */
	public void toTerm(String strTerm) {
		double coEff = 0;
		int exp = 0;
		//Creates an empty string
		String temp = "";
		//While the input is not an empty string
		while(!strTerm.equals("")) {
			//Make a variable with the first character in the string
			char tempChar = strTerm.charAt(0);
			//Remove that character
			if(strTerm.length()>1) {
				strTerm = strTerm.substring(1,strTerm.length());
			}
			else {
				strTerm = "";
			}
			//If an x is reached, or the string is empty
			if(tempChar =='x'||strTerm.equals("")) {
				//If there is no exponent
				if(tempChar == 'x') {
					exp = 1;
				}
				//If the character is a digit or the new string is empty
				if(temp.equals("") || Character.isDigit(tempChar)) {
					temp+=tempChar;
				}
				char first = temp.charAt(0);
				//If the only character is an x
				if(first == 'x') {
					coEff = 1;
					exp = 1;
				}
				//Sets the coefficient to the current string value
				else {
					coEff = Double.parseDouble(temp);
					temp = "";
				}
			}
			//For exponent
			else if(tempChar == '^') {
				temp = "";
				while(!strTerm.equals("")) {
					tempChar = strTerm.charAt(0);
					if(strTerm.length()>1) {
						strTerm = strTerm.substring(1,strTerm.length());
					}
					else {
						strTerm = "";
					}
					temp+=tempChar;
				}
				exp=Integer.parseInt(temp);
			}
			
			else {
				temp+=tempChar;
			}
		}
		//Sets the new term
		setCo(coEff);
		setExp(exp);
	}
	

}

