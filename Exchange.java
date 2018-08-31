public class Exchange
{
	int id;
	Exchange parent;
	ExchangeList children;
	MobilePhoneSet mobiles;
	Exchange (int number)
	{
		id = number;
	}
	public MobilePhoneSet residentSet()
	{
		return mobiles;
	}
}