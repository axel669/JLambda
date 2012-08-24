package axel.lambda;

import java.text.DecimalFormat;

public class Complex_old{
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
	
	public double real,imag;
	
	public static final Complex_old i=new Complex_old(0,1);
	public static final Complex_old zero=new Complex_old();
	public static final Complex_old half=new Complex_old(.5);
	public static final Complex_old one=new Complex_old(1);
	public static final Complex_old two=new Complex_old(2);
	public static final Complex_old e=new Complex_old(Math.E);
	public static final Complex_old pi=new Complex_old(Math.PI);
	public static final Complex_old ten=new Complex_old(10);
	public static final Complex_old the_answer=new Complex_old(42);
	public static final Complex_old ipi=new Complex_old(0,Math.PI);
	
	public Complex_old(){
		this(0,0);
	}
	public Complex_old(double r){
		this(r,0);
	}
	public Complex_old(double r, double i){
		real=r;
		imag=i;
	}
	
	public String format(){
		String r="";
		if(real==0.0 && imag==0.0)
			return "0";
		if(real!=0.0)
			r+=formatSmall(formatter.format(real));
		if(imag!=0.0)
		{
			if(imag>0 && real!=0.0)
				r+="+";
			if(imag!=1.0)
				r+=formatSmall(formatter.format(imag));
			r+="i";
		}
		return r;
	}
	
	public double lensq(){return real*real+imag*imag;}
	public Complex_old negate(){
		return new Complex_old(-real,-imag);
	}
	
	public Complex_old add(Complex_old other){
		return new Complex_old(real+other.real,imag+other.imag);
	}
	public Complex_old sub(Complex_old other){
		return new Complex_old(real-other.real,imag-other.imag);
	}
	public Complex_old mul(Complex_old other){
		double a=real, b=imag, c=other.real, d=other.imag;
		return new Complex_old(a*c-b*d,b*c+a*d);
	}
	public Complex_old div(Complex_old other){
		double a=real, b=imag, c=other.real, d=other.imag, e=other.lensq();
		if(b*c==a*d)
			return new Complex_old((a*c+b*d)/e);
		return new Complex_old((a*c+b*d)/e,(b*c-a*d)/e);
	}
	public Complex_old mod(Complex_old other){
		return sub(other.mul(div(other).floor()));
	}
	public Complex_old pow(Complex_old other){
		if(imag==0)
		{
			if(other.imag==0)
			{
				if(this.real<0 && Math.abs(other.real)<1)
					return this.abs().pow(other).mul(e.pow(ipi.mul(other)));
				return new Complex_old(Math.pow(real,other.real));
			}
			return new Complex_old(
				Math.pow(real,other.real)*Util_old.cos(other.imag*Util_old.ln(real),Lambda.Mode.radians),
				Math.pow(real,other.real)*Util_old.sin(other.imag*Util_old.ln(real),Lambda.Mode.radians)
			);
		}
		Complex_old r=abs(), angle=new Complex_old(0,Math.atan2(real,imag));
		return r.pow(other).mul(Complex_old.e.pow(angle.mul(other)));
	}
	public Complex_old fac(){
		return null;
	}
	
	public Complex_old exp(){
		return Complex_old.e.pow(this);
	}
	
	public Complex_old sin(LContext c){
		if(imag==0.0)
			return new Complex_old(Util_old.sin(real,c.mode));
		if(real==0.0)
			return new Complex_old(0.0,Math.sinh(imag));
		return new Complex_old(Util_old.sin(real,c.mode)*Math.cosh(imag),Util_old.cos(real,c.mode)*Math.sinh(imag));
	}
	public Complex_old cos(LContext c){
		if(imag==0.0)
			return new Complex_old(Util_old.cos(real,c.mode));
		if(real==0.0)
			return new Complex_old(0.0,Math.cosh(imag));
		return new Complex_old(Util_old.cos(real,c.mode)*Math.cosh(imag),Util_old.sin(real,c.mode)*Math.sinh(imag));
	}
	public Complex_old tan(LContext c){
		return sin(c).div(cos(c));
	}
	public Complex_old sec(LContext c){
		return one.div(cos(c));
	}
	public Complex_old csc(LContext c){
		return one.div(sin(c));
	}
	public Complex_old cot(LContext c){
		return cos(c).div(sin(c));
	}
	
	public Complex_old sinh(){
		return exp().sub(negate().exp()).div(two);
	}
	public Complex_old cosh(){
		return exp().add(negate().exp()).div(two);
	}
	public Complex_old tanh(){
		return exp().sub(negate().exp()).div(exp().add(negate().exp()));
	}
	public Complex_old csch(){
		return Complex_old.two.div(exp().sub(negate().exp()));
	}
	public Complex_old sech(){
		return Complex_old.two.div(exp().add(negate().exp()));
	}
	public Complex_old coth(){
		return exp().add(negate().exp()).div(exp().sub(negate().exp()));
	}
	
	public Complex_old asin(Lambda.Mode mode){
		if(imag==0)
			return new Complex_old(Util_old.asin(real,mode));
		Complex_old r=i.negate().mul(
			i.mul(this).add(
				one.sub(
					this.pow(two)
				).sqrt()
			).ln()
		);
		return mode==Lambda.Mode.degrees?r.to_deg():r;
	}
	public Complex_old acos(Lambda.Mode mode){
		if(imag==0)
			return new Complex_old(Util_old.acos(real,mode));
		Complex_old r=i.mul(
				i.mul(this).add(
					one.sub(
						this.pow(two)
					).sqrt()
				).ln()
			).add(new Complex_old(Util_old.PI_2));
		return mode==Lambda.Mode.degrees?r.to_deg():r;
	}
	
	public Complex_old ln(){
		return new Complex_old(Util_old.ln(this.abs().real),Math.atan2(imag,real));
	}
	public Complex_old log(Complex_old base){
		return ln().div(base.ln());
	}
	
	public Complex_old re(){
		return new Complex_old(real);
	}
	public Complex_old im(){
		return new Complex_old(imag);
	}
	
	public Complex_old floor(){
		return new Complex_old(Math.floor(real),Math.floor(imag));
	}
	public Complex_old ceil(){
		return new Complex_old(Math.ceil(real),Math.ceil(imag));
	}
	public Complex_old round(){
		return new Complex_old(Math.round(real),Math.round(imag));
	}
	
	public Complex_old abs(){
		return new Complex_old(Math.sqrt(lensq()));
	}
	public Complex_old sqrt(){
		return pow(half);
	}
	public Complex_old root(Complex_old base){
		return pow(base.inv());
	}
	public Complex_old inv(){
		return one.div(this);
	}
	
	public Complex_old to_deg(){
		return new Complex_old(Math.toDegrees(real),Math.toDegrees(imag));
	}
	public Complex_old to_rad(){
		return new Complex_old(Math.toRadians(real),Math.toRadians(imag));
	}
	
	public String toString(){
		return "("+real+','+imag+")";
	}
}
