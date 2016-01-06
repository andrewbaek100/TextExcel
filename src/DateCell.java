//By Ryan Chan
//AP Computer Science 2016
import java.util.*;
import java.text.*;
public class DateCell extends Cell{//Cell of type Date
	public String type = "date";
	public Date content = new Date();
	public String originalDate="";
	public SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	public DateCell(String input){
		originalDate=input;
		try {
			content=format.parse(input);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Invalid date format");
		}
	}
	public Date getContent(){
		return content;
	}
	public String toString(){
		return originalDate;
	}
	public String getText(){
		return format.format(content);
	}
	public void setContent(String input){
		try {
			content=format.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Invalid date format");
		}
	}
}
