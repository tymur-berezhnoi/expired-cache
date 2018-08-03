package com.cachemap;

public interface CacheMap<KeyType, ValueType>  {

	/**
	 * Sets how long new entries are kept in the cache. Until this method is called,
	 * some kind of default value should apply.
	 */
    void setTimeToLive(long timeToLive);
	long getTimeToLive();

    /**
     * Caches the given value under the given key.
     *
     * If there already is an item under the given key, it will be replaced by the new value. <p>
     *
     * @param key may not be null
     * @param value may be null, in which case the cache entry will be removed (if it existed).
     * @return the previous value, or null if none
     */
    ValueType put(KeyType key, ValueType value);

    /**
     * Clears all expired entries.
     * This is called automatically in conjuction with most operations,
     * but for memory optimization reasons you may call this explicitely at any time.
     */
    void clearExpired();

    /**
     * Removes all entries.
     */
    void clear();

    /**
     * Checks if the given key is included in this cache map.
     */
    boolean containsKey(KeyType keyType);

    /**
     * Checks if the given value is included in this cache map.
     */
    boolean containsValue(ValueType valueType);

    /**
     * Returns the value for the given key. Null if there is no value,
     * or if it has expired.
     */
    ValueType get(KeyType keyType);

    /**
     * True if this cache is empty.
     */
    boolean isEmpty();

    /**
     * Removes the given key.
     * @param keyType
     * @return the previous value, if there was any
     */
    ValueType remove(KeyType keyType);

    /**
     * How many entries this cache map contains.
     */
    int size();

}
