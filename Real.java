package axel.lambda;

public class Real extends Num{
	public static final Real zero=new Real();
	public static final Real one=new Real(1);
	public static final Real two=new Real(2);
	public static final Real e=new Real(Math.E);
	public static final Real pi=new Real(Math.PI);
	public static final Real ten=new Real(10);
	public static final Real the_answer=new Real(42);
	
	public double value;
	
	
	public Real(){
		this(0);
	}
	public Real(double val){
		super(REAL);
		value=val;
	}
	
	public String format(){
		return formatSmall(formatter.format(value));
	}
	
	public double val(){return value;}
	public Num negate(){return new Real(-value);}
	
	public Num add(Num other){
		if(other.type==REAL)
			return new Real(value+other.val());
		return new Complex(value+other.rval(),other.ival());
	}
	public Num sub(Num other){
		if(other.type==REAL)
			return new Real(value-other.val());
		return new Complex(value-other.rval(),-other.ival());
	}
	public Num mul(Num other){
		if(other.type==REAL)
			return new Real(value*other.val());
		return new Complex(value*other.rval(),value*other.ival());
	}
	public Num div(Num other){
		if(other.type==REAL)
			return new Real(value/other.val());
		double e=other.lensq();
		return new Complex(value*other.rval()/e,-value*other.ival()/e);
	}
	public Num mod(Num other){
		if(other.type==REAL)
			return new Real(value%other.val());
		return add(other.mul(negate().div(other).ceil()));
	}
	public Num pow(Num other){
		if(other.type==REAL)
		{
			if(value<0 && Math.abs(other.val())<1)
				return (new Real(Math.pow(Math.abs(value),other.val()))).mul(e.pow(new Complex(0,Math.PI*other.val())));
			return new Real(Math.pow(value,other.val()));
		}
		return new Complex(
				Math.pow(value,other.rval())*Util.cos(other.ival()*Math.log(value),Lambda.Mode.radians),
				Math.pow(value,other.rval())*Util.sin(other.ival()*Math.log(value),Lambda.Mode.radians)
			);
	}
	public Num fac(){
		return new Real(Util.factorial(value));
	}
	public Num exp(){
		return new Real(Math.exp(value));
	}
	
	public Num sin(LContext c){
		return new Real(Util.sin(value,c.mode));
	}
	public Num cos(LContext c){
		return new Real(Util.cos(value,c.mode));
	}
	public Num tan(LContext c){
		return new Real(Util.tan(value,c.mode));
	}
	public Num csc(LContext c){
		return new Real(1/Util.sin(value,c.mode));
	}
	public Num sec(LContext c){
		return new Real(1/Util.cos(value,c.mode));
	}
	public Num cot(LContext c){
		return new Real(1/Util.tan(value,c.mode));
	}
	public Num sinh(){
		return new Real((Math.exp(value)-Math.exp(-value))/2);
	}
	public Num cosh(){
		return new Real((Math.exp(value)+Math.exp(-value))/2);
	}
	public Num tanh(){
		return new Real((Math.exp(value)-Math.exp(-value))/(Math.exp(value)+Math.exp(-value)));
	}
	public Num csch(){
		return new Real(2/(Math.exp(value)-Math.exp(-value)));
	}
	public Num sech(){
		return new Real(2/(Math.exp(value)+Math.exp(-value)));
	}
	public Num coth(){
		return new Real((Math.exp(value)+Math.exp(-value))/(Math.exp(value)-Math.exp(-value)));
	}
	
	public Num ln(){
		return new Real(Math.log(value));
	}
	public Num log(Num base){
		return new Real(Math.log(value)/Math.log(base.val()));
	}
	
	public Num re(){
		return this;
	}
	public Num im(){
		return Real.zero;
	}
	
	public Num floor(){
		return new Real(Math.floor(value));
	}
	public Num ceil(){
		return new Real(Math.ceil(value));
	}
	public Num round(){
		return new Real(Math.round(value));
	}
	
	public Num abs(){
		return new Real(Math.abs(value));
	}
	public Num sqrt(){
		if(value>=0)
			return new Real(Math.sqrt(value));
		return pow(new Real(0.5));
	}
	public Num root(Num base){
		return pow(base.inv());
	}
	public Num inv(){
		return new Real(1/value);
	}
	
	public String toString(){
		return "("+value+")";
	}
}
