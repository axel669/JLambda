package axel.jlambda;

import java.util.regex.*;
import java.util.*;

public class Lambda {
	public static Pattern numregex=Pattern.compile("\\d{0,}(\\.\\d+){0,}(E(\\-|\\+){0,1}\\d+){0,}");
	public static Pattern wordregex=Pattern.compile("[a-z_]{1}((_\\d+)|[a-z_])*");
	
	public static Pattern numtest=Pattern.compile("[\\d\\.]");
	public static Pattern optest=Pattern.compile("[\\+\\-\\/\\*\\!\\^\\%\\=]");
	public static Pattern wordtest=Pattern.compile("[a-zA-Z_]");
	
	public static Pattern rpn_number=Pattern.compile("\\-{0,1}\\d{0,}(\\.\\d+){0,}(E(\\-|\\+){0,1}\\d+){0,}i{0,1}");
	
	public static void parseToken(Matcher regex, Info info){
		regex.find(info.i);
		info.s=info.eq.substring(regex.start(),regex.end());
		info.i+=info.s.length();
	}
	
	public static String[] getFunctions(){
		String[] r=new String[FuncToken.func_code.size()];
		Object[] keys=FuncToken.func_code.keySet().toArray();
		for(int x=0;x<keys.length;x++)
			r[x]=keys[x]+"";
		Arrays.sort(r);
		return r;
	}
	
	public static Token parse(String eq){
		deque tokens=tokenize(eq.replaceAll("\\s",""));
		return treeinate(tokens);
	}
	
	public static complex eval(String eq){
		return eval(eq,new LContext());
	}
	public static complex eval(String eq, LContext c){
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
				token=new NumToken(util.get_num(info.s));
			}
			else if(optest.matcher(eqtest).matches())
			{
				info.i++;
				token=new OpToken(eqtest);
			}
			else if(wordtest.matcher(eqtest).matches())
			{
				parseToken(wordparse,info);
				if(FuncToken.func_code.containsKey(info.s))
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
						r.push(new NumToken(complex.zero));
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
	
	public static complex eval_rpn(String eq){
		return eval_rpn(eq,new LContext());
	}
	public static complex eval_rpn(String eq, LContext c){
		cdeque stack=new cdeque();
		String[] pieces=eq.split(" ");
		for(int x=0;x<pieces.length;x++)
		{
			String test=pieces[x].substring(0,1);
			if(rpn_number.matcher(pieces[x]).matches())
				stack.push(util.get_num(pieces[x]));
			else if(optest.matcher(test).matches())
			{
				complex first, second=stack.pop();
				int opcode=OpToken.op_num.get(pieces[x]);
				switch(opcode)
				{
					case 0:
						first=stack.pop();
						stack.push(first.add(second));
						break;
					
					case 1:
						first=stack.pop();
						stack.push(first.sub(second));
						break;
					
					case 2:
						first=stack.pop();
						stack.push(first.mul(second));
						break;
					
					case 3:
						first=stack.pop();
						stack.push(first.div(second));
						break;
					
					case 4:
						first=stack.pop();
						stack.push(first.mod(second));
						break;
					
					case 5:
						first=stack.pop();
						stack.push(first.pow(second));
						break;
					
					case 6:
						stack.push(second.fac());
						break;
				}
			}
			else
			{
				if(FuncToken.func_code.containsKey(pieces[x]))
					switch(FuncToken.func_code.get(pieces[x]))
					{
						case EXP:
							stack.push(util.exp(stack.pop()));
							break;
						
						case SIN:
							stack.push(util.sin(stack.pop(),c.mode));
							break;
						case COS:
							stack.push(util.cos(stack.pop(),c.mode));
							break;
						case TAN:
							stack.push(util.tan(stack.pop(),c.mode));
							break;
						case CSC:
							stack.push(util.csc(stack.pop(),c.mode));
							break;
						case SEC:
							stack.push(util.sec(stack.pop(),c.mode));
							break;
						case COT:
							stack.push(util.cot(stack.pop(),c.mode));
							break;
						
						case SINH:
							stack.push(util.sinh(stack.pop()));
							break;
						case COSH:
							stack.push(util.cosh(stack.pop()));
							break;
						case TANH:
							stack.push(util.tanh(stack.pop()));
							break;
						case CSCH:
							stack.push(util.csch(stack.pop()));
							break;
						case SECH:
							stack.push(util.sech(stack.pop()));
							break;
						case COTH:
							stack.push(util.coth(stack.pop()));
							break;
						
						case ASIN:
							stack.push(util.asin(stack.pop(),c.mode));
							break;
						case ACOS:
							stack.push(util.acos(stack.pop(),c.mode));
							break;
						case ATAN:
							stack.push(util.atan(stack.pop(),c.mode));
							break;
						case ACSC:
							stack.push(util.acsc(stack.pop(),c.mode));
							break;
						case ASEC:
							stack.push(util.asec(stack.pop(),c.mode));
							break;
						case ACOT:
							stack.push(util.acot(stack.pop(),c.mode));
							break;
						
						case ASINH:
							stack.push(util.asinh(stack.pop()));
							break;
						case ACOSH:
							stack.push(util.acosh(stack.pop()));
							break;
						case ATANH:
							stack.push(util.atanh(stack.pop()));
							break;
						case ACSCH:
							stack.push(util.acsch(stack.pop()));
							break;
						case ASECH:
							stack.push(util.asech(stack.pop()));
							break;
						case ACOTH:
							stack.push(util.acoth(stack.pop()));
							break;
						
						case LN:
							stack.push(util.ln(stack.pop()));
							break;
						case LOG:
							stack.push(util.log(stack.pop()));
							break;
						
						case RE:
							stack.push(new complex(stack.pop().real));
							break;
						case IM:
							stack.push(new complex(stack.pop().imag));
							break;
						
						case FLOOR:
							stack.push(util.floor(stack.pop()));
							break;
						case CEIL:
							stack.push(util.ceil(stack.pop()));
							break;
						case ROUND:
							stack.push(util.round(stack.pop()));
							break;
						
						case ABS:
							stack.push(stack.pop().abs());
							break;
						case SQRT:
							stack.push(stack.pop().pow(complex.half));
							break;
						case ROOT:
							complex first,second;
							second=stack.pop();
							first=stack.pop();
							stack.push(first.pow(second.inv()));
							break;
						case INV:
							stack.push(stack.pop().inv());
							break;
						
						case GAMMA:
							stack.push(util.gamma(stack.pop()));
							break;
					}
				else
					stack.push(c.variables.get(pieces[x]).eval(c));
			}
		}
		if(stack.size()>1)
			return null;
		return stack.pop();
	}
}
