public class RoutingMapTree
{
	private Exchange root;
	public RoutingMapTree()
	{
		root = new Exchange(0);
	}
	public Exchange getRoot()
	{
		return root;
	}
	public RoutingMapTree(Exchange a)
	{
		root = a;
	}
	public boolean containsNode(Exchange a) throws Exception
	{
		Exchange r = root;
		ExchangeList l = r.childList();
		LinkedList.Node n = l.getHead();
		if (r == a)
			return true;
		else
		{
			for(int i=0;i<r.numChildren();i++)
			{
				if(r.subtree(i).containsNode(a))
					return true;
				n = n.next();
			}
			return false;
		}
	}
	public void switchOn(MobilePhone a,Exchange b) throws Exception
	{
		Exchange r = getRoot();
		MobilePhoneSet mset = r.residentSet();
		MobilePhone m = mset.search(a.number());
		if (m != null)
		{
			Exchange base = a.location();
			base.unregister(a);
			while(base != getRoot())
			{
				base = base.getParent();
				base.unregister(a);
			}
			
		}
		a.switchOn();
		a.setBase(b);
		b.register(a);
		while(b != getRoot())
		{
			b = b.getParent();
			b.register(a);
		}	
	}
	public void switchOff(MobilePhone a) throws Exception
	{
		if (a.status())
		{
			a.switchOff();
		}
		else
			throw new Exception("Mobile Phone " + a.number() + " is already off");
	}
	public Exchange search(int id) throws Exception
	{
		Exchange r = root;
		ExchangeList l = r.childList();
		LinkedList.Node n = l.getHead();
		if (r.getId() == id)
			return r;
		else
		{
			for(int i=0;i<r.numChildren();i++)
			{
				Exchange e = r.subtree(i).search(id);
				if (e != null)
					return e;
				n = n.next();
			}
			return null;
		}
	}
	public String performAction(String actionMessage)
	{
		String[] str = actionMessage.split(" ");
		String action;
		String ans = actionMessage + ": ";
		int a = 0;
		int b = 0;
		if (str.length == 3)
		{
			action = str[0];
			try
			{
				a = Integer.valueOf(str[1]);
				b = Integer.valueOf(str[2]);
			}
			catch (NumberFormatException e)
			{
				return ans + "Invalid action message";
			}
		}
		else if (str.length == 2)
		{
			action = str[0];
			if (!(action.equals("switchOffMobile") || action.equals("queryMobilePhoneSet")))
			{
				return ans + "Invalid action message";
			}
			try
			{
				a = Integer.valueOf(str[1]);
			}
			catch (NumberFormatException e)
			{
				return ans + "Invalid action message";
			}
		}
		else
		{
			return ans + "Invalid action message";
		}
		switch(action)
		{
		case "addExchange":
			{
				try
				{
					Exchange e1 = new Exchange(b);
					if (search(b) != null)
					{
						return ans + "Exchange " + b + " already exists";
					}
					else
					{
						try
						{
							Exchange e2 = search(a);
							if (e2 != null)
							{
								e2.addChild(e1);
								e1.setParent(e2);
							}
							else
							{
								return ans + "Exchange " + a + " does not exist";
							}
						}
						catch (Exception ex)
						{
							return ans + ex.getMessage();
						}
					}
				}
				catch (Exception ex)
				{
					return ans + ex.getMessage();
				}
			}
			return "";
		case "switchOnMobile":
			{
				Exchange r = getRoot();
				MobilePhoneSet mset = r.residentSet();
				MobilePhone mob = mset.search(a);
				Exchange base = new Exchange(-1);
				try
				{
					if (mob != null)
						base = mob.location();
				}
				catch (Exception ex)
				{
					return ans + ex.getMessage();
				}
				if (mob == null)
				{
					try
					{
						Exchange e = search(b);
						if (e != null && e.childList().getSize() != 0)
						{
							return ans + "Exchange " + b + " is not a base station";
						}
						else
						{
							if (e != null)
							{
								MobilePhone m = new MobilePhone(a);
								switchOn(m,e);
							}
							else
							{
								return ans + "Exchange " + b + " does not exist";
							}
						}
					} 
					catch (Exception ex) 
					{
						return ans + ex.getMessage();
					}
				}
				else
				{
					return ans + "Mobile phone " + a + " is already registered with exchange " + base.getId();
				}
			}
			return "";
		case "switchOffMobile":
			{
				Exchange r = getRoot();
				MobilePhoneSet mset = r.residentSet();
				MobilePhone mob = mset.search(a);
				if (mob != null)
				{
					try
					{
						switchOff(mob);
					}
					catch (Exception ex)
					{
						return ans + ex.getMessage();
					}
				}
				else
				{
					return ans + "Mobile phone " + a + " not found";
				}
			}
			return "";
		case "queryNthChild":
			{
				try
				{
					Exchange e1 = search(a);
					if (e1 != null)
					{
						try
						{
							Exchange e2 = e1.child(b);
							return ans + e2.getId();
						}
						catch (Exception ex)
						{
							return ans + ex.getMessage();
						}
					}
					else
					{
						return ans + "Exchange " + a + " does not exist";
					}
				}
				catch (Exception ex)
				{
					return ans + ex.getMessage();
				}
			}
		case "queryMobilePhoneSet":
			{
				try
				{
					Exchange e1 = search(a);
					if (e1 != null)
					{
						MobilePhoneSet mset1 = e1.residentSet();
						LinkedList l1 = mset1.mobset().list();
						LinkedList.Node n1 = l1.getHead();
						int pos = 0;
						int cnt = 0;
						String s = "";
						if (l1.getSize() == 0)
							return ans + "No mobiles found";
						for(int i=0;i<l1.getSize();i++)
						{
							MobilePhone m = (MobilePhone)n1.data();
							if (m.status())
							{
								pos = i;
								cnt += 1;
							}
							n1 = n1.next();
						}
						n1 = l1.getHead();
						for(int i=0;i<pos;i++)
						{
							MobilePhone m = (MobilePhone)n1.data();
							if (m.status())
								s += m.number() + ", ";
							n1 = n1.next();
						}
						MobilePhone m = (MobilePhone)n1.data();
						if (m.status())
							s += m.number();
						if (cnt > 0)
							return ans + s;
						else
							return ans + "No mobiles found";
					}
					else
					{
						return ans + "Exchange " + a + " does not exist";
					}
				}
				catch (Exception ex)
				{
					return ans + ex.getMessage();
				}
			}
		default:
				return ans + "Invalid action message";
		}
	}
}