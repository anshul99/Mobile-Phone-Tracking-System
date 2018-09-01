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
	public RoutingMapTree subtree(Exchange a)
	{
		RoutingMapTree t = new RoutingMapTree(a);
		return t;
	}
	public boolean containsNode(Exchange a)
	{
		ExchangeList l = getRoot().children;
		LinkedList.Node n = l.getLL().getHead();
		if (getRoot() == a)
			return true;
		else
		{
			for(int i=0;i<l.getSize();i++)
			{
				if(subtree((Exchange) n.getData()).containsNode(a))
					return true;
				n = n.next;
			}
			return false;
		}
	}
	public Exchange search(int id)
	{
		ExchangeList l = getRoot().children;
		LinkedList.Node n = l.getLL().getHead();
		if (getRoot().id == id)
			return getRoot();
		else
		{
			for(int i=0;i<l.getSize();i++)
			{
				if(subtree((Exchange) n.getData()).search(id) != null)
					return subtree((Exchange) n.getData()).search(id);
				n = n.next;
			}
			return null;
		}
	}
	public void switchOn(MobilePhone a,Exchange b)
	{
		if (!a.on)
		{
			a.switchOn();
			try
			{
				b.register(a);
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
			while(b != root)
			{
				b = b.parent;
				try
				{
					b.register(a);
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
		else
			System.out.println("Already on");
			
	}
	public void switchOff(MobilePhone a)
	{
		if (a.on)
		{
			/*try
			{
				Exchange b = a.location();
			} 
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}*/
			a.switchOff();
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
				Exchange e2 = search(a);
				if (e2 != null)
				{
					e2.addChild(e1);
					e1.setParent(e2);
				}
			}
		}
	}
}