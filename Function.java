package axel.lambda;

public interface Function {
	public Num call(SArray<Token> args, LContext c);
}
