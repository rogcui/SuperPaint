package superpaint;
/**
 * Class used to store MyShape objects.
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
public class DoublyLinkedList  {
  private ListNode head; 
  private ListNode tail; 
  
  // constructor
  public DoublyLinkedList() {
    head = null; 
    tail = null;
  }
  
  public boolean isEmpty() {
    return head == null;
  }
  
  // addfront method to add object to the head
  public void addFront (Object item) {
    ListNode newLink = new ListNode(item); 
    
    if (isEmpty()) 
      tail = newLink; 
    else
      head.previous = newLink; 
    newLink.next = head; 
    head = newLink; 
  }
  
  // addEnd method to add object to the tail
  public void addEnd (Object item) {
    ListNode newLink = new ListNode(item); 
    if (isEmpty()) 
      head = newLink; 
    else {
      tail.next = newLink; 
      newLink.previous = tail; 
    }
    tail = newLink; 
  }
  
  // removeFront method to remove from front
  public ListNode removeFront() { 
    if (head == null)
      return null;
    ListNode temp = head;
    if (head.next == null) 
      tail = null; 
    else
      head.next.previous = null;
    head = head.next; 
    return temp;
  }
  
  // removeEnd method to remove from end
  public ListNode removeEnd() { 
    if (head == null)
      return null;
    ListNode temp = tail;
    
    if (head.next == null)
      head = null; 
    else {
      tail.previous.next = null;
    }
      
    tail = tail.previous; 
    return temp;
  }
  
  // Search for an item in the list
  // returns reference to ListNode if found, null otherwise
  public ListNode search( Object key ) {
    ListNode searchNode;
    searchNode = head;
    while ( (searchNode != null) && (!key.equals(searchNode.getValue())) ) {
      searchNode = searchNode.getNext();
    }
    return searchNode;
  }
}


class ListNode {
  
  public Object value; 
  public ListNode next; // next link in list
  public ListNode previous; // previous link in list
  
  public ListNode( Object nodeValue ) {
    this( nodeValue, null);
  }
  
  public ListNode( Object nodeValue, ListNode nodeNext ) {
    value = nodeValue;
    next = nodeNext;
  }
  
  // Accessor: Return the value for current node object
  public Object getValue() {
    return value;
  }
  
  // Accessor: Return reference to next node object
  public ListNode getNext() {
    return next;
  }
  
  // Mutator: Set new value for current node object
  public void setValue( Object newValue ) {
    value = newValue;
  }
  
  
  // Mutator: Set new reference to the next node object
  public void setNext( ListNode newNext ) {
    next = newNext;
  }
  
  public ListNode(long d) {
    value = d;
  }
  
  public void displayLink() {
    System.out.print(value + " ");
  }
  
}
