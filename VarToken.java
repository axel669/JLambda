package axel.lambda;

public class VarToken extends Token{
	public String name;
	
	public VarToken(String name){
		super(Type.variable);
		this.name=name;
	}
	
	public Num eval(LContext c){
		return c.variables.get(name).eval(c);
	}
	
	public String toString(){
		return toString(0);
	}
	public String toString(int tabs){
		return Util.space(tabs)+"Var: "+name;
	}
}
