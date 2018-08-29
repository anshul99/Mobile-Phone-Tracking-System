public class Myset
{
    public static void main(String[] args)
    {
        try
        {
            Myset a = new Myset();
            System.out.println(a.IsEmpty());
            a.Insert(1);
            a.Insert(2);
            a.Insert(3);
            a.Insert(4);
            a.Insert(5);
            System.out.println(a.IsEmpty());

            Myset b = new Myset();
            b.Insert(4);
            b.Insert(3);
            b.Insert(0);
            b.Insert(9);
            b.Insert(7);
            b.Insert(-1);
            System.out.println(a.IsMember(1));
            System.out.println(a.IsMember(7));
            System.out.println(a);
            a.Delete(3);
            System.out.println(a);
            System.out.println(b);
            System.out.println(a.Union(b));
            System.out.println(a.Intersection(b));
        }
        catch (Exception e)
        {
            System.out.println("Except");
        }
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
    Node head;
    Node tail;
    int size;

    public int size()
    {
        return size;
    }

    public String toString()
    {
        String str= "";
        String temp = "";
        Node itr = head;
        while(itr != null)
        {
            temp = itr.data.toString();
            if(!temp.equals(""))
            {
                str = str+", "+temp;
            }
            itr = itr.next;
        }
        if(str.equals(""))
        {
            return str;
        }
        else
        {
            return str.substring(2);
        }
    }

    public Node Head()
    {
        return head;
    }

    public Node Search(Object obj)
    {
        Node itr = head;
        while(itr != null && obj != itr.data)
        {
            itr = itr.next;
        }
        return itr;
    }

    public boolean IsEmpty()
    {
        return size==0;
    }

    public boolean Add(Object data)
    {
        if(head==null)
        {
            head = new Node(data);
            tail = head;
        }
        else
        {
            Node temp = new Node(data);
            tail.next = temp;
            temp.parent = tail;
            tail = temp;
        }
        size++;
        return true;
    }

    public Node Remove(Node node)
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
            if(node.next != null)
            {
                node.next.parent = node.parent;
            }
            else
            {
                tail = node.parent;
            }
        }
        return node.next;
    }

    public Object Remove()
    {
        if(head == null)
        {
            throw new IllegalStateException();
        }
        if(head == tail)
        {
            Object temp = head.data;
            head = null;
            tail = null;
            size--;
            return temp;
        }
        else
        {
            Object data = head.data;
            head = head.next;
            head.parent = null;
            size--;
            return data;
        }
    }
}