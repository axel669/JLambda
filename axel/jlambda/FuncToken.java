package axel.jlambda;

import java.util.HashMap;

@SuppressWarnings("serial")
public class FuncToken extends Token{
	public String name;
	public SArray args;
	public function_name fncode;
	
	/*public static enum function_name{
		EXP,
		SIN,COS,TAN,CSC,SEC,COT,
		SINH,COSH,TANH,CSCH,SECH,COTH,
		LN,LOG,
		RE,IM,
		FLOOR,CEIL,ROUND,
		ABS,SQRT,ROOT,INV,
		ASIN,ACOS,ATAN,ACSC,ASEC,ACOT,
		ASINH,ACOSH,ATANH,ACSCH,ASECH,ACOTH,
		GAMMA
	};*/
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
			put("asin",function_name.ASIN);
			put("acos",function_name.ACOS);
			put("atan",function_name.ATAN);
			put("acsc",function_name.ACSC);
			put("asec",function_name.ASEC);
			put("acot",function_name.ACOT);
			put("asinh",function_name.ASINH);
			put("acosh",function_name.ACOSH);
			put("atanh",function_name.ATANH);
			put("acsch",function_name.ACSCH);
			put("asech",function_name.ASECH);
			put("acoth",function_name.ACOTH);
			put("gamma",function_name.GAMMA);
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
			
			case SIN:
				return util.sin(args.get(0,c),c.mode);
			case COS:
				return util.cos(args.get(0,c),c.mode);
			case TAN:
				return util.tan(args.get(0,c),c.mode);
			case CSC:
				return util.csc(args.get(0,c),c.mode);
			case SEC:
				return util.sec(args.get(0,c),c.mode);
			case COT:
				return util.cot(args.get(0,c),c.mode);
			
			case SINH:
				return util.sinh(args.get(0,c));
			case COSH:
				return util.cosh(args.get(0,c));
			case TANH:
				return util.tanh(args.get(0,c));
			case CSCH:
				return util.csch(args.get(0,c));
			case SECH:
				return util.sech(args.get(0,c));
			case COTH:
				return util.coth(args.get(0,c));
			
			case ASIN:
				return util.asin(args.get(0,c),c.mode);
			case ACOS:
				return util.acos(args.get(0,c),c.mode);
			case ATAN:
				return util.atan(args.get(0,c),c.mode);
			case ACSC:
				return util.acsc(args.get(0,c),c.mode);
			case ASEC:
				return util.asec(args.get(0,c),c.mode);
			case ACOT:
				return util.acot(args.get(0,c),c.mode);
			
			case ASINH:
				return util.asinh(args.get(0,c));
			case ACOSH:
				return util.acosh(args.get(0,c));
			case ATANH:
				return util.atanh(args.get(0,c));
			case ACSCH:
				return util.acsch(args.get(0,c));
			case ASECH:
				return util.asech(args.get(0,c));
			case ACOTH:
				return util.acoth(args.get(0,c));
			
			case LN:
				return util.ln(args.get(0,c));
			case LOG:
				if(args.length()==1)
					return util.log(args.get(0,c));
				return util.log(args.get(0,c),args.get(1,c));
			
			case RE:
				return new complex(args.get(0,c).real);
			case IM:
				return new complex(args.get(0,c).imag);
			
			case FLOOR:
				return util.floor(args.get(0,c));
			case CEIL:
				return util.ceil(args.get(0,c));
			case ROUND:
				return util.round(args.get(0,c));
			
			case ABS:
				return args.get(0,c).abs();
			case SQRT:
				return args.get(0,c).pow(complex.half);
			case ROOT:
				return args.get(0,c).pow(args.get(1,c).inv());
			case INV:
				return args.get(0,c).inv();
			
			case GAMMA:
				return util.gamma(args.get(0,c));
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
		r+=util.space(tabs)+"Func: "+this.name;
		for(int x=0;x<args.length();x++)
			r+="\n"+args.get(x).toString(tabs+1);
		return r;
	}
}
