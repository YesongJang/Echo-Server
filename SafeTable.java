package echo;

import java.util.Hashtable;

public class SafeTable<K, V> extends Hashtable<K, V> {
    @Override
    public synchronized V get(Object key) {
        return super.get(key);
    }

    @Override
    public synchronized V put(K key, V value) {
        return super.put(key, value);
    }
}

