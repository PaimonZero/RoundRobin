package controller;

class CircularLinkedList {
    private Node head;

    public CircularLinkedList() {
        this.head = null;
    }
    
    class Node {
        private String name;
        private double data;
        private Node next;

        public String getName() {
            return name;
        }

        public void setName(String name) {    
            this.name = name;
        }

        public double getData() {
            return data;
        }

        public void setData(double data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node() {
        }

        public Node(double data, String name) {
            this.data = data;
            this.name = name.toUpperCase();
        }
    }
    
    public boolean isEmpty() {
        return (head == null);
    }
    
    public int length() {
        if(isEmpty()) {
            return 0;
        }else {
            int count = 1;
            Node temp = head;
            while(temp.getNext() != head){
                temp = temp.getNext();
                count++;
            }
            return count;
        }
    }
    

    public void insert(double data, String name) {
        Node newNode = new Node(data, name);
        if (head == null) {
            head = newNode;
            newNode.setNext(head);
        } else {
            Node temp = head;
            while (temp.getNext() != head) {
                temp = temp.getNext();
            }
            temp.setNext(newNode);
            newNode.setNext(head);
        }
    }
    

    public void roundRobin(double timeQuantum) {
        if (head == null) {
            System.out.println("Empty List!");
            return;
        }

        Node current = head;
        int count = length();
        do {
            if (current.getData() > timeQuantum) {
                current.setData(current.getData() - timeQuantum);
                System.out.printf("Executing process %s with remaining time: %.3f\n", current.getName(), current.getData());
                current = current.getNext(); // Move to the next process
                count--;
            } else {
                System.out.println("Executing process "+current.getName()+" with remaining time: 0");
                Node nextNode = current.getNext();
                remove(current); // Remove the process that has finished
                count--;
                if(head != null) {      //check condition !!!Warning!!!
                   current = nextNode; // Move to the next process
                } else {
                    break;
                }
            }
        } while (count > 0);     //current != head
    }

    public void remove(Node nodeToRemove) {
        if (nodeToRemove == head) {
            if (head.getNext() == head) {
                // Only one node in the list
                head = null;
            } else {
                Node temp = head;
                while (temp.getNext() != head) {
                    temp = temp.getNext();
                }
                temp.setNext(head.getNext());
                head = head.getNext();
            }
        } else {
            Node temp = head;
            while (temp.getNext() != head && temp.getNext() != nodeToRemove) {
                temp = temp.getNext();
            }
            if (temp.getNext() == nodeToRemove) {
                temp.setNext(nodeToRemove.getNext());
            }
        }
    }
    
    public void display() {
        if (head == null) {
            System.out.println("Empty List!");
            return;
        }

        Node temp = head;
        do {
            System.out.print(temp.getData() + "->");
            temp = temp.getNext();
        } while (temp != head);
        System.out.println(".");
    }
    
    public static void main(String[] args) {
        CircularLinkedList link = new CircularLinkedList();
        
        link.insert(15.5, "Nam");
        link.insert(10.1, "Nam2");
        link.insert(20.6, "Nam3");
        link.insert(2.3, "Nam4");
        
        
        double timeQuantum = 2.1;
        
        if(timeQuantum <= 0) {
            System.err.println("Quantum must be > 0! Current quantum: "+timeQuantum);
        } else {
            System.out.println(">>..>>..>>Round-Robin<<..<<..<<");
            System.out.println(">>Time quantum: " + timeQuantum);
            link.display();
            int loop = 1;
            System.out.println("------------------------------------");
            do {                    
                System.out.println(">>Loop " +loop);
                link.roundRobin(timeQuantum);
                loop++;
                System.out.println("------------------------------------");
            } while(link.isEmpty() != true);
        }
    }
}
