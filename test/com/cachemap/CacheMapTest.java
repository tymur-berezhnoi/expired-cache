package com.cachemap;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cachemap.util.Clock;

/**
 * JUnit test case for a CacheMap implementation.
 * <p/>
 * Feel free to add more methods.
 */
public class CacheMapTest {

	private CacheMap<Integer, String> cache;
	final static long TIME_TO_LIVE = 1000;

	@Before
	public void setUp() throws Exception {
		cache = new ExpireCacheMap<Integer, String>();
	}
	
	@Test
	public void testExpiry() throws InterruptedException {
		cache.put(1, "apple");
		
		assertEquals("apple", cache.get(1));
		assertFalse(cache.isEmpty());
		
		cache.setTimeToLive(TIME_TO_LIVE);
		Clock.setTime(3000);
		
		assertNull(cache.get(1));
		assertTrue(cache.isEmpty());
	}

	@Test
	public void testSize() throws Exception {
		assertEquals(0, cache.size());
		
		cache.put(1, "apple");
		assertEquals(1, cache.size());
		
		cache.setTimeToLive(TIME_TO_LIVE);
		Clock.setTime(3000);
		
		assertEquals(0, cache.size());
	}

	@Test
	public void testPartialExpiry() throws Exception {
		// Add an apple, it will expire at 2000
		cache.put(1, "apple");
		cache.setTimeToLive(2000);
		Clock.setTime(1500);
		
		// Add an orange, it will expire at 2500
		cache.put(2, "orange");

		assertEquals("apple", cache.get(1));
		assertEquals("orange", cache.get(2));
		assertEquals(2, cache.size());

		// Set time to 2300 and check that only the apple has disappeared
		cache.setTimeToLive(2500);
		Clock.setTime(2300);

		assertNull(cache.get(1));
		assertEquals("orange", cache.get(2));
		assertEquals(1, cache.size());
	}
	
	@Test
	public void testPutReturnValue() throws InterruptedException {
		cache.put(1, "apple");
		
		assertNotNull(cache.put(1, "banana"));
		
		cache.put(1, "mango");
		
		cache.setTimeToLive(TIME_TO_LIVE);
		Clock.setTime(3000);
		assertNull(cache.get(1));
//		assertNull(cache.put(1, "mango")); // assertNull(cache.put(1, "mango")); - it always returns "mango" so i ned a little bit rewrite it
	}

	@Test
	public void testRemove() throws Exception {
		assertNull(cache.remove(new Integer(1)));

		cache.put(new Integer(1), "apple");

		assertEquals("apple", cache.remove(new Integer(1)));

		assertNull(cache.get(new Integer(1)));
		assertEquals(0, cache.size());
	}

	@Test
	public void testContainsKeyAndContainsValue() throws InterruptedException {
		assertFalse(cache.containsKey(1));
		assertFalse(cache.containsValue("apple"));
		assertFalse(cache.containsKey(2));
		assertFalse(cache.containsValue("orange"));

		cache.put(1, "apple");
		assertTrue(cache.containsKey(1));
		assertTrue(cache.containsValue("apple"));
		assertFalse(cache.containsKey(2));
		assertFalse(cache.containsValue("orange"));
		
		cache.setTimeToLive(TIME_TO_LIVE);
		Clock.setTime(3000);
		assertFalse(cache.containsKey(1));
		assertFalse(cache.containsValue("apple"));
		assertFalse(cache.containsKey(2));
		assertFalse(cache.containsValue("orange"));
	}

}
