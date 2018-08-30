public class Myset
{
	LinkedList l = new LinkedList();
	public boolean IsEmpty()
	{
		return l.isEmpty();
	}
	public boolean IsMember(Object o)
	{
		if (l.search(o) != null)
			return true;
		else
			return false;
	}
	public void Insert(Object o) throws Exception
	{
		if (IsMember(o) == false)
		{
			l.addFront(o);
		}
		else
			throw new Exception();
	}
	public void Delete(Object o) throws Exception
	{
		LinkedList.Node n = l.search(o);
		if (n != null)
			l.remove(n);
		else
			throw new Exception();
	}
	public Myset Union(Myset a)
	{
		Myset s = new Myset();
		LinkedList l2 = a.l;
		LinkedList.Node n1 = l.head;
		LinkedList.Node n2 = l2.head;
		while (n1 != null)
		{
			try
			{
				s.Insert(n1.data);
			}
			catch (Exception e)
			{}
			n1 = n1.next;
		}
		while (n2 != null)
		{
			try
			{
				s.Insert(n2.data);
			}
			catch (Exception e)
			{}
			n2 = n2.next;
		}
		return s;
	}
	public Myset Intersection(Myset a)
	{
		Myset s = new Myset();
		LinkedList.Node n1 = l.head;
		while(n1 != null)
		{
			if (a.IsMember(n1.data))
			{
				try
				{
					s.Insert(n1.data);
				}
				catch(Exception e)
				{}
			}
			n1 = n1.next;
		}
		return s;
	}
}

class LinkedList
{
    class Node
    {
        Object data;
        Node next;
        Node parent;
        public Node(Object obj)
        {
            data = obj;
            next = null;
            parent = null;
        }
    }
    Node head = new Node(null);
    Node tail = new Node(null);
    int size = 0;
    public Node search(Object obj)
    {
        Node itr = head;
        while(itr != null && obj != itr.data)
        {
            itr = itr.next;
        }
        return itr;
    }

    public boolean isEmpty()
    {
        return size==0;
    }

    public void addFront(Object data)
    {
        
    	Node node = new Node(data);
    	node.next = head;
    	head.parent = node;
        head = node;
        size++;
    }

    public void remove(Node node)
    {
        size--;
        if(node == head)
        {
            if(head == tail)
            {
                head = null;
                tail = null;
            }
            else
            {
                head = head.next;
                head.parent = null;
            }
        }
        else
        {
        	node.parent.next = node.next;
            if(node != tail)
            {
            	node.next.parent = node.parent;
            }
            else
            {
                tail = node.parent;
            }
        }
    }
}
