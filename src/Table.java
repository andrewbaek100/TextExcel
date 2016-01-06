//By Ryan Chan
//CDM AP Computer Science 2016
import java.util.*;
import persistence.*;
public class Table implements Savable{
	public Cell[][] table;
	public Table(int x,int y){ //Creates new array of Cell objects and creates objects to fill the array
		table = new Cell[x][y];
		for (int i=0;i<table.length;i++){
			for (int j=0;j<table[i].length;j++){
				table[i][j]=new Cell();
			}
		}
	}
	public Table(){ //Default size is 12x12
		this(12,12);
	}
	public String[] getSaveData(){
		String[] dataTable = new String[table.length*table[0].length+2];
		dataTable[0]=Integer.toString(table.length);
		dataTable[1]=Integer.toString(table[0].length);
		for (int i=0;i<table.length;i++){
			for (int j=0;j<table[i].length;j++){
				dataTable[(i)*table[0].length+j+2]=table[i][j].toString();
			}
		}
		return dataTable;
	}
	public void loadFrom(String[] saveData){
		table = new Cell[Integer.parseInt(saveData[0])][Integer.parseInt(saveData[1])];
		String coords;
		for (int i=0;i<table.length;i++){
			for (int j=0;j<table[i].length;j++){
				coords = Character.toChars('A'+j)[0]+Integer.toString(i+1);
				setCell(coords,saveData[(i)*table[0].length+j+2]);
			}
		}
	}
	public void printTable(){	//Prints table
		headerRow(table.length);
		for (int i=0;i<table.length;i++){
			drawCell(Integer.toString(i+1)); //Draws table numbers
			for (int j=0;j<table[i].length;j++){ //Creates cells with content from cells
				drawCell(table[i][j].getText());
			}
			System.out.println();
			drawBar(table[i].length);
			System.out.println();
		}
	}
	private static void drawCell(String content){	//Draws a cell using a given String
		if (content.length()>12){
			content=content.substring(0, 11)+">";
		}
		int spaceLength = (12 - content.length()) / 2;	//Calculates needed spaces to center String
		for (int j = 1; j <= spaceLength; j++) {
			System.out.print(" ");
		}
		if(content.length()%2==1){
			System.out.print(" ");
		}
		System.out.print(content);
		for (int j = 1; j <= spaceLength; j++) {
			System.out.print(" ");
		}
			System.out.print("|");
	}
	private static void drawBar(int size){	//Draws a bar of -'s
		for (int i = 0; i <= size; i++) {
			for (int j = 1; j <= 12; j++) {
				System.out.print("-");
			}
			System.out.print("+");
		}
	}
	private void headerRow(int size){	//Draws the header row of the table with letters
		char collumn='A';
		drawCell(" ");
		for (int i=1;i<=size;i++){
			drawCell(Character.toString(collumn));
			collumn++;	//Iterates column character
		}
		System.out.println();
		drawBar(size);
		System.out.println();
	}
	public void setCell(String address, String inputContent){//Sets a specified cell
		char collum=address.charAt(0); //Determines table coordinates
		String row=address.substring(1);
		int x = collum-'A';
		int y = Integer.parseInt(row)-1;
		try
		{	//Checks if input is a double, and if so, creates a double Cell
			double inputDouble=Double.parseDouble(inputContent);
			table[y][x]=new DoubleCell(inputDouble);
		}
		catch(NumberFormatException e){
			
			if (inputContent.charAt(0)=='"'){ //If input is a String, creates a String cell
				System.out.println("c");
				table[y][x]=new StringCell(inputContent);
			}
			else if (countOccurance(inputContent,"/")==2&&(inputContent.contains(" ")==false)){//Determines if input string is a date, if so creates a Date cell
				table[y][x]=new DateCell(inputContent);
			}
			else if (inputContent.equals("<empty>")){ //If input is <empty>, an empty default cell is created
				table[y][x]=new Cell();
			}
			else if (inputContent.charAt(0)=='('){ //For use in functions, not yet implemented
				System.out.println("b");
				table[y][x]=new FormulaCell(inputContent,table);
			}
		}
	}
	public String outputCell(String address){ //Outputs specified cell
		char collum=address.charAt(0);
		String row=address.substring(1);
		int x = collum-'A';
		int y = Integer.parseInt(row)-1;
		return table[y][x].toString();
	}
	public void clear(){ //Clears entire table
		for (int i=0;i<table.length;i++){
			for (int j=0;j<table[i].length;j++){
				table[i][j]=new Cell();
			}
		}
	}
	public void clearCell(String address){ //Clears specific cell
		char collum=address.charAt(0);
		String row=address.substring(1);
		int x = collum-'A';
		int y = Integer.parseInt(row)-1;
		table[y][x]=new Cell();
	}
	public int countOccurance(String input,String value){//Counts the number of occurrences of a String within another String
		int count = 0;
		int index = 0;
		while (input.indexOf(value,index)!=-1){
			index=input.indexOf(value,index)+1;
			if(index==input.length())
			break;
			count++;
		}
		return count;
	}
}