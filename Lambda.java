package axel.lambda;

import java.util.regex.*;
import java.util.*;

public class Lambda {
	enum Mode{degrees,radians,gradians};
	
	public static Pattern numregex=Pattern.compile("\\d{0,}(\\.\\d+){0,}(E(\\-|\\+){0,1}\\d+){0,}");
	public static Pattern wordregex=Pattern.compile("[a-z_]{1}((_\\d+)|[a-z_])*");
	
	public static Pattern numtest=Pattern.compile("[\\d\\.]");
	public static Pattern optest=Pattern.compile("[\\+\\-\\/\\*\\!\\^\\%\\=]");
	public static Pattern wordtest=Pattern.compile("[a-zA-Z_]");
	
	public static void parseToken(Matcher regex, Info info){
		regex.find(info.i);
		info.s=info.eq.substring(regex.start(),regex.end());
		info.i+=info.s.length();
	}
	
	public static String[] getFunctions(){
		String[] r=new String[FuncToken.builtin.size()];
		Object[] keys=FuncToken.builtin.keySet().toArray();
		for(int x=0;x<keys.length;x++)
			r[x]=keys[x]+"";
		Arrays.sort(r);
		return r;
	}
	
	public static Token parse(String eq){
		deque tokens=tokenize(eq.replaceAll("\\s",""));
		return treeinate(tokens);
	}
	
	public static Num eval(String eq){
		return eval(eq,new LContext());
	}
	public static Num eval(String eq, LContext c){
		return parse(eq).eval(c);
	}
	
	public static void parseParens(Info info, Token base){
		info.i++;
		boolean end=false;
		while(info.i<info.eq.length() && !end)
		{
			int s=info.i, pcount=1;
			for(;info.i<info.eq.length();info.i++)
			{
				char c=info.eq.charAt(info.i);
				if(c==',' && pcount==1)
					break;
				else if(c=='(')
					pcount++;
				else if(c==')')
				{
					if(pcount==1)
					{
						end=true;
						break;
					}
					else
						pcount--;
				}
			}
			Token tree=parse(info.eq.substring(s,info.i));
			base.push(tree);
			info.i++;
		}
	}
	
	public static deque tokenize(String eq){
		deque r=new deque();
		Info info=new Info(eq);
		Matcher numparse=numregex.matcher(eq), wordparse=wordregex.matcher(eq);
		Token token;
		while(info.i<eq.length())
		{
			String eqtest=eq.substring(info.i,info.i+1);
			if(numtest.matcher(eqtest).matches())
			{
				parseToken(numparse,info);
				token=new NumToken(Util.getNum(info.s));
			}
			else if(optest.matcher(eqtest).matches())
			{
				info.i++;
				token=new OpToken(eqtest);
			}
			else if(wordtest.matcher(eqtest).matches())
			{
				parseToken(wordparse,info);
				if(FuncToken.builtin.containsKey(info.s))
				{
					token=new FuncToken(info.s);
					parseParens(info,token);
				}
				else
					token=new VarToken(info.s);
			}
			else
			{
				token=new ParenToken();
				parseParens(info,token);
			}
			r.push(token);
		}
		return r;
	}
	
	public static Token treeinate(deque tokens){
		Token r=tokens.popf();
		while(tokens.size()>0)
		{
			Token token=tokens.popf();
			if(token.type==Token.Type.operator)
			{
				while(r.parent!=null)
				{
					if(r.type==Token.Type.operator && r.prec()<token.prec())
						break;
					r=r.parent;
				}
				if(r.prec()!=-1 && r.prec()<token.prec())
				{
					token.push(r.pop());
					r.push(token);
				}
				else if(r.parent==null)
					token.push(r);
				else
				{
					Token p=r.parent;
					token.push(p.pop());
					p.push(token);
				}
				r=token;
			}
			else
			{
				if(r.type==Token.Type.operator && !r.full())
				{
					if(((OpToken)r).operator.charAt(0)=='-' && ((OpToken)r).operands.length()==0)
						r.push(new NumToken(Real.zero));
					r.push(token);
				}
				else
				{
					tokens.pushf(token);
					tokens.pushf(new OpToken("*"));
				}
			}
		}
		while(r.parent!=null)
			r=r.parent;
		return r;
	}
}
