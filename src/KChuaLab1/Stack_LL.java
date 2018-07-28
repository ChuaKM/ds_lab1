/* Koon Chua
 * EN 605.202.81
 * Lab 1
 *
 * Stack_LL class that instantiates a Linked-List based stack
 * Contains isEmpty, push, pop, peek, empty, getSize methods
 */

public class Stack_LL {
    private Node head;          // Pointer that keeps track of the top of the stack
    private int size;

    /**
     * Defines node data structure for a linked list
     * Stores an element and a reference to another node
     */
    public class Node {
        public char item;
        Node next;
    }

    /**
     * Constructor: Instantiates top node of the stack
     * Establish head of stack and stack size
     */
    public Stack_LL() {
        head = null;
        size = 0;
    }

    /**
     * Method to return whether a stack is empty
     * @return true if size == 0, else false
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Method to push new value onto the top of the stack
     * Re-establish head
     * @param new_item new data to be added
     */
    // Add item to the beginning of the list
    public void push(char new_item) {

        Node temp_head = head;
        head = new Node();
        head.item = new_item;
        head.next = temp_head;
        size++;
    }

    /**
     * Method to pop off the top most value of the stack
     * Set head to the next value in the stack
     * @return data that was on top of the stack
     */
    public char pop() {
        if (!isEmpty())
        {
            char item = head.item;
            head = head.next;
            size--;
            return item;
        }
        else {
            return 0;
        }
    }

    /**
     * Method to return the topmost value in the stack without removing it
     * @return data that was on the top of the stack
     */
    public char peek() {
        return head.item;
    }

    /**
     * Method to delete all the elements in the stack
     */
    public void empty() {
        while(!isEmpty()) {
            pop();
        }
    }

    /**
     * Method to return size of the stack
     * @return size of stack
     */
    public int getSize() {
        return size;
    }

}