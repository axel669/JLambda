package axel.lambda;

import java.util.HashMap;

@SuppressWarnings("serial")
public class FuncToken extends Token{
	public String name;
	public SArray args;
	public function_name fncode;
	
	public enum function_name{
		EXP,SIN,COS,TAN,CSC,SEC,COT,SINH,COSH,TANH,
		CSCH,SECH,COTH,LN,LOG,RE,IM,FLOOR,CEIL,ROUND,ABS,SQRT,ROOT,INV
	};
	public static HashMap<String,function_name> func_code=new HashMap<String,function_name>(){
		{
			put("exp",function_name.EXP);
			put("sin",function_name.SIN);
			put("cos",function_name.COS);
			put("tan",function_name.TAN);
			put("csc",function_name.CSC);
			put("sec",function_name.SEC);
			put("cot",function_name.COT);
			put("sinh",function_name.SINH);
			put("cosh",function_name.COSH);
			put("tanh",function_name.TANH);
			put("csch",function_name.CSCH);
			put("sech",function_name.SECH);
			put("coth",function_name.COTH);
			put("ln",function_name.LN);
			put("log",function_name.LOG);
			put("re",function_name.RE);
			put("im",function_name.IM);
			put("floor",function_name.FLOOR);
			put("ceil",function_name.CEIL);
			put("round",function_name.ROUND);
			put("abs",function_name.ABS);
			put("sqrt",function_name.SQRT);
			put("root",function_name.ROOT);
			put("inv",function_name.INV);
		}
	};
	
	public FuncToken(String name){
		super(Type.function);
		this.name=name;
		args=new SArray();
		fncode=func_code.get(name);
	}
	
	public complex eval(LContext c){
		switch(fncode)
		{
			case EXP:
				return util.exp(args.get(0,c));
		}
		return null;
	}
	
	public void push(Token t){
		t.parent=this;
		args.push(t);
	}
	
	public String toString(){
		return toString(0);
	}
	public String toString(int tabs){
		String r="";
		r+=Util_old.space(tabs)+"Func: "+this.name;
		for(int x=0;x<args.length();x++)
			r+="\n"+args.get(x).toString(tabs+1);
		return r;
	}
}
