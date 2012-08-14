package axel.lambda;

public class Util {
	public static final double to_rad=Math.PI/180.0;
	public static final double to_deg=180.0/Math.PI;
	
	/*
	Trig Functions
	====================================================================================
	*/
	public static double sin(double x, Lambda.Mode mode){
		if(mode==Lambda.Mode.radians && piMultiple(x))
			return 0;
		if(mode==Lambda.Mode.degrees && multiple180(x))
			return 0;
		return Math.sin(mode==Lambda.Mode.degrees?Math.toRadians(x):x);
	}
	public static double cos(double x, Lambda.Mode mode){
		if(mode==Lambda.Mode.radians && piMultiple(x-Math.PI/2))
			return 0;
		if(mode==Lambda.Mode.degrees && multiple180(x-90.0))
			return 0;
		return Math.cos(mode==Lambda.Mode.degrees?Math.toRadians(x):x);
	}
	public static double tan(double x, Lambda.Mode mode){
		if(mode==Lambda.Mode.radians && piMultiple(x))
			return 0;
		if(mode==Lambda.Mode.degrees && multiple180(x))
			return 0;
		return Math.tan(mode==Lambda.Mode.degrees?Math.toRadians(x):x);
	}
	
	public static double asin(double x, Lambda.Mode mode){
		return Math.asin(x)*(mode==Lambda.Mode.degrees?to_deg:1);
	}
	public static double acos(double x, Lambda.Mode mode){
		return Math.acos(x)*(mode==Lambda.Mode.degrees?to_deg:1);
	}
	public static double atan(double x, Lambda.Mode mode){
		return Math.atan(x)*(mode==Lambda.Mode.degrees?to_deg:1);
	}
	
	/*
	====================================================================================
	*/
	
	public static double factorial(double x){
		if(x%1!=0.0)
			return gamma(x);
		double r=1, y=2;
		while(y<=x)
			r*=y++;
		return r;
	}
	
	static final double GAMMA[] = {
        57.156235665862923517,
        -59.597960355475491248,
        14.136097974741747174,
        -0.49191381609762019978,
        .33994649984811888699e-4,
        .46523628927048575665e-4,
        -.98374475304879564677e-4,
        .15808870322491248884e-3,
        -.21026444172410488319e-3,
        .21743961811521264320e-3,
        -.16431810653676389022e-3,
        .84418223983852743293e-4,
        -.26190838401581408670e-4,
        .36899182659531622704e-5
    };
    public static final double lgamma(double x) {
        double tmp = x + 5.2421875;
        double sum = 0.99999999999999709182;
        for (int i = 0; i < GAMMA.length; ++i) {
            sum += GAMMA[i] / ++x;
        }
        return 0.9189385332046727418 + Math.log(sum) + (tmp-4.7421875)*Math.log(tmp) - tmp;
    }
    public static double gamma(double x){
    	return Math.exp(lgamma(x));
    }
    
    public static String space(int tabs){
    	String r="";
    	while(tabs-->0)
    		r+="   ";
    	return r;
    }
    public static Num getNum(String s){
    	if(s.charAt(s.length()-1)=='i')
    		return new Complex(0,Double.parseDouble(s.substring(0,s.length()-1)));
    	return new Real(Double.parseDouble(s));
    }
    
    public static boolean piMultiple(double x){
    	x/=Math.PI;
    	return x==Math.floor(x);
    }
    public static boolean multiple180(double x){
    	x/=180;
    	return x==Math.floor(x);
    }
    //public static double
}
