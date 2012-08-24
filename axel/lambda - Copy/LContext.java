package axel.lambda;

import java.util.*;

@SuppressWarnings({"serial","unchecked"})
public class LContext {
	public Lambda.Mode mode;
	public HashMap<String,Token> variables;
	
	public static HashMap<String,Token> default_vars=new HashMap<String,Token>(){
		{
			put("pi",new NumToken(complex.pi));
			put("e",new NumToken(complex.e));
			put("the_answer",new NumToken(complex.the_answer));
			put("i",new NumToken(complex.i));
		}
	};
	
	public LContext()
	{
		this(Lambda.Mode.degrees);
	}
	public LContext(Lambda.Mode m)
	{
		mode=m;
		variables=(HashMap<String,Token>)default_vars.clone();
	}
	public LContext(Lambda.Mode m, LContext base)
	{
		mode=m;
		variables=(HashMap<String,Token>)base.variables.clone();
	}
	
	public String[] getVars(){
		String[] r=new String[variables.size()];
		Object[] keys=variables.keySet().toArray();
		for(int x=0;x<keys.length;x++)
			r[x]=keys[x]+"";
		Arrays.sort(r);
		return r;
	}
}
