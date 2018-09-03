public class LinkedList
{
    class Node
    {
        private Object data;
        private Node next;
        public Node(Object obj)
        {
            data = obj;
            next = null;
        }
        public Object data()
        {
        	return data;
        }
        public Node next()
        {
        	return next;
        }
    }
    private Node head = new Node(null);
    private int size = 0;
    public Node getHead()
    {
    	return head;
    }
    public int getSize()
    {
    	return size;
    }
    public int search(Object obj)
    {
        Node n = head;
        int cnt = 0;
        while(n != null)
        {
            if (n.data == obj)
            	return cnt;
            n = n.next;
            cnt ++;
        }
        return -1;
    }

    public boolean isEmpty()
    {
        return size==0;
    }

    public void addRear(Object data)
    {
        
    	Node node = new Node(data);
    	if (isEmpty())
    	{
    		head = node;
    	}
    	else
    	{
    		Node n = head;
    		while(n.next != null)
    			n = n.next;
    		n.next = node;
    	}
    	size++;
    }

    public void remove(int cnt)
    {
        Node n = head;
    	if(cnt == 0)
        {
            head = head.next;
        }
        else
        {
        	for(int i=0;i<cnt-1;i++)
        		n = n.next;
        	n.next = n.next.next;
        }
        size--;
    }
}