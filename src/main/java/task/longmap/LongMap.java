package task.longmap;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class LongMap<V> implements TestMap<V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private int capacity;
    private int limit;
    private Node<V>[] table;
    private int size;

    public LongMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public LongMap(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }

        this.capacity = initialCapacity;
        this.limit = capacity * 4 / 3;
        this.table = new Node[capacity];
    }

    public V put(long key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, capacity);

        for (Node<V> node = table[index]; node != null; node = node.next) {
            if (node.hash == hash && node.key == key) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }

        addNode(hash, key, value, index);
        return null;
    }

    public V get(long key) {
        int index = indexFor(hash(key), capacity);

        for (Node<V> node = table[index]; node != null; node = node.next) {
            if (key == node.key) {
                return node.value;
            }
        }

        return null;
    }

    public V remove(long key) {
        int index = indexFor(hash(key), capacity);

        Node<V> previous = null;
        Node<V> node = table[index];

        while (node != null) {
            Node<V> next = node.next;

            if (key == node.key) {
                if (previous == null) {
                    table[index] = next;
                } else {
                    previous.next = next;
                }
                size--;
                return node.value;
            }

            previous = node;
            node = next;
        }

        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        for (Node node : table) {
            while (node != null) {
                if (node.key == key) {
                    return true;
                }
                node = node.next;
            }
        }

        return false;
    }

    public boolean containsValue(V value) {
        if (value == null) {
            return false;
        }

        for (Node node : table) {
            while (node != null) {
                if (value.equals(node.value)) {
                    return true;
                }
                node = node.next;
            }
        }

        return false;
    }

    public long[] keys() {
        long[] keys = new long[size];
        int index = 0;

        for (Node node : table) {
            while (node != null) {
                keys[index++] = node.key;
                node = node.next;
            }
        }

        return keys;
    }

    public V[] values(Class<V> vClass) {
        V[] values = (V[]) Array.newInstance(vClass, size);
        int index = 0;

        for (Node node : table) {
            while (node != null) {
                values[index++] = (V) node.value;
                node = node.next;
            }
        }

        return values;
    }

    public long size() {
        return size;
    }

    public void clear() {
        size = 0;
        Arrays.fill(table, null);
    }

    static class Node<V> {
        final int hash;
        final Long key;
        V value;
        Node<V> next;

        Node(int hash, Long key, V value, Node<V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final Long getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof LongMap.Node) {
                LongMap.Node<?> e = (LongMap.Node<?>) o;
                if (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }

    private int hash(Long key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int indexFor(int hash, int capacity) {
        return hash % capacity;
    }

    private void addNode(int hash, Long key, V value, int index) {
        Node<V> node = table[index];
        table[index] = new Node<V>(hash, key, value, node);

        if (++size > limit) {
            resize(capacity * 2);
        }
    }

    private void resize(int newCapacity) {
        if (newCapacity > MAXIMUM_CAPACITY) {
            newCapacity = MAXIMUM_CAPACITY;
        }

        Node<V>[] newTable = new Node[newCapacity];

        for (int i = 0; i < table.length; i++) {
            Node<V> node = table[i];

            while (node != null) {
                long key = node.key;
                int index = indexFor(hash(key), newCapacity);
                Node<V> next = node.next;
                node.next = newTable[index];
                newTable[index] = node;
                node = next;
            }
        }

        table = newTable;
        capacity = newCapacity;
        limit = newCapacity * 4 / 3;
    }
}
