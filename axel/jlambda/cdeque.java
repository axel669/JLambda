package axel.jlambda;

public class cdeque{
	private cNode first, last;
	private int length;
	
	public cdeque(){
		first=null;
		last=null;
		length=0;
	}
	public void push(complex t){
		if(length==0)
			first=last=new cNode(t);
		else
			last=last.insertAfter(new cNode(t));
		length++;
	}
	public void pushf(complex t){
		if(length==0)
			first=last=new cNode(t);
		else
			first=first.insertBefore(new cNode(t));
		length++;
	}
	public complex pop(){
		complex r=last.value;
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
	public complex popf(){
		complex r=first.value;
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
class cNode{
	public cNode prev, next;
	public complex value;
	
	public cNode(complex t){
		value=t;
	}
	
	public cNode insertBefore(cNode n){
		prev=n;
		n.next=this;
		return n;
	}
	public cNode insertAfter(cNode n){
		next=n;
		n.prev=this;
		return n;
	}
}
