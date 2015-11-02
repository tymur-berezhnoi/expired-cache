
/**
 * @author Timur Berezhnoi
 */
public class ExpireCahceMap<KeyType, ValueType> implements CacheMap<KeyType, ValueType> {

	/* (non-Javadoc)
	 * @see CacheMap#setTimeToLive(long)
	 */
	@Override
	public void setTimeToLive(long timeToLive) {
		
		
	}

	/* (non-Javadoc)
	 * @see CacheMap#getTimeToLive()
	 */
	@Override
	public long getTimeToLive() {
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see CacheMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public ValueType put(KeyType key, ValueType value) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see CacheMap#clearExpired()
	 */
	@Override
	public void clearExpired() {
		
		
	}

	/* (non-Javadoc)
	 * @see CacheMap#clear()
	 */
	@Override
	public void clear() {
		
		
	}

	/* (non-Javadoc)
	 * @see CacheMap#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		
		return false;
	}

	/* (non-Javadoc)
	 * @see CacheMap#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		
		return false;
	}

	/* (non-Javadoc)
	 * @see CacheMap#get(java.lang.Object)
	 */
	@Override
	public ValueType get(Object key) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see CacheMap#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		
		return false;
	}

	/* (non-Javadoc)
	 * @see CacheMap#remove(java.lang.Object)
	 */
	@Override
	public ValueType remove(Object key) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see CacheMap#size()
	 */
	@Override
	public int size() {
		
		return 0;
	}
	
	
	
}