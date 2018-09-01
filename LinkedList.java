public class LinkedList
{
    class Node
    {
        Object data;
        Node next;
        public Node(Object obj)
        {
            data = obj;
            next = null;
        }
        public Object getData()
        {
        	return data;
        }
    }
    Node head = new Node(null);
    int size = 0;
    public Node getHead()
    {
    	return head;
    }
    public int search(Object obj)
    {
        Node itr = head;
        int cnt = 0;
        while(itr != null)
        {
            if (itr.data == obj)
            	return cnt;
            itr = itr.next;
            cnt ++;
        }
        return -1;
    }

    public boolean isEmpty()
    {
        return size==0;
    }

    public void addFront(Object data)
    {
        
    	Node node = new Node(data);
    	node.next = head;
        head = node;
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