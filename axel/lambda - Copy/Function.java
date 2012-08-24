package axel.lambda;

public interface Function {
	public complex call(SArray<Token> args, LContext c);
}
