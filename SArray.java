package axel.lambda;

public class SArray<T> {
	private T[] buffer;
	
	public SArray(){
		buffer=null;
	}
	
	public T get(int index){
		if(buffer==null)
			throw new Error("Can't get it.");
		return buffer[index];
	}
	
	@SuppressWarnings("unchecked")
	public void push(T item){
		if(buffer==null)
		{
			buffer=(T[])new Object[1];
			buffer[0]=item;
		}
		else
		{
			T[] n=(T[])new Object[buffer.length+1];
			for(int x=0;x<buffer.length;x++)
				n[x]=buffer[x];
			n[buffer.length]=item;
			buffer=n;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T pop(){
		T r=buffer[buffer.length-1];
		if(buffer.length==1)
			buffer=null;
		else
		{
			T[] n=(T[])new Object[buffer.length-1];
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
