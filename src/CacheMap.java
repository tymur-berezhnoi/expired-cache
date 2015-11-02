/**
 * A generic cache. Works just like a Map, except that entries automatically "disappear"
 * when they expire. <p>
 *
 * For example, if you have a bunch of persons in a database or external system,
 * and you have a corresponding Person class with id of type Long, you can create a cache
 * with 60-second expiry like this: <p>
 * <pre>
 * CacheMap&lt;Long, Person&gt; cache = new CacheMapImpl&lt;Long, Person&gt;();
 * cache.setTimeToLive(60000);
 * </pre>
 *
 * Your code for fetching a person by id would look something like this: <p>
 *
 * <pre>
 * Person person = cache.get(personId);
 * if (person == null) {
 *   person = slowSystemThatShouldntBeUsedTooOften.getPerson(personId);
 *   cache.put(personId, person);
 * }
 * return person;
 * </pre>
 *
 * Additional notes: <br>
 * <ul>
 * <li>
 * 	Implementations do not have to be thread-safe.
 * </li>
 * <li>
 *  No methods should ever return or count any entries that have expired.
 * </li>
 * <li>
 *  Implementations do not have to clear expired entries automatically
 *  as soon as they expire.
 *  It is OK to clean up expired entries in conjunction with method calls instead.
 *  From the outside, however, it should look like entries disappear as soon as they
 *  get expired.
 * </li>
 * <li>
 *  For unit-testing purposes, this class should get the current time using
 *  the Clock class, not System.currentTimeMillis(). That way unit tests can override the
 *  time.
 * </li>
 * </ul>
 *
 * @see Clock
 */
public interface CacheMap<KeyType, ValueType>  {

	/**
	 * Sets how long new entries are kept in the cache. Until this method is called,
	 * some kind of default value should apply.
	 */
	public void setTimeToLive(long timeToLive);
	public long getTimeToLive();

    /**
     * Caches the given value under the given key.
     *
     * If there already is an item under the given key, it will be replaced by the new value. <p>
     *
     * @param key may not be null
     * @param item may be null, in which case the cache entry will be removed (if it existed).
     * @return the previous value, or null if none
     */
    public ValueType put(KeyType key, ValueType value);

    /**
     * Clears all expired entries.
     * This is called automatically in conjuction with most operations,
     * but for memory optimization reasons you may call this explicitely at any time.
     */
    public void clearExpired();

    /**
     * Removes all entries.
     */
    public void clear();

    /**
     * Checks if the given key is included in this cache map.
     */
    public boolean containsKey(Object key);

    /**
     * Checks if the given value is included in this cache map.
     */
    public boolean containsValue(Object value);

    /**
     * Returns the value for the given key. Null if there is no value,
     * or if it has expired.
     */
    public ValueType get(Object key);

    /**
     * True if this cache is empty.
     */
    public boolean isEmpty();

    /**
     * Removes the given key.
     * @param key
     * @return the previous value, if there was any
     */
    public ValueType remove(Object key);

    /**
     * How many entries this cache map contains.
     */
    public int size();

}
