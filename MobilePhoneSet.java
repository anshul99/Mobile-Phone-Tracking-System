public class MobilePhoneSet
{
	Myset mobileset = new Myset();
	public void Insert(MobilePhone m)
	{
		try 
		{
			mobileset.Insert(m);
		}
		catch (Exception e)
		{
			System.out.println("Already registered");
		}
	}
}