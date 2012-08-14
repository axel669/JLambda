package axel.lambda;

import java.text.*;

public class Num {
	public static final int REAL=0;
	public static final int COMPLEX=1;
	protected static final DecimalFormat formatter=new DecimalFormat("0.##############E0");
	protected static final DecimalFormat shortForm=new DecimalFormat("0.##############");
	
	public int type;
	
	public Num(int type){
		this.type=type;
	}
	
	public String format(){return "";}
	protected String formatSmall(String s){
		String[] parts=s.split("E");
		if(parts[1].compareTo("0")==0)
			return parts[0];
		double exp=Double.parseDouble(parts[1]);
		if(exp>=-4 && exp<7)
			return shortForm.format(Double.parseDouble(parts[0])*Math.pow(10,exp));
		return parts[0]+"E"+parts[1];
	}
	
	public double val(){return 0.0;}
	public double rval(){return 0.0;}
	public double ival(){return 0.0;}
	public double lensq(){return 0.0;}
	public Num negate(){return null;}
	
	public Num add(Num other){return null;}
	public Num sub(Num other){return null;}
	public Num mul(Num other){return null;}
	public Num div(Num other){return null;}
	public Num mod(Num other){return null;}
	public Num pow(Num other){return null;}
	public Num fac(){return null;}
	public Num exp(){return null;}
		
	public Num sin(LContext c){return null;}
	public Num cos(LContext c){return null;}
	public Num tan(LContext c){return null;}
	public Num sec(LContext c){return null;}
	public Num csc(LContext c){return null;}
	public Num cot(LContext c){return null;}
	public Num sinh(){return null;}
	public Num cosh(){return null;}
	public Num tanh(){return null;}
	public Num sech(){return null;}
	public Num csch(){return null;}
	public Num coth(){return null;}
	
	public Num asin(LContext c){return null;}
	public Num acos(LContext c){return null;}
	public Num atan(LContext c){return null;}
	public Num asec(LContext c){return null;}
	public Num acsc(LContext c){return null;}
	public Num acot(LContext c){return null;}
	
	public Num ln(){return null;}
	public Num log(Num base){return null;}
	
	public Num re(){return null;}
	public Num im(){return null;}
	
	public Num floor(){return null;}
	public Num ceil(){return null;}
	public Num round(){return null;}
	
	public Num abs(){return null;}
	public Num sqrt(){return null;}
	public Num root(Num base){return null;}
	public Num inv(){return null;}
}
