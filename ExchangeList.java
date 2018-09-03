public class ExchangeList
{
	private LinkedList l = new LinkedList();
	private int size = 0;
	public LinkedList.Node getHead()
	{
		return l.getHead();
	}
	public void Insert(Exchange a)
	{
		l.addRear(a);
		size++;
	}
	public LinkedList getList()
	{
		return l;
	}
	public int getSize()
	{
		return size;
	}
}