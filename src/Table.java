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
	public String[] getSaveData(){	//Creates a String array from table data
		String[] dataTable = new String[table.length*table[0].length+2];
		dataTable[0]=Integer.toString(table.length);	//First 2 indexes are used to store table dimensions
		dataTable[1]=Integer.toString(table[0].length);
		for (int i=0;i<table.length;i++){
			for (int j=0;j<table[i].length;j++){
				dataTable[(i)*table[0].length+j+2]=table[i][j].toString();
			}
		}
		return dataTable;
	}
	public void loadFrom(String[] saveData){//Loads data table from a String array
		table = new Cell[Integer.parseInt(saveData[0])][Integer.parseInt(saveData[1])];//Loads dimensions from first 2 slots in array
		String coords;
		for (int i=0;i<table.length;i++){
			for (int j=0;j<table[i].length;j++){
				coords = Character.toChars('A'+j)[0]+Integer.toString(i+1);
				setCell(coords,saveData[(i)*table[0].length+j+2]);
			}
		}
	}
	public void printTable(){	//Prints table
		headerRow(table[0].length);
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
		if (content.length()>12){	//If content is over 12 characters, shortens it
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
				table[y][x]=new StringCell(inputContent);
			}
			else if (countOccurance(inputContent,"/")==2&&(inputContent.contains(" ")==false)){//Determines if input string is a date, if so creates a Date cell
				table[y][x]=new DateCell(inputContent);
			}
			else if (inputContent.equals("<empty>")){ //If input is <empty>, an empty default cell is created
				table[y][x]=new Cell();
			}
			else if (inputContent.charAt(0)=='('){ //For use in functions
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
	public void sort(String input){	//Sorts cells in ascending or descending order
		char typeOrder = input.charAt(4); //Checks if order will be ascending or descending
		
		char collum=input.charAt(6);	//Gets first address
		String row=input.substring(7,input.indexOf(" ",7));
		int x1 = collum-'A';
		int y1 = Integer.parseInt(row)-1;
		
		collum=input.charAt(input.indexOf(" - ")+3); //Gets second address
		row=input.substring(input.indexOf(" - ")+4);
		int x2 = collum-'A';
		int y2 = Integer.parseInt(row)-1;
		
		int[][] order = new int[y2-y1+1][x2-x1+1]; //Int array used to store cell order
		Cell[][] sortArray = new Cell[y2-y1+1][x2-x1+1];	//Temporary array used to store cells while they are moved
		for (int i = y1;i<=y2;i++){	//Copies specified section of main table to the temporary array
			System.arraycopy(table[i], x1, sortArray[i-y1], 0, x2-x1+1);
		}
		int size = (y2-y1+1)*(x2-x1+1);	//Number of cells in the region
		double limit,limitLow;
		String limitString,limitStringLow;
		int limity=0;
		int limitx=0;
		int numInOrder=1;
		for (int k = 0;k<size;k++){
			limit=0;	//sets limit variables to extreme values to start off the process of determining cell order
			limitLow=999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999.0;
			limitString="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			limitStringLow="zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
			for (int i = 0;i<=y2-y1;i++){
				for (int j = 0;j<=x2-x1;j++){
					if (typeOrder == 'd') {	//If order is descending, finds max number
						if (sortArray[i][j].type.equals("double") || sortArray[i][j].type.equals("formula")) {

							if (Math.max(sortArray[i][j].getNumber(), limit) == sortArray[i][j].getNumber()) { //Finds cell with the highest value and records its data and position
								limit = sortArray[i][j].getNumber();
								limity = i;
								limitx = j;
							}
						}
					}
					if (typeOrder == 'a') {//If order is ascending, finds min number
						if (sortArray[i][j].type.equals("double") || sortArray[i][j].type.equals("formula")) {
							if (Math.min(sortArray[i][j].getNumber(), limitLow) == sortArray[i][j].getNumber()) {//Finds cell with the lowest value and records its data and position
								limitLow = sortArray[i][j].getNumber();
								limity = i;
								limitx = j;
							}
						}
					}				
				}
			}
			sortArray[limity][limitx]= new Cell();	//Takes resulting cell from this sorting cycle, & deletes it
			sortArray[limity][limitx].setContent("used");
			order[limity][limitx]=numInOrder;	//Stores the cell's position and order
			numInOrder++;
		}
		for (int i = y1;i<=y2;i++){	//Resets the temporary array to once again match the region of the excel table exactly
			System.arraycopy(table[i], x1, sortArray[i-y1], 0, x2-x1+1);
		}
		int current=1;
		for (int h=0;h<sortArray.length;h++){	//Reorders the region using the copied cells in the temporary array and the order stored in the order array
			for (int k = 0; k<sortArray[h].length;k++){
				
				for (int i = 0;i<order.length;i++){
					for (int j =0; j<order[i].length;j++){
						if (order[i][j]==current){
							table[y1+h][x1+k]=sortArray[i][j];
						}
					}
				}
				current++;
			}
		}
	}
	public static void help(String input){	//Help command, displayes various help messages for different inputs
		String shortened = "NO";
		if (input.length()<=5){
			System.out.println("\nWelcome to TextExcel!\nValid Commands\n------------------");
			System.out.println("print, quit, <cell>, clear, save, load, sorta, sortd");
			System.out.println("\nCommands are case sensitive");
			System.out.println("\nFor more in-depth information on a command, use \"help <command>\"");
		} else{
			shortened=input.substring(5);
		}
		if (shortened.equals("print")){
			System.out.println("Usage: \n\n\tprint\n\nPrints out the entire spreadsheet\n");
		}else if (shortened.equals("quit")){
			System.out.println("Usage: \n\n\tquit\n\nQuits the program. Any unsaved changes to the spreadsheet will be lost\n");
		}else if (shortened.equals("<cell>")){
			System.out.println("Usage: \n\n\t<cell>\n\nPrints out the raw content of the specified cell\n\n\t<cell> = <data>\n\nSets the content of the specified cell to the specified data\n");
		}else if (shortened.equals("clear")){
			System.out.println("Usage: \n\n\tclear\n\nClears the entire table\n\n\tclear <cell>\n\nClears the specified cell\n");
		}else if (shortened.equals("save")){
			System.out.println("Usage: \n\n\tsave <file name>\n\nSaves the table to the specified file. Default file location is the program's directory\n");
		}else if (shortened.equals("load")){
			System.out.println("Usage: \n\n\tload <file name>\n\nLoads a table from the specified file. By default, the program will search its directory for the file\n");
		}else if (shortened.equals("sorta")){
			System.out.println("Usage: \n\n\tsorta <cell1> - <cell2>\n\nSorts cells in the specified block of cells bounded by <cell1> and <cell2>, in ascending order\n");
		}else if (shortened.equals("sortd")){
			System.out.println("Usage: \n\n\tsorta <cell1> - <cell2>\n\nSorts cells in the specified block of cells bounded by <cell1> and <cell2>, in ascending order\n");
		}else if (shortened.equals("NO")){
			System.out.println();
		}else {
			System.out.println("'"+shortened+"' is not a recognized command. Use 'help' for more information on commands\n");
		}
	}
}