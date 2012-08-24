package axel.lambda;

public class deque{
	private Node first, last;
	private int length;
	
	public deque(){
		first=null;
		last=null;
		length=0;
	}
	public void push(Token t){
		if(length==0)
			first=last=new Node(t);
		else
			last=last.insertAfter(new Node(t));
		length++;
	}
	public void pushf(Token t){
		if(length==0)
			first=last=new Node(t);
		else
			first=first.insertBefore(new Node(t));
		length++;
	}
	public Token pop(){
		Token r=last.value;
		length--;
		if(length>0)
		{
			last=last.prev;
			last.next=null;
		}
		else
			first=last=null;
		return r;
	}
	public Token popf(){
		Token r=first.value;
		length--;
		if(length>0)
		{
			first=first.next;
			first.prev=null;
		}
		else
			first=last=null;
		return r;
	}
	public int size(){
		return length;
	}
}
class Node{
	public Node prev, next;
	public Token value;
	
	public Node(Token t){
		value=t;
	}
	
	public Node insertBefore(Node n){
		prev=n;
		n.next=this;
		return n;
	}
	public Node insertAfter(Node n){
		next=n;
		n.prev=this;
		return n;
	}
}
