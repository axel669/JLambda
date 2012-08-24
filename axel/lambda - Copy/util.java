package axel.lambda;

public class util{
	public static final double pi=Math.PI;
	public static final double pi_2=Math.PI/2;
	public static final double e=Math.E;
	
	public static final int degrees=0;
	public static final int radians=1;
	
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
		return complex.one.div(sin(x,mode));
	}
	public static complex sec(complex x, int mode){
		return complex.one.div(cos(x,mode));
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
		return complex.e.pow(x);
	}
	public static complex inv(complex x){
		return complex.one.div(x);
	}
	
	public static complex sinh(complex x){
		return exp(x).sub(exp(x.negate())).div(complex.two);
	}
	public static complex cosh(complex x){
		return exp(x).add(exp(x.negate())).div(complex.two);
	}
	public static complex tanh(complex x){
		complex a=exp(x), b=exp(x.negate());
		return a.sub(b).div(a.add(b));
	}
	public static complex csch(complex x){
		return complex.two.div(exp(x).sub(exp(x.negate())));
	}
	public static complex sech(complex x){
		return complex.two.div(exp(x).add(exp(x.negate())));
	}
	public static complex coth(complex x){
		complex a=exp(x), b=exp(x.negate());
		return a.add(b).div(a.sub(b));
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
