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
		try
		{
			mobiles.Insert(a);
		}
		catch (Exception e)
		{
			throw new Exception("Already registered");
		}
	}
	public void addChild(Exchange a)
	{
		children.Insert(a);
	}
	public void setParent(Exchange a)
	{
		parent = a;
	}
}