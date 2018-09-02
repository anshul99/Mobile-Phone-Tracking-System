public class Exchange
{
	int id;
	Exchange parent;
	ExchangeList children = new ExchangeList();
	MobilePhoneSet mobiles = new MobilePhoneSet();
	Exchange (int number)
	{
		id = number;
	}
	public MobilePhoneSet residentSet()
	{
		return mobiles;
	}
	public void register(MobilePhone a) throws Exception
	{
		mobiles.Insert(a);
	}
	public void unregister(MobilePhone a) throws Exception
	{
		mobiles.Delete(a);
	}
	public void addChild(Exchange a)
	{
		children.Insert(a);
	}
	public void setParent(Exchange a)
	{
		parent = a;
	}
	public boolean searchMob(MobilePhone m)
	{
		return mobiles.search(m);
	}
	public Exchange parent()
	{
		return parent;
	}
	public int numChildren()
	{
		return children.getSize();
	}
	public Exchange child(int i) throws Exception
	{
		if (i<children.getSize())
		{
			LinkedList l = children.getLL();
			LinkedList.Node n = l.getHead();
			for(int j=0;j<i;j++)
			{
				n = n.next;
			}
			return (Exchange) n.data;
		}
		else
			throw new Exception("Out of range");
	}
	public boolean isRoot()
	{
		if(parent != null)
			return false;
		else
			return true;
	}
	public RoutingMapTree subtree(int i) throws Exception
	{
		Exchange a;
		a = child(i);
		RoutingMapTree t = new RoutingMapTree(a);
		return t;
	}
}