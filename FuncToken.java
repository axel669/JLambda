package axel.lambda;

import java.util.HashMap;

@SuppressWarnings("serial")
public class FuncToken extends Token{
	public String name;
	public SArray<Token> args;
	
	public static HashMap<String,Function> builtin=new HashMap<String,Function>(){
		{
			put("exp",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).exp();
				}
			});
			put("sin",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).sin(c);
				}
			});
			put("cos",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).cos(c);
				}
			});
			put("tan",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).tan(c);
				}
			});
			put("sec",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).sec(c);
				}
			});
			put("csc",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).csc(c);
				}
			});
			put("cot",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).cot(c);
				}
			});
			
			put("sinh",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).sinh();
				}
			});
			put("cosh",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).cosh();
				}
			});
			put("tanh",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).tanh();
				}
			});
			put("ln",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).ln();
				}
			});
			put("log",new Function(){
				public Num call(SArray<Token> args, LContext c){
					if(args.length()==1)
						return args.get(0).eval(c).log(Real.ten);
					return args.get(0).eval(c).log(args.get(1).eval(c));
				}
			});
			put("re",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).re();
				}
			});
			put("im",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).im();
				}
			});
			put("floor",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).floor();
				}
			});
			put("ceil",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).ceil();
				}
			});
			put("round",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).round();
				}
			});
			put("abs",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).abs();
				}
			});
			put("sqrt",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).sqrt();
				}
			});
			put("root",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).root(args.get(1).eval(c));
				}
			});
			put("inv",new Function(){
				public Num call(SArray<Token> args, LContext c){
					return args.get(0).eval(c).inv();
				}
			});
		}
	};
	
	public FuncToken(String name){
		super(Type.function);
		this.name=name;
		args=new SArray<Token>();
	}
	
	public Num eval(LContext c){
		return builtin.get(name).call(args,c);
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
		r+=Util.space(tabs)+"Func: "+this.name;
		for(int x=0;x<args.length();x++)
			r+="\n"+args.get(x).toString(tabs+1);
		return r;
	}
}
