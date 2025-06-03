/**
 * MyHashMap implements a simplified version of a hash map (also known as dictionary or unordered_map),
 * without using any built-in hash table libraries.
 *
 * The data structure supports the following operations in average-case O(1) time:
 *
 * - put(int key, int value): Inserts a new key-value pair or updates the value of an existing key.
 * - get(int key): Retrieves the value associated with the key, or returns -1 if the key does not exist.
 * - remove(int key): Deletes the key-value pair from the map if present.
 *
 * Internally, this implementation uses an array of linked lists (separate chaining) to handle collisions,
 * along with a custom hash function based on polynomial rolling hash to compute bucket indices.
 */
 
public class MyHashMap {
    
    private static class HashNode {
        int key, val;
        HashNode next;

        HashNode(int key, int val) {
            this.key = key;
            this.val = val;
            this.next = null;
        }
    }

    private final HashNode[] bucket = new HashNode[100];

    public MyHashMap() {
    
    }

    private long getHashCode(int n) {
        long coeff = 1, p = 37, hashcode = 0;
        while (n != 0) {
            hashcode += coeff * (n % 10);
            coeff *= p;
            n /= 10;
        }
        return hashcode;
    }

    private int getHash(long hashcode) {
        return (int) (hashcode & 99L); // hashcode % 100 assuming & 99 was used as bitmask
    }

    public void put(int key, int value) {
        int index = getHash(getHashCode(key));
        if (bucket[index] == null) {
            bucket[index] = new HashNode(key, value);
        } else {
            HashNode node = bucket[index];
            while (node.next != null && node.key != key) {
                node = node.next;
            }
            if (node.key == key) {
                node.val = value;
            } else {
                node.next = new HashNode(key, value);
            }
        }
    }

    public int get(int key) {
        int index = getHash(getHashCode(key));
        HashNode node = bucket[index];
        while (node != null && node.key != key) {
            node = node.next;
        }
        return node != null ? node.val : -1;
    }

    public void remove(int key) {
        int index = getHash(getHashCode(key));
        HashNode node = bucket[index], prev = null;
        while (node != null && node.key != key) {
            prev = node;
            node = node.next;
        }
        if (node != null) {
            if (prev != null) {
                prev.next = node.next;
            } else {
                bucket[index] = node.next;
            }
        }
    }
    public static void main(String[] args){
		
		MyHashMap obj = new MyHashMap();
		obj.put(1, 10);
		obj.put(2, 20);
		obj.get(1);     
		obj.get(3);      
		obj.put(2, 30);  
		obj.get(2);      
		obj.remove(2);
		obj.get(2);       
	}
}

