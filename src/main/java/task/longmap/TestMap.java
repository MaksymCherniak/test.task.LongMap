package task.longmap;

public interface TestMap<V> {

    V put(long key, V value);
    V get(long key);
    V remove(long key);

    boolean isEmpty();
    boolean containsKey(long key);
    boolean containsValue(V value);

    long[] keys();
    V[] values(Class<V> vClass);

    long size();
    void clear();
}
