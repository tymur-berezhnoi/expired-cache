package com.cachemap;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Timur Berezhnoi
 */
public class ExpireCacheMap<KeyType, ValueType> implements CacheMap<KeyType, ValueType> {

	private Entry<KeyType, ValueType>[] table;
	private static final int DEFAULT_CAPACITY = 10;
	
	private int size;
	private long timeToLive;

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
	public ExpireCacheMap() {
		table = new Entry[DEFAULT_CAPACITY];
	}

	public ExpireCacheMap(int capacity) {
		table = new Entry[capacity];
	}

	@Override
	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
		expireProcessor(timeToLive);
	}

	@Override
	public long getTimeToLive() {
		return timeToLive;
	}

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
				entry.next = new Entry<>(key, value);
				size++;
			}
		} else {
			table[hash] = new Entry<>(key, value);
			size++;
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clearExpired() {
		table = new Entry[DEFAULT_CAPACITY];
		size = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		table = new Entry[DEFAULT_CAPACITY];
		size = 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		Entry<KeyType, ValueType>[] tab;
		ValueType v;
		if ((tab = table) != null && size > 0) {
			for (int i = 0; i < tab.length; ++i) {
				for (Entry<KeyType, ValueType> e = tab[i]; e != null; e = e.next) {
					if ((v = e.value) == value
							|| (value != null && value.equals(v)))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public ValueType get(Object key) {
		int hash = hash(key);

		Entry<KeyType, ValueType> entry = table[hash];
		while (entry != null) {
			if (entry.key != null && entry.key.equals(key)) {
				return entry.value;
			} else if (entry.key == null) {
				return table[hash].value;
			}
			entry = entry.next;
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public ValueType remove(Object key) {
		int hash = hash(key);

		if (table[hash] == null) {
			return null;
		} else {
			Entry<KeyType, ValueType> previous = null;
			Entry<KeyType, ValueType> current = table[hash];

			while (current != null) {
				if (current.key.equals(key)) {
					if (previous == null) { // delete first entry node.
						table[hash] = table[hash].next;
						size--;
						return current.value;
					} else {
						previous.next = current.next;
						size--;
						return current.value;
					}
				}
				previous = current;
				current = current.next;
			}
			return null;
		}
	}

	@Override
	public int size() {
		return size;
	}

	private final int hash(Object key) {
		return Math.abs(key == null ? 0 : key.hashCode()) % DEFAULT_CAPACITY;
	}

	private void expireProcessor(long timeToLive) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				clearExpired();
				t.cancel();
			}
		}, timeToLive);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if(size == 0) {
			return builder.append("{}").toString();
		}
		for (int i = 0; i < size; i++) {
			if (table[i] != null) {
				Entry<KeyType, ValueType> entry = table[i];
				while (entry != null) {
					builder.append("{")
					.append(entry.key)
					.append("=")
					.append(entry.value)
					.append("}")
					.append(" ");
					entry = entry.next;
				}
			}
		}
		return builder.toString();
	}
}