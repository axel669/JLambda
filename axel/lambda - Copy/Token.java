package axel.lambda;

public class Token {
	enum Type{number,operator,variable,function};
	
	public Type type;
	public Token parent;
	public boolean parens;
	
	public Token(Type t){
		parens=false;
		type=t;
		parent=null;
	}
	
	public boolean full(){return true;}
	public void push(Token t){}
	public Token pop(){return null;}
	public int prec(){return -1;}
	public complex eval(LContext c){return null;}
	
	public String toString(){
		return "Token";
	}
	public String toString(int tabs){
		return "Token";
	}
}
