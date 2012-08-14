package axel.lambda;

import java.util.HashMap;

@SuppressWarnings("serial")
public class OpToken extends Token{
	public String operator;
	public SArray<Token> operands;
	public int p;
	
	public static final HashMap<String,Integer> op_num=new HashMap<String,Integer>(){
		{
			put("+",0);
			put("-",1);
			put("*",2);
			put("/",3);
			put("%",4);
			put("^",5);
			put("!",6);
		}
	};
	public static final HashMap<String,Integer> precedence=new HashMap<String,Integer>(){
		{
			put("+",0);
			put("-",0);
			put("*",1);
			put("/",1);
			put("%",1);
			put("^",2);
			put("!",3);
		}
	};
	
	public OpToken(String op){
		super(Type.operator);
		operator=op;
		operands=new SArray<Token>();
		p=precedence.get(operator);
	}
	
	public int prec(){return p;}
	
	public Num eval(LContext c){
		switch(op_num.get(operator))
		{
			case 0: return operands.get(0).eval(c).add(operands.get(1).eval(c));
			case 1: return operands.get(0).eval(c).sub(operands.get(1).eval(c));
			case 2: return operands.get(0).eval(c).mul(operands.get(1).eval(c));
			case 3: return operands.get(0).eval(c).div(operands.get(1).eval(c));
			case 4: return operands.get(0).eval(c).mod(operands.get(1).eval(c));
			case 5: return operands.get(0).eval(c).pow(operands.get(1).eval(c));
			case 6: return operands.get(0).eval(c).fac();
		}
		return null;
	}
	
	public void push(Token t){
		t.parent=this;
		operands.push(t);
	}
	public Token pop(){
		return operands.pop();
	}
	
	public boolean full(){
		return operands.length()==(operator=="!"?1:2);
	}
	
	public String toString(){
		return this.toString(0);
	}
	public String toString(int tabs){
		String r="";
		r+=Util.space(tabs)+"Op: "+operator;
		for(int x=0;x<operands.length();x++)
			r+="\n"+operands.get(x).toString(tabs+1);
		return r;
	}
}
