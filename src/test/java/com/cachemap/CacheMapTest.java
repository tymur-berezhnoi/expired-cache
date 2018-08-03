package com.cachemap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheMapTest {

	private CacheMap<Integer, String> cache;
	private final static long TIME_TO_LIVE = 10;

	@BeforeEach
	void setUp() {
		cache = new ExpireCacheMap<>();
	}
	
	@Test
	void testExpiry() throws InterruptedException {
		cache.put(1, "apple");
		
		assertEquals("apple", cache.get(1));
		assertFalse(cache.isEmpty());
		
		cache.setTimeToLive(TIME_TO_LIVE);
		Clock.setTime(50);
		
		assertNull(cache.get(1));
		assertTrue(cache.isEmpty());
	}

	@Test
	void testSize() throws Exception {
		assertEquals(0, cache.size());
		
		cache.put(1, "apple");
		assertEquals(1, cache.size());
		
		cache.setTimeToLive(TIME_TO_LIVE);
		Clock.setTime(50);
		
		assertEquals(0, cache.size());
	}

	@Test
	void testPartialExpiry() throws Exception {
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
	void testPutReturnValue() throws InterruptedException {
		cache.put(1, "apple");
		
		assertNotNull(cache.put(1, "banana"));
		
		cache.put(1, "mango");
		
		cache.setTimeToLive(TIME_TO_LIVE);
		Clock.setTime(3000);
		assertNull(cache.get(1));
		assertNull(cache.put(1, "mango")); // assertNull(cache.put(1, "mango")); - it always returns "mango" so i ned a little bit rewrite it
	}

	@Test
	void testRemove() {
		assertNull(cache.remove(1));

		cache.put(1, "apple");

		assertEquals("apple", cache.remove(1));

		assertNull(cache.get(1));
		assertEquals(0, cache.size());
	}

	@Test
	void testContainsKeyAndContainsValue() throws InterruptedException {
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
