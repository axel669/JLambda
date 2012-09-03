JLambda
=======

The JLambda script is open source and free for personal or commercial use.

Command line takes one argument "-rpn" for rpn mode. No arguments puts in infix mode.

API:
all public API functions are part of the Lambda class and are static functions.

Token parse(String)
	Takes a string containing the equation to parse and returns a Token object.
	eval(LContext) can be called on this token as needed.

complex eval(String[,LContext])
	Takes a string containing the equation to evaluate and an optional LContext.
	If the LContext is omitted, the function uses a fresh LContext.

complex eval_rpn(String[,LContext])
	Same as eval, only in rpn mode.