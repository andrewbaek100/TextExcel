//By Ryan Chan
//AP Computer Science 2016
public class StringCell extends Cell{ //Cell of type String
	public String type="string";
	public StringCell(String input){
		type="string";
		content=input;
	}
	public StringCell(){
		this("");
	}
	public String getContent(){
		return content;
	}
	public String toString(){
		return content;
	}
	public String getText(){
		return content.substring(1,content.length()-1);
	}
	public void setContent(String input){
		content=input;
	}
}
