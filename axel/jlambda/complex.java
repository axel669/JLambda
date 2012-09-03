package axel.jlambda;

import java.text.DecimalFormat;

public class complex{
	protected static final DecimalFormat formatter=new DecimalFormat("0.##############E0");
	protected static final DecimalFormat shortForm=new DecimalFormat("0.##############");
	
	protected String formatSmall(String s){
		String[] parts=s.split("E");
		if(parts[1].compareTo("0")==0)
			return parts[0];
		double exp=Double.parseDouble(parts[1]);
		if(exp>=-4 && exp<7)
			return shortForm.format(Double.parseDouble(parts[0])*Math.pow(10,exp));
		return parts[0]+"E"+parts[1];
	}
	public String format(){
		String r="";
		if(real==0.0 && imag==0.0)
			return "0";
		if(real!=0.0)
		{
			if(real==Double.POSITIVE_INFINITY)
				r+="Infinity";
			else if(real==Double.NEGATIVE_INFINITY)
				r+="-Infinity";
			else
				r+=formatSmall(formatter.format(real));
		}
		if(imag!=0.0)
		{
			if(imag>0 && real!=0.0)
				r+="+";
			if(imag!=1.0)
			{
				if(real==Double.POSITIVE_INFINITY)
					r+="Infinity*";
				else if(real==Double.NEGATIVE_INFINITY)
					r+="-Infinity*";
				else
					r+=formatSmall(formatter.format(imag));
			}
			r+="i";
		}
		return r;
	}
	
	
	public double real, imag;
	
	public static final complex i=new complex(0,1);
	public static final complex zero=new complex();
	public static final complex half=new complex(.5);
	public static final complex one=new complex(1);
	public static final complex two=new complex(2);
	public static final complex e=new complex(util.e);
	public static final complex pi=new complex(Math.PI);
	public static final complex pi_2=new complex(util.pi_2);
	public static final complex ten=new complex(10);
	public static final complex the_answer=new complex(42);
	public static final complex ipi=new complex(0,util.pi);
	
	public complex(){
		this(0,0);
	}
	public complex(double r){
		this(r,0);
	}
	public complex(double r, double i){
		real=r;
		imag=i;
	}
	
	public double lensq(){
		return real*real+imag*imag;
	}
	
	public complex add(complex y){
		return new complex(real+y.real,imag+y.imag);
	}
	public complex sub(complex y){
		return new complex(real-y.real,imag-y.imag);
	}
	public complex mul(complex y){
		if(imag==0)
		{
			if(y.imag==0)
				return new complex(real*y.real);
			return new complex(real*y.real,real*y.imag);
		}
		return new complex(real*y.real-imag*y.imag,imag*y.real+real*y.imag);
	}
	public complex div(complex y){
		if(y.imag==0)
			return new complex(real/y.real,imag/y.real);
		double z=y.lensq();
		return new complex((real*y.real+imag*y.imag)/z,(imag*y.real-real*y.imag)/z);
	}
	public complex mod(complex y){
		return this.sub(y.mul(util.floor(div(y))));
	}
	public complex pow(complex y){
		if(imag==0)
		{
			if(y.imag==0)
			{
				if(real<0 && Math.abs(y.real)<1)
					return abs().pow(y).mul(e.pow(ipi.mul(y)));
				return new complex(Math.pow(real,y.real));
			}
			return new complex(
				Math.pow(real,y.real)*util.cos(y.imag*util.ln(real),1),
				Math.pow(real,y.real)*util.sin(y.imag*util.ln(real),1)
			);
		}
		complex r=abs(), angle=new complex(0,Math.atan2(imag,real));
		return r.pow(y).mul(e.pow(angle.mul(y)));
	}
	public complex fac(){
		if(imag==0 && real>=0 && real<=170 && Math.round(real)==real)
			return new complex(util.factorial(real));
		return util.gamma(new complex(real+1,imag));
	}
	
	public complex negate(){
		return new complex(-real,-imag);
	}
	public complex abs(){
		return new complex(Math.sqrt(lensq()));
	}
	public complex inv(){
		return one.div(this);
	}
	
	public complex to_deg(){
		return new complex(Math.toDegrees(real),Math.toDegrees(imag));
	}
	public complex to_rad(){
		return new complex(Math.toRadians(real),Math.toRadians(imag));
	}
	
	public String toString(){
		return "("+real+','+imag+")";
	}
}
