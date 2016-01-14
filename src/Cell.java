public class Cell{ //Default Cell object
	public String content="";
	public String type="default";
	public Cell(){
		content="";
	}
	public double getNumber(){
		return 0;
	}
	public String toString(){
		return "<empty>";
	}
	public String getText(){
		return content;
	}
	public void setContent(String input){
		content=input;
	}
}