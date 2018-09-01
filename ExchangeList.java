public class ExchangeList
{
	LinkedList l = new LinkedList();
	int size = 0;
	public LinkedList.Node head()
	{
		return l.head;
	}
	public void Insert(Exchange a)
	{
		l.addFront(a);
		size++;
	}
	public LinkedList getLL()
	{
		return l;
	}
	public int getSize()
	{
		return size;
	}
}