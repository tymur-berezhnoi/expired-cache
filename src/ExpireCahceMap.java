import java.util.Arrays;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * @author Timur Berezhnoi
 */
public class ExpireCahceMap<KeyType, ValueType> implements
		CacheMap<KeyType, ValueType> {

	private Entry<KeyType, ValueType>[] table;
	static final int DEFAULT_CAPACITY = 10;

	static class Entry<KeyType, ValueType> {

		KeyType key;
		ValueType value;
		Entry<KeyType, ValueType> next;

		public Entry(KeyType key, ValueType value) {
			this.key = key;
			this.value = value;
		}
	}

	@SuppressWarnings("unchecked")
	public ExpireCahceMap() {
		table = new Entry[DEFAULT_CAPACITY];
	}

	@SuppressWarnings("unchecked")
	public ExpireCahceMap(int capacity) {
		table = new Entry[capacity];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#setTimeToLive(long)
	 */
	@Override
	public void setTimeToLive(long timeToLive) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#getTimeToLive()
	 */
	@Override
	public long getTimeToLive() {

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public ValueType put(KeyType key, ValueType value) {
		int hash = hash(key);
		
		Entry<KeyType, ValueType> entry = table[hash];
		if (entry != null) {
			if (entry.key == null || entry.key.equals(key)) {
				entry.value = value;
			} else {
				while (entry.next != null) {
					entry = entry.next;
					if (entry.key.equals(key)) {
						return value;
					}
				}
				entry.next = new Entry<KeyType, ValueType>(key, value);
			}
		} else {
			table[hash] = new Entry<KeyType, ValueType>(key, value);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#clearExpired()
	 */
	@Override
	public void clearExpired() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#clear()
	 */
	@Override
	public void clear() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#get(java.lang.Object)
	 */
	@Override
	public ValueType get(Object key) {
		int hash = hash(key);
		
		Entry<KeyType, ValueType> entry = table[hash];
		while (entry != null) {
			if (entry.key != null && entry.key.equals(key)) {
				return entry.value;
			} else if(entry.key == null) {
				return table[hash].value;
			}
			entry = entry.next;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#isEmpty()
	 */
	@Override
	public boolean isEmpty() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#remove(java.lang.Object)
	 */
	@Override
	public ValueType remove(Object key) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CacheMap#size()
	 */
	@Override
	public int size() {

		return 0;
	}

	private final int hash(Object key) {
		int result = 0;
		return (key == null) ? result : (result = key.hashCode()) ^ (result >>> 16);
	}

}