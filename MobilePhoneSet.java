public class MobilePhoneSet
{
	private Myset mobileset = new Myset();
	public Myset mobset()
	{
		return mobileset;
	}
	public void Insert(MobilePhone m) throws Exception
	{
		try 
		{
			mobileset.Insert(m);
		}
		catch (Exception e)
		{
			throw new Exception("Mobile phone " + m.number() + " is already registered");
		}
	}
	public void Delete(MobilePhone m) throws Exception
	{
		try
		{
			mobileset.Delete(m);
		}
		catch (Exception e)
		{
			throw new Exception("Mobile phone " + m.number() + " not found");
		}
	}
	public int getSize()
	{
		return mobileset.list().getSize();
	}
	public MobilePhone search(int num)
	{
		
		LinkedList.Node n = mobileset.list().getHead();
		for(int i=0;i<getSize();i++)
		{
			MobilePhone m = (MobilePhone) n.data();
			if (m.number() == num)
			{
				return m;
			}
			n = n.next();
		}
		return null;
	}
}