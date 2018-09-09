public class MobilePhone
{
	private int id;
	private boolean on;
	private Exchange base;
	MobilePhone (int number)
	{
		id = number;
	}
	public int number()
	{
		return id;
	}
	public boolean status()
	{
		return on;
	}
	public void switchOn()
	{
		on = true;
	}
	public void switchOff()
	{
		on = false;
	}
	public Exchange location() throws Exception
	{
		if (on)
		{
			return base;
		}
		else
			throw new Exception("Error - Mobile phone with identifier " + id + " is currently swithed off");
	}
	public void setBase(Exchange b)
	{
		base = b;
	}
}