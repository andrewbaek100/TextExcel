//By Ryan Chan
//AP Computer Science 2016
public class DoubleCell extends Cell {//Cell of type double
	public double content=0;
	public DoubleCell(double input){
		type = "double";
		content = input;
	}
	public DoubleCell(int input){
		this((double)input);
	}
	public double getNumber(){
		return content;
	}
	public double getContent(){
		return content;
	}
	public String toString(){
		return Double.toString(content);
	}
	public String getText(){
		return Double.toString(content);
	}
	public void setContent(double input){
		content=input;
	}
	public void setContent(int input){
		content=(double)input;
	}
}
