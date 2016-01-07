//By Ryan Chan
//AP Computer Science 2016
public class FormulaCell extends Cell{//Cell of type Formula
	public Cell[][] table;
	boolean error = false;
	public FormulaCell(String input,Cell[][] table){
		type="formula";
		content=input;
		this.table=table;
	}
	public double getNumber(){
		return Double.parseDouble(evaluate());
	}
	public String getText(){
		return evaluate();
	}
	public String toString(){
		return content;
	}
	public String evaluate() { //Determines order of operation and executes operations accordingly
		String input = content.substring(2,content.length()-2);
		if (input.contains("sum")){
			return Double.toString(sumAvg(input,"sum"));
		} else if (input.contains("avg")){
			return Double.toString(sumAvg(input,"avg"));
		}else{
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
			num2=getNumFromString(excerpt.substring(excerpt.lastIndexOf(" ")+1));
			String operation = input.substring(operatorPos,operatorPos+1);
			if (error==true){
				return "Error";
			}
			if (operation.equals("+")){
				combined= Double.toString(num1+num2);
			} else if (operation.equals("-")) {
				combined = Double.toString(num1 - num2);
			} else if (operation.equals("*")) {
				combined = Double.toString(num1 * num2);
			} else if (operation.equals("/")) {
				combined = Double.toString(num1 / num2);
			} else {
				return "Error";
			}
			input = firstHalf + combined + secondHalf;			
		}
		return input;
		}
	}
	private double getNumFromString(String input){
		if ((input.charAt(0)>='A')&&(input.charAt(0)<='Z')){
			char collum=input.charAt(0);
			String row=input.substring(1);
			int x = collum-'A';
			int y = Integer.parseInt(row)-1;
			if (table[y][x].type.equals("double")||table[y][x].type.equals("formula")){
				return(table[y][x].getNumber());
			} else {
				System.out.println("aaa");
				error=true;
				return 0;
				}
		}
		else {
			return Double.parseDouble(input);
		}
	}
	private double sumAvg(String input, String mode){
		double total = 0;
		double count = 0;
		char collum=input.charAt(4);
		String row=input.substring(5,input.indexOf(" ",5));
		int x1 = collum-'A';
		int y1 = Integer.parseInt(row)-1;
		
		collum=input.charAt(input.indexOf(" - ")+3);
		row=input.substring(input.indexOf(" - ")+4);
		int x2 = collum-'A';
		int y2 = Integer.parseInt(row)-1;
		for (int i = y1;i<=y2;i++){
			for (int j = x1;j<=x2;j++){
				if (table[i][j].type.equals("double")||table[i][j].type.equals("double")){
					total+=table[i][j].getNumber();
					count++;
				}
			}
		}
		if (mode.equals("avg")){
			return total/count;
		} else if (mode.equals("sum")){
			return total;
		} else {
			return 999999;
		}
	}
}