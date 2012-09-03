package axel.jlambda;

import java.util.*;

@SuppressWarnings({"serial","unchecked"})
public class LContext {
	public int mode;
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
		this(util.degrees);
	}
	public LContext(int m)
	{
		mode=m;
		variables=(HashMap<String,Token>)default_vars.clone();
	}
	public LContext(int m, LContext base)
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
