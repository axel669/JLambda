package axel.lambda;

public class NumToken extends Token{
	public complex value;
	
	public NumToken(complex val){
		super(Type.number);
		value=val;
	}
	
	public complex eval(LContext c){
		return value;
	}
	
	public String toString(){
		return "Num: "+value;
	}
	public String toString(int tabs){
		return util.space(tabs)+this.toString();
	}
}
