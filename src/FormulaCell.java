//By Ryan Chan
//AP Computer Science 2016
public class FormulaCell extends Cell{//Cell of type Formula
	public String type="formula";
	public Cell[][] table;
	boolean error = false;
	public FormulaCell(String input,Cell[][] table){
		content=input;
		this.table=table;
	}
	public double getContent(){
		return Double.parseDouble(parse());
	}
	public String toString(){
		System.out.println("a");
		return parse();
	}
	public String parse() { //Determines order of operation and executes operations accordingly
		String input = content;
		int operatorPos = 0;
		int startPos = 0;
		int endPos = 0;
		String excerpt, firstHalf, secondHalf, combined = null;
		double num1,num2;
		while (input.indexOf(" ") != -1) { //Loops until there is only one term in the string, which will be the final answer
			if ((input.indexOf("*") != -1)||(input.indexOf("/ ") != -1)) {	//Searches for multiplication and division first
				if ((input.indexOf("*")!=-1)&&input.indexOf("/ ")!=-1){	//Finds first instance of multiplication or division, if present
					operatorPos = Math.min(input.indexOf("*"),input.indexOf("/ "));
				} else if ((input.indexOf("*")!=-1)&&input.indexOf("/ ")==-1){
					operatorPos = input.indexOf("*");
				} else if ((input.indexOf("*")==-1)&&input.indexOf("/ ")!=-1){
					operatorPos=input.indexOf("/ ");
				}
			} else if ((input.indexOf("+") != -1)||(input.indexOf("- ") != -1)) {	//Searches for addition and subtraction if no multiplication or division remain
				if ((input.indexOf("+")!=-1)&&input.indexOf("- ")!=-1){	//Finds first instance of addition or subtration, if present
					operatorPos = Math.min(input.indexOf("+"),input.indexOf("- "));
				} else if ((input.indexOf("+")!=-1)&&input.indexOf("- ")==-1){
					operatorPos = input.indexOf("+");
				} else if ((input.indexOf("+")==-1)&&input.indexOf("- ")!=-1){
					operatorPos=input.indexOf("- ");
				}
			}

			if (input.lastIndexOf(" ", operatorPos - 2) != -1) {	//Checks if the number before the operator is the first in the string and records starting position
				startPos = input.lastIndexOf(" ", operatorPos - 2) + 1;	
			} else {
				startPos = 0;
			}
			if (input.indexOf(" ", operatorPos + 2) != -1) {	//Checks if the number after the operator is the last number and records end position
				endPos = input.indexOf(" ", operatorPos + 2);
			} else {
				endPos = input.length();
			}

			firstHalf = input.substring(0, startPos);	//First half of string taken up until the starting point for the operation
			secondHalf = input.substring(endPos);	//Second half of string taken from ending point of the operation to string ending
			excerpt = input.substring(startPos, endPos);	//Operation string is taken between starting point and ending point
			
			num1=getNumFromString(excerpt.substring(0, excerpt.indexOf(" ")));
			num2=getNumFromString(excerpt.substring(excerpt.indexOf(" "+3)));
			String operation = input.substring(operatorPos,operatorPos+1);
			if (error=true){
				return "Error";
			}
			if (operation.equals("+")){
				combined= Double.toString(num1+num2);
			}
			input = firstHalf + combined + secondHalf;			
		}
		return combined;
	}
	private double getNumFromString(String input){
		if ((input.charAt(0)>='A')&&(input.charAt(0)<='Z')){
			char collum=input.charAt(0);
			String row=input.substring(1,input.indexOf(" "));
			int x = collum-'A';
			int y = Integer.parseInt(row)-1;
			if (table[y][x].type.equals("double")==false){
				error=true;
				return 0;
			} else {
				return(table[y][x].getNumber());
			}
		}
		else {
			return Double.parseDouble(input);
		}
	}
}