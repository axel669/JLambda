package axel.lambda;

public class Complex extends Num{
	public double real,imag;
	
	public static final Complex i=new Complex(0,1);
	
	public Complex(){
		this(0,0);
	}
	public Complex(double r){
		this(r,0);
	}
	public Complex(double r, double i){
		super(COMPLEX);
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
	
	public double rval(){return real;}
	public double ival(){return imag;}
	public double lensq(){return real*real+imag*imag;}
	public Num negate(){
		return new Complex(-real,-imag);
	}
	
	public Num add(Num other){
		if(other.type==REAL)
			return new Complex(real+other.val(),imag);
		return new Complex(real+other.rval(),imag+other.ival());
	}
	public Num sub(Num other){
		if(other.type==REAL)
			return new Complex(real-other.val(),imag);
		return new Complex(real-other.rval(),imag-other.ival());
	}
	public Num mul(Num other){
		if(other.type==REAL)
			return new Complex(real*other.val(),imag*other.val());
		double a=real, b=imag, c=other.rval(), d=other.ival();
		return new Complex(a*c-b*d,b*c+a*d);
	}
	public Num div(Num other){
		if(other.type==REAL)
			return new Complex(real/other.val(),imag/other.val());
		double a=real, b=imag, c=other.rval(), d=other.ival(), e=other.lensq();
		if(b*c==a*d)
			return new Real((a*c+b*d)/e);
		return new Complex((a*c+b*d)/e,(b*c-a*d)/e);
	}
	public Num mod(Num other){
		if(other.type==REAL)
			return sub(other.mul(other.inv().mul(this).floor()));
		return sub(other.mul(div(other).floor()));
	}
	public Num pow(Num other){
		Num r=abs(), angle=new Real(Math.atan2(real,imag));
		return r.pow(other).mul(Real.e.pow(Complex.i.mul(angle).mul(other)));
	}
	public Num exp(){
		return Real.e.pow(this);
	}
	
	public Num sin(LContext c){
		if(imag==0.0)
			return new Real(Util.sin(real,c.mode));
		if(real==0.0)
			return new Complex(0.0,Math.sinh(imag));
		return new Complex(Util.sin(real,c.mode)*Math.cosh(imag),Util.cos(real,c.mode)*Math.sinh(imag));
	}
	public Num cos(LContext c){
		if(imag==0.0)
			return new Real(Util.cos(real,c.mode));
		if(real==0.0)
			return new Complex(0.0,Math.cosh(imag));
		return new Complex(Util.cos(real,c.mode)*Math.cosh(imag),Util.sin(real,c.mode)*Math.sinh(imag));
	}
	public Num tan(LContext c){
		return sin(c).div(cos(c));
	}
	public Num sec(LContext c){
		return Real.one.div(cos(c));
	}
	public Num csc(LContext c){
		return Real.one.div(sin(c));
	}
	public Num cot(LContext c){
		return cos(c).div(sin(c));
	}
	public Num sinh(){
		return exp().sub(negate().exp()).div(Real.two);
	}
	public Num cosh(){
		return exp().add(negate().exp()).div(Real.two);
	}
	public Num tanh(){
		return exp().sub(negate().exp()).div(exp().add(negate().exp()));
	}
	public Num csch(){
		return Real.two.div(exp().sub(negate().exp()));
	}
	public Num sech(){
		return Real.two.div(exp().add(negate().exp()));
	}
	public Num coth(){
		return exp().add(negate().exp()).div(exp().sub(negate().exp()));
	}
	
	public Num ln(){
		return new Complex(Math.log(this.abs().val()),Math.atan2(imag,real));
	}
	public Num log(Num base){
		return ln().div(base.ln());
	}
	
	public Num re(){
		return new Real(real);
	}
	public Num im(){
		return new Real(imag);
	}
	
	public Num floor(){
		return new Complex(Math.floor(real),Math.floor(imag));
	}
	public Num ceil(){
		return new Complex(Math.ceil(real),Math.ceil(imag));
	}
	public Num round(){
		return new Complex(Math.round(real),Math.round(imag));
	}
	
	public Num abs(){
		return new Real(Math.sqrt(lensq()));
	}
	public Num sqrt(){
		return pow(new Real(0.5));
	}
	public Num root(Num base){
		return pow(base.inv());
	}
	public Num inv(){
		return Real.one.div(this);
	}
	
	public String toString(){
		return "("+real+','+imag+")";
	}
}
