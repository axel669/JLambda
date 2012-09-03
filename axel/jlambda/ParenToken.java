package axel.jlambda;

public class ParenToken extends Token{
	public Token arg;
	
	public ParenToken(){
		super(Type.number);
	}
	
	public complex eval(LContext c){
		return arg.eval(c);
	}
	
	public void push(Token t){
		t.parent=this;
		arg=t;
	}
	
	public String toString(){
		return toString(0);
	}
	public String toString(int tabs){
		String t=util.space(tabs);
		return t+"Paren:\n"+arg.toString(tabs+1);
	}
}
