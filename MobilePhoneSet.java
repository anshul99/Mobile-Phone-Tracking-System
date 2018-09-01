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
}