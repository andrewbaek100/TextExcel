//Written by Ryan Chan
//AP Computer Science 2016
import java.util.*;

import persistence.PersistenceHelper;
public class TextExcel {
	public static int TABLE_X_SIZE=10;	//Class constants for table size
	public static int TABLE_Y_SIZE=10;
	public static boolean running=true;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Table grid=new Table(TABLE_X_SIZE,TABLE_Y_SIZE);	//Initialize table object
		System.out.println("Welcome to TextExcel, by Ryan Chan");
		
		while (running){	//Gets user input
			System.out.print("Enter a command: ");
			parseInput(sc.nextLine(),grid);
		}
	}
	public static void parseInput(String input,Table grid){	//Acts on the user input
		char number='.';
		char firstLetter='.';
		if (input.length()>1){ //Takes the first 2 characters of the string
			firstLetter = input.charAt(0);
			number = input.charAt(1);
		}
		if (input.length()<5){
			for(int i = input.length();i<5;i++){
				input+=" ";
			}
		}
		if(input.substring(0,5).equals(("print"))){ //Prints table
			grid.printTable();
		} else if(input.substring(0,4).equals(("quit"))){ //Quits program
			System.out.println("Thank you for using TextExcel!");
			running=false;
		} else if(input.contains(" = ")){ //Setting cells
			grid.setCell(input.substring(0,input.indexOf(" = ")), input.substring(input.indexOf(" = ")+3));
		} else if('A'<=firstLetter&&firstLetter<='Z'&&'0'<=number&&number<='9'&&input.indexOf("=")==-1){ //Displaying cells
			System.out.println(input.substring(0,input.indexOf(" "))+"= "+grid.outputCell(input.substring(0,input.indexOf(" "))));
		} else if(input.substring(0,5).equals("clear")){ //Clearing cells
			if (input.contains(" ")){	//If a cell is specified, clears that cell
				grid.clearCell(input.substring(6));
			} else {	//If no cell is specified, clears the entire table
				grid.clear();
			}
		} else if (input.substring(0,4).equals("save")){	//Saving the table
			try {
				PersistenceHelper.save(input.substring(5), grid);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error while saving "+input.substring(5));
			}
		} else if (input.substring(0,4).equals("load")){	//Loading the table
			try {
				PersistenceHelper.load(input.substring(5), grid);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error while loading "+input.substring(5));
			}
		} else if (input.substring(0,4).equals("sort")){	//sorting the table
			grid.sort(input);
		} else if (input.substring(0,4).equals("help")){	//Help menu
			Table.help(input);
		}
		else {
			System.out.println("Command not recognized");
		}
	}
}