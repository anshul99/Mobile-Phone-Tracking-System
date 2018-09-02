public class RoutingMapTree
{
	Exchange root;
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
		Exchange r = getRoot();
		ExchangeList l = r.children;
		LinkedList.Node n = l.getLL().getHead();
		if (r == a)
			return true;
		else
		{
			for(int i=0;i<r.numChildren();i++)
			{
				if(r.subtree(i).containsNode(a))
					return true;
				n = n.next;
			}
			return false;
		}
	}
	public Exchange search(int id) throws Exception
	{
		Exchange r = getRoot();
		ExchangeList l = r.children;
		LinkedList.Node n = l.getLL().getHead();
		if (r.id == id)
			return r;
		else
		{
			for(int i=0;i<r.numChildren();i++)
			{
				Exchange e = r.subtree(i).search(id);
				if (e != null)
					return e;
				n = n.next;
			}
			return null;
		}
	}
	public void switchOn(MobilePhone a,Exchange b) throws Exception
	{
		if (!a.status())
		{
			a.switchOn();
			a.setBase(b);
			b.register(a);
			while(b != root)
			{
				b = b.parent;
				b.register(a);	
			}
		}
		else
			System.out.println("Already on");
			
	}
	public void switchOff(MobilePhone a) throws Exception
	{
		if (a.status())
		{
			Exchange b = a.location();
			a.switchOff();
			b.unregister(a);
			while(b != root)
			{
				b = b.parent;
				b.unregister(a);
			}
		}
		else
			System.out.println("Already off");
	}
	public void performAction(String actionMessage)
	{
		String[] str = actionMessage.split(" ");
		String action;
		int a;
		int b;
		if (str.length == 3)
		{
			action = str[0];
			a = Integer.valueOf(str[1]);
			b = Integer.valueOf(str[2]);
		}
		else
		{
			action = str[0];
			a = Integer.valueOf(str[1]);
			b = 0;
		}
		switch(action)
		{
		case "addExchange":
			{
				Exchange e1 = new Exchange(b);
				try
				{
					Exchange e2 = search(a);
					if (e2 != null)
					{
						e2.addChild(e1);
						e1.setParent(e2);
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
				try
				{
					Exchange e = search(b);
					if (e != null && e.children.size != 0)
					{
						System.out.println("Exchange is not at level 0");
					}
					else
					{
						if (e != null)
						{
							MobilePhone m = new MobilePhone(a);
							switchOn(m,e);
						}
						else
							System.out.println("Exchange does not exist");
					}
				} 
				catch (Exception ex) 
				{
					System.out.println(ex.getMessage());
				}
			}
			break;
		case "switchOffMobile":
			boolean f = false;
			Exchange r = getRoot();
			MobilePhoneSet mset = r.mobiles;
			LinkedList.Node n = mset.mobileset.l.head;
			MobilePhone mob = new MobilePhone(0);
			for(int i=0;i<mset.getSize();i++)
			{
				MobilePhone m = (MobilePhone) n.data;
				if (m.id == a)
				{
					mob = m;
					f = true;
					break;
				}
			}
			if (f)
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
				System.out.println("Mobile not found");
			break;
		case "queryNthChild":
			try
			{
				Exchange e1 = search(a);
				if (e1 != null)
				{
					try
					{
						Exchange e2 = e1.child(b);
						int id = e2.id;
						System.out.println(id);
					}
					catch (Exception ex)
					{
						System.out.println(ex.getMessage());
					}
				}
				else
					System.out.println("Exchange not found");
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			break;
		case "queryMobilePhoneSet":
			try
			{
				Exchange e1 = search(a);
				if (e1 != null)
				{
					MobilePhoneSet mset1 = e1.mobiles;
					LinkedList l1 = mset1.mobileset.l;
					LinkedList.Node n1 = l1.head;
					if (l1.size == 0)
						System.out.println("No mobiles found");
					for(int i=0;i<l1.size;i++)
					{
						System.out.println(((MobilePhone)n1.data).id);
						n1 = n1.next;
					}
				}
				else
					System.out.println("Exchange not found");
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			break;
		}
	}
}