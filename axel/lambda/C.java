package axel.lambda;

public class C {
	public static void out(Object... args){
		for(int x=0;x<args.length;x++)
			System.out.println(args[x]+"");
	}
	public static void puts(Object... args){
		for(int x=0;x<args.length;x++)
		{
			System.out.print(args[x]+"");
			if(x<args.length-1)
				System.out.print(' ');
		}
	}
}
