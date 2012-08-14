package axel.lambda;

public class NumToken extends Token{
	public Num value;
	
	public NumToken(Num val){
		super(Type.number);
		value=val;
	}
	
	public Num eval(LContext c){
		return value;
	}
	
	public String toString(){
		return "Num: "+value;
	}
	public String toString(int tabs){
		return Util.space(tabs)+this.toString();
	}
}
