package axel.jlambda;

public class util{
	public static final double pi=Math.PI;
	public static final double pi_2=Math.PI/2;
	public static final double e=Math.E;
	public static final complex ln_10_c=ln(complex.ten);
	
	public static final int degrees=0;
	public static final int radians=1;
	
	public static final complex i=new complex(0,1);
	public static final complex zero=new complex();
	public static final complex half=new complex(.5);
	public static final complex one=new complex(1);
	public static final complex two=new complex(2);
	public static final complex c_e=new complex(e);
	public static final complex c_pi=new complex(pi);
	public static final complex c_pi_2=new complex(pi_2);
	public static final complex ten=new complex(10);
	public static final complex the_answer=new complex(42);
	public static final complex ipi=new complex(0,pi);
	public static final complex ipi_2=new complex(0,pi_2);
	
	
	public static double factorial(double x){
		double r=1;
		while(x > 1)
			r*=x--;
		return r;
	}
	
	public static double sin(double x, int mode){
		if(mode==0 && multiple_180(x))
			return 0;
		if(mode==1 && multiple_pi(x))
			return 0;
		return Math.sin(mode==0?Math.toRadians(x):x);
	}
	public static double cos(double x, int mode){
		if(mode==0 && multiple_180(x-90))
			return 0;
		if(mode==1 && multiple_pi(x-pi_2))
			return 0;
		return Math.cos(mode==0?Math.toRadians(x):x);
	}
	public static double tan(double x, int mode){
		if(mode==0 && multiple_180(x))
			return 0;
		if(mode==1 && multiple_pi(x))
			return 0;
		return Math.tan(mode==0?Math.toRadians(x):x);
	}
	public static double csc(double x, int mode){
		return 1/sin(x,mode);
	}
	public static double sec(double x, int mode){
		return 1/cos(x,mode);
	}
	public static double cot(double x, int mode){
		return 1/tan(x,mode);
	}
	
	public static double asin(double x, int mode){
		double r=Math.asin(x);
		return mode==0?Math.toDegrees(r):r;
	}
	public static double acos(double x, int mode){
		double r=Math.acos(x);
		return mode==0?Math.toDegrees(r):r;
	}
	public static double atan(double x, int mode){
		double r=Math.atan(x);
		return mode==0?Math.toDegrees(r):r;
	}
	public static double acsc(double x, int mode){
		return asin(1/x,mode);
	}
	public static double asec(double x, int mode){
		return acos(1/x,mode);
	}
	public static double acot(double x, int mode){
		return atan(1/x,mode);
	}
	
	public static double sinh(double x){
		return (exp(x)-exp(-x))/2;
	}
	public static double cosh(double x){
		return (exp(x)+exp(-x))/2;
	}
	public static double tanh(double x){
		double a=exp(x), b=exp(-x);
		return (a-b)/(a+b);
	}
	public static double csch(double x){
		return 2/(exp(x)-exp(-x));
	}
	public static double sech(double x){
		return 2/(exp(x)+exp(-x));
	}
	public static double coth(double x){
		double a=exp(x), b=exp(-x);
		return (a+b)/(a-b);
	}
	
	public static double ln(double x){
		return Math.log(x);
	}
	public static double log(double x){
		return Math.log10(x);
	}
	public static double log(double x, double base){
		return Math.log(x)/Math.log(base);
	}
	
	public static double exp(double x){
		return Math.exp(x);
	}
	
	
	public static complex sin(complex x, int mode){
		if(x.imag==0)
			return new complex(sin(x.real,mode));
		if(x.real==0)
			return new complex(0,sinh(x.imag));
		return new complex(sin(x.real,mode)*cosh(x.imag),cos(x.real,mode)*sinh(x.imag));
	}
	public static complex cos(complex x, int mode){
		if(x.imag==0)
			return new complex(cos(x.real,mode));
		if(x.real==0)
			return new complex(cosh(x.imag));
		return new complex(cos(x.real,mode)*cosh(x.imag),sin(x.real,mode)*sinh(x.imag));
	}
	public static complex tan(complex x, int mode){
		if(x.imag==0)
			return new complex(tan(x.real,mode));
		return sin(x,mode).div(cos(x,mode));
	}
	public static complex csc(complex x, int mode){
		return one.div(sin(x,mode));
	}
	public static complex sec(complex x, int mode){
		return one.div(cos(x,mode));
	}
	public static complex cot(complex x, int mode){
		return cos(x,mode).div(sin(x,mode));
	}
	
	public static complex floor(complex x){
		return new complex(Math.floor(x.real),Math.floor(x.imag));
	}
	public static complex ceil(complex x){
		return new complex(Math.floor(x.real),Math.floor(x.imag));
	}
	public static complex round(complex x){
		return new complex(Math.floor(x.real),Math.floor(x.imag));
	}
	
	public static complex exp(complex x){
		if(x.imag==0)
			return new complex(exp(x.real));
		return c_e.pow(x);
	}
	public static complex inv(complex x){
		return one.div(x);
	}
	
	public static complex sinh(complex x){
		return exp(x).sub(exp(x.negate())).div(two);
	}
	public static complex cosh(complex x){
		return exp(x).add(exp(x.negate())).div(two);
	}
	public static complex tanh(complex x){
		complex a=exp(x), b=exp(x.negate());
		return a.sub(b).div(a.add(b));
	}
	public static complex csch(complex x){
		return two.div(exp(x).sub(exp(x.negate())));
	}
	public static complex sech(complex x){
		return two.div(exp(x).add(exp(x.negate())));
	}
	public static complex coth(complex x){
		complex a=exp(x), b=exp(x.negate());
		return a.add(b).div(a.sub(b));
	}
	
	public static complex asin(complex x, int mode){
		if(x.imag==0)
			return new complex(asin(x.real,mode));
		complex r=i.negate().mul( ln( x.mul( i ).add( sqrt( one.sub( x.pow( two ) ) ) ) ) );
		return mode==0?r.to_deg():r;
	}
	public static complex acos(complex x, int mode){
		complex r=c_pi_2.sub(asin(x,1));
		return mode==0?r.to_deg():r;
	}
	public static complex atan(complex x, int mode){
		complex r=new complex(0,0.5).mul( ln( one.sub( i.mul(x) ) ).sub( ln( one.add( i.mul(x) ) ) ) );
		return mode==0?r.to_deg():r;
	}
	public static complex acsc(complex x, int mode){
		return asin(x.inv(),mode);
	}
	public static complex asec(complex x, int mode){
		return acos(x.inv(),mode);
	}
	public static complex acot(complex x, int mode){
		return atan(x.inv(),mode);
	}
	
	public static complex asinh(complex x){
		return ln( x.add( sqrt( one.add( x.pow( two ) ) ) ) );
	}
	public static complex acosh(complex x){
		return ln( x.add( sqrt(x.add(one)).mul(sqrt(x.sub(one))) ) );
	}
	public static complex atanh(complex x){
		return half.mul( ln( one.add(x) ).sub( ln( one.sub(x) ) ) );
	}
	public static complex acsch(complex x){
		return asinh(x.inv());
	}
	public static complex asech(complex x){
		return acosh(x.inv());
	}
	public static complex acoth(complex x){
		return atanh(x.inv());
	}
	
	public static complex sqrt(complex x){
		return x.pow(half);
	}
	
	public static complex ln(complex x){
		double r=x.abs().real, angle=Math.atan2(x.imag,x.real);
		return new complex(ln(r),angle);
	}
	public static complex log(complex x){
		return ln(x).div(ln_10_c);
	}
	public static complex log(complex x, complex base){
		return ln(x).div(ln(base));
	}
	
	public static int g=7;
	public static double[] p={
		0.99999999999980993, 676.5203681218851, -1259.1392167224028,
		771.32342877765313, -176.61502916214059, 12.507343278686905,
		-0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7
	};
	
	public static complex gamma(complex z){
		if(z.real<0.5)
			return c_pi.div( util.sin( c_pi.mul(z),1 ).mul(gamma( one.sub(z) )) );
		z=z.sub(complex.one);
		complex x=new complex(p[0]);
		for(int i=1;i<g+2;i++)
			x=x.add( (new complex(p[i])).div( z.add( new complex(i) ) ) );
		complex t=new complex(z.real+g+0.5,z.imag);
		return new complex(Math.sqrt(2*util.pi)).mul(
			t.pow( new complex(z.real+0.5,z.imag) )
		).mul(
			util.exp(t.negate())
		).mul(x);
	}
	
	
	public static String space(int tabs){
    	String r="";
    	while(tabs-->0)
    		r+="   ";
    	return r;
    }
	public static complex get_num(String s){
    	if(s.charAt(s.length()-1)=='i')
    		return new complex(0,Double.parseDouble(s.substring(0,s.length()-1)));
    	return new complex(Double.parseDouble(s));
    }
	
	public static boolean multiple_pi(double x){
		x/=Math.PI;
		return x==Math.floor(x);
	}
	public static boolean multiple_180(double x){
		x/=180;
		return x==Math.floor(x);
	}
}
