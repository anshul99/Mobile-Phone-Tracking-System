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
			System.out.println("Mobile Phone " + a + " is already off");
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
	public void performAction(String actionMessage)
	{
		String[] str = actionMessage.split(" ");
		String action;
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
				System.out.println("Invalid action message");
				return;
			}
		}
		else if (str.length == 2)
		{
			action = str[0];
			if (!(action.equals("switchOffMobile") || action.equals("queryMobilePhoneSet")))
			{
				System.out.println("Invalid action message");
				return;
			}
			try
			{
				a = Integer.valueOf(str[1]);
			}
			catch (NumberFormatException e)
			{
				System.out.println("Invalid action message");
				return;
			}
		}
		else
		{
			System.out.println("Invalid action message");
			return;
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
						System.out.println("Exchange " + b + " already exists");
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
								System.out.println("Exchange " + a + " does not exist");
							}
						}
						catch (Exception ex)
						{
							System.out.println(ex.getMessage());
						}
					}
				}
				catch (Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			}
			break;
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
					System.out.println(ex.getMessage());
				}
				if (mob == null)
				{
					try
					{
						Exchange e = search(b);
						if (e != null && e.childList().getSize() != 0)
						{
							System.out.println("Exchange " + b + " is not a base station");
						}
						else
						{
							if (e != null)
							{
								MobilePhone m = new MobilePhone(a);
								switchOn(m,e);
							}
							else
								System.out.println("Exchange " + b + " does not exist");
						}
					} 
					catch (Exception ex) 
					{
						System.out.println(ex.getMessage());
					}
				}
				else
				{
					System.out.println("Mobile phone " + a + " is already registered with exchange " + base.getId());
				}
			}
			break;
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
						System.out.println(ex.getMessage());
					}
				}
				else
					System.out.println("Mobile phone " + a + " not found");
			}
			break;
		case "queryNthChild":
			{
				try
				{
					System.out.print(actionMessage + ": ");
					Exchange e1 = search(a);
					if (e1 != null)
					{
						try
						{
							Exchange e2 = e1.child(b);
							int id = e2.getId();
							System.out.println(id);
						}
						catch (Exception ex)
						{
							System.out.println(ex.getMessage());
						}
					}
					else
						System.out.println("Exchange " + a + " does not exist");
				}
				catch (Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			}
			break;
		case "queryMobilePhoneSet":
			{
				try
				{
					System.out.print(actionMessage + ": ");
					Exchange e1 = search(a);
					if (e1 != null)
					{
						MobilePhoneSet mset1 = e1.residentSet();
						LinkedList l1 = mset1.mobset().list();
						LinkedList.Node n1 = l1.getHead();
						int pos = 0;
						if (l1.getSize() == 0)
							System.out.println("No mobiles found");
						for(int i=0;i<l1.getSize();i++)
						{
							MobilePhone m = (MobilePhone)n1.data();
							if (m.status())
								pos = i;
							n1 = n1.next();
						}
						n1 = l1.getHead();
						for(int i=0;i<pos;i++)
						{
							MobilePhone m = (MobilePhone)n1.data();
							if (m.status())
								System.out.print(m.number() + ", ");
							n1 = n1.next();
						}
						System.out.println(((MobilePhone)n1.data()).number());
					}
					else
						System.out.println("Exchange " + a + " does not exist");
				}
				catch (Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			}
			break;
		default:
				System.out.println("Invalid action message");
		}
	}
}
