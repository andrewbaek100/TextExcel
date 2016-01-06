//By Ryan Chan
//AP Computer Science 2016
public class StringCell extends Cell{ //Cell of type String
	public String type="string";
	public StringCell(){
		content="";
	}
	public StringCell(String input){
		content=input;
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
