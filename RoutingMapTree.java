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
	public Exchange findPhone(MobilePhone m) throws Exception
	{
		Exchange r = getRoot();
		MobilePhoneSet mset = r.residentSet();
		MobilePhone a = mset.search(m.number());
		if (a != null)
			return a.location();
		else
			throw new Exception("Error - No mobile phone with identifier " + m.number() + " found in the network");
	}
	public Exchange lowestRouter(Exchange a, Exchange b) throws Exception
	{
		Exchange e1 = search(a.getId());
		Exchange e2 = search(b.getId());
		if (e1.numChildren() != 0 || e2.numChildren() != 0)
			throw new Exception("Error - Please enter base exchange");
		if (e1 == e2)
			return a;
		else
		{
			Exchange c = e1.getParent();
			while(!c.isRoot())
			{
				for(int i=0;i<c.numChildren();i++)
				{
					RoutingMapTree t = c.subtree(i);
					if (t.containsNode(e2))
						return c;
				}
				c = c.getParent();
			}
			return getRoot();
		}
	}
	public ExchangeList routeCall(MobilePhone a, MobilePhone b) throws Exception
	{
		ExchangeList route = new ExchangeList();
		Exchange r = getRoot();
		MobilePhoneSet mset = r.residentSet();
		MobilePhone m1 = mset.search(a.number());
		MobilePhone m2 = mset.search(b.number());
		if (m1 != null && m2 != null)
		{
			Exchange e1;
			Exchange e2;
			try
			{
				e1 = m1.location();
			}
			catch (Exception ex)
			{
				throw new Exception("Error - Mobile phone with identifier " + a.number() + " is currently switched off");
			}
			try
			{
				e2 = m2.location();
			}
			catch (Exception ex)
			{
				throw new Exception("Error - Mobile phone with identifier " + b.number() + " is currently switched off");
			}
			Exchange e3 = lowestRouter(e1,e2);
			while (e1 != e3)
			{
				route.Insert(e1);
				e1 = e1.getParent();
			}
			while (e3 != e2)
			{
				route.Insert(e3);
				for (int i=0;i<e3.numChildren();i++)
				{
					if (e3.subtree(i).containsNode(e2))
					{
						e3 = e3.child(i);
						break;
					}
				}
			}
			route.Insert(e2);
			return route;
		}
		else
		{
			if (m1 == null)
				throw new Exception("Error - No mobile phone with identifier " + a.number());
			else if (m2 == null)
				throw new Exception("Error - No mobile phone with identifier " + b.number());
		}
		return null;
	}
	public void movePhone(MobilePhone a, Exchange b) throws Exception
	{
		Exchange r = getRoot();
		MobilePhoneSet mset = r.residentSet();
		MobilePhone m = mset.search(a.number());
		Exchange c = search(b.getId());
		if (m != null && c != null)
		{
			if (m.status())
			{
				if (c.numChildren() == 0)
					switchOn(m,c);
				else
					throw new Exception("Error - " + b.getId() + " is not a base exchange");
			}
			else
				throw new Exception("Error - Mobile phone " + a.number() + " is off");
		}
		else
		{
			if (m == null)
				throw new Exception("Error - No mobile with identifier " + a.number());
			if (c == null)
				throw new Exception("Error - No exchange with identifier " + b.getId());
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
			if (!(action.equals("switchOffMobile") || action.equals("queryMobilePhoneSet") || action.equals("findPhone")))
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
		case "findPhone":
			{
				MobilePhone m = new MobilePhone(a);
				try
				{
					Exchange e = findPhone(m);
					return "queryFindPhone " + a + ": " + e.getId();
				}
				catch (Exception ex)
				{
					return "queryFindPhone " + a + ": " + ex.getMessage();
				}
			}
		case "lowestRouter":
			{	
				Exchange e1 = new Exchange(a);
				Exchange e2 = new Exchange(b);
				try
				{
					Exchange e3 = lowestRouter(e1,e2);
					return "queryLowestRouter " + a + " " + b + ": " + e3.getId();
				} 
				catch (Exception ex)
				{
					return "queryLowestRouter " + a + " " + b + ": " + ex.getMessage();
				}
			}
		case "findCallPath":
			{
				MobilePhone m1 = new MobilePhone(a);
				MobilePhone m2 = new MobilePhone(b);
				if (a == b)
				{
					return "queryFindCallPath " + a + " " + b + ": " + "Error - Please enter different mobiles";
				}
				try
				{
					ExchangeList elist = routeCall(m1,m2);
					String s = "";
					LinkedList.Node n = elist.getHead();
					for(int i=0;i<elist.getSize()-1;i++)
					{
						s += ((Exchange)n.data()).getId();
						s += ", ";
						n = n.next();
					}
					s += ((Exchange)n.data()).getId();
					return "queryFindCallPath " + a + " " + b + ": " + s;
				}
				catch (Exception ex)
				{
					return "queryFindCallPath " + a + " " + b + ": " + ex.getMessage();
				}
			}
		case "movePhone":
			{
				try
				{
					MobilePhone m = new MobilePhone(a);
					Exchange e = new Exchange(b);
					movePhone(m,e);
				}
				catch (Exception ex)
				{
					return ans + ex.getMessage();
				}
				return "";
			}
		default:
				return ans + "Invalid action message";
		}
	}
}