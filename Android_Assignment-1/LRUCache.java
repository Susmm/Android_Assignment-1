import java.util.HashMap;
import java.util.Map;

/**
 * LRUCache implements a Least Recently Used (LRU) cache data structure.
 * 
 * A cache is initialized with a fixed capacity. When the number of items exceeds the capacity, 
 * it evicts the least recently used item.
 * 
 * This class supports the following operations:
 * 
 * - get(int key): Retrieves the value associated with the key if present in the cache; 
 *   otherwise, returns -1. Also marks the item as recently used.
 * 
 * - put(int key, int value): Inserts a new key-value pair into the cache or updates an existing key.
 *   If the cache is at full capacity, it removes the least recently used item before insertion.
 * 
 * Internally, it uses a HashMap for O(1) access to cache entries and a Doubly Linked List to track usage order.
 */

public class LRUCache {

    private class Node {
        int key, val;
        Node prev, next;
        Node(int key, int val, Node prev, Node next) {
            this.key = key;
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node head = null, tail = null;
    private Map<Integer, Node> hm;
    private int sz, cap;

    public LRUCache(int capacity) {
        head = null;
        tail = null;
        sz = 0;
        cap = capacity;
        hm = new HashMap<>();
    }

    public int get(int key) {
        if (!hm.containsKey(key)) {
            return -1;
        }
        int value = hm.get(key).val;
        put(key, value);
        return value;
    }

    public void put(int key, int value) {
        if (hm.containsKey(key)) {
            Node temp = hm.get(key);
            if (temp == head) {
                temp.val = value;
                return;
            }
            if (temp.prev != null) {
                temp.prev.next = temp.next;
                if (temp.next != null) {
                    temp.next.prev = temp.prev;
                }
                if (tail == temp) {
                    tail = temp.prev;
                }
            }
            temp.next = head;
            head.prev = temp;
            temp.prev = null;
            temp.val = value;
            head = temp;
        } else {
            if (sz < cap) {
                Node temp = new Node(key, value, null, null);
                if (head == null) {
                    head = temp;
                    tail = temp;
                } else {
                    temp.next = head;
                    head.prev = temp;
                    head = temp;
                }
                sz++;
                hm.put(key, temp);
            } else {
                hm.remove(tail.key);
                if (tail.prev != null) {
                    tail.prev.next = null;
                    Node toDelete = tail;
                    tail = tail.prev;
                } else {
                    Node toDelete = tail;
                    head = null;
                    tail = null;
                }
                sz--;
                put(key, value);
            }
        }
    }
    // driver code
    public static void main(String[] args){
		
		LRUCache lru = new LRUCache(2);
        lru.put(1, 1);
		lru.put(2, 2);
		lru.get(1);
		lru.put(3, 3);
		lru.get(2);      
		lru.put(4, 4);    
		lru.get(1);      
		lru.get(3);      
		lru.get(4);   
	}
}

