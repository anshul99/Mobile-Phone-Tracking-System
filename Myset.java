public class Myset
{
	LinkedList l = new LinkedList();
	public boolean IsEmpty()
	{
		return l.isEmpty();
	}
	public boolean IsMember(Object o)
	{
		if (l.search(o) == -1)
			return false;
		else
			return true;
	}
	public void Insert(Object o) throws Exception
	{
		if (IsMember(o) == false)
		{
			l.addFront(o);
		}
		else
			throw new Exception();
	}
	public void Delete(Object o) throws Exception
	{
		int n = l.search(o);
		if (IsMember(o))
			l.remove(n);
		else
			throw new Exception();
	}
	public Myset Union(Myset a)
	{
		Myset s = new Myset();
		LinkedList l2 = a.l;
		LinkedList.Node n1 = l.head;
		LinkedList.Node n2 = l2.head;
		while (n1 != null)
		{
			try
			{
				s.Insert(n1.data);
			}
			catch (Exception e)
			{}
			n1 = n1.next;
		}
		while (n2 != null)
		{
			try
			{
				s.Insert(n2.data);
			}
			catch (Exception e)
			{}
			n2 = n2.next;
		}
		return s;
	}
	public Myset Intersection(Myset a)
	{
		Myset s = new Myset();
		LinkedList.Node n1 = l.head;
		while(n1 != null)
		{
			if (a.IsMember(n1.data))
			{
				try
				{
					s.Insert(n1.data);
				}
				catch(Exception e)
				{}
			}
			n1 = n1.next;
		}
		return s;
	}
}