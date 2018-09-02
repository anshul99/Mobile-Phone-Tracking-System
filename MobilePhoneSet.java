public class MobilePhoneSet
{
	Myset mobileset = new Myset();
	public void Insert(MobilePhone m) throws Exception
	{
		try 
		{
			mobileset.Insert(m);
		}
		catch (Exception e)
		{
			throw new Exception("Already registered");
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
			throw new Exception("Already unregistered");
		}
	}
	public boolean search(MobilePhone m)
	{
		return mobileset.IsMember(m);
	}
	public int getSize()
	{
		return mobileset.l.size;
	}
}