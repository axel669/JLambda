package axel.lambda;

public class SArray {
	private Token[] buffer;
	
	public SArray(){
		buffer=null;
	}
	
	public Token get(int index){
		if(buffer==null)
			throw new Error("Can't get it.");
		return buffer[index];
	}
	public complex get(int index, LContext c){
		if(buffer==null)
			throw new Error("Can't get it.");
		return buffer[index].eval(c);
	}
	
	@SuppressWarnings("unchecked")
	public void push(Token item){
		if(buffer==null)
		{
			buffer=(Token[])new Object[1];
			buffer[0]=item;
		}
		else
		{
			Token[] n=(Token[])new Object[buffer.length+1];
			for(int x=0;x<buffer.length;x++)
				n[x]=buffer[x];
			n[buffer.length]=item;
			buffer=n;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Token pop(){
		Token r=buffer[buffer.length-1];
		if(buffer.length==1)
			buffer=null;
		else
		{
			Token[] n=(Token[])new Object[buffer.length-1];
			for(int x=0;x<buffer.length-1;x++)
				n[x]=buffer[x];
			buffer=n;
		}
		return r;
	}
	
	public int length(){
		return buffer==null?0:buffer.length;
	}
}
