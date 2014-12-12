/*
 * Copyright (c) 2012-2014 Microsoft Mobile.  All Rights Reserved.
 *
 * This file is based on or incorporates material from Apache Harmony
 * http://harmony.apache.org (collectively, “Third Party Code”). Microsoft Mobile
 * is not the original author of the Third Party Code. The original copyright
 * notice and the license, under which Microsoft Mobile received such Third Party
 * Code, are set forth below.
 *
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jsimple.util;

import jsimple.unit.UnitTest;

import org.junit.Test;

public class AbstractMapTest extends UnitTest {

	@Test
	public void testKeySet() {
		Map<Object, Object> map1 = new HashMap<Object, Object>(0);
		assertSame("HashMap(0)", map1.keySet(), map1.keySet());

		Map<Object, Object> map2 = new HashMap<Object, Object>(10);
		assertSame("HashMap(10)", map2.keySet(), map2.keySet());
	}

	@Test
	public void testClear() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.clear();
		assertTrue(map.isEmpty());
	}

	@Test
	public void testValues() {
		Map<Object, Object> map1 = new HashMap<Object, Object>(0);
		assertSame("HashMap(0)", map1.values(), map1.values());

		Map<Object, Object> map2 = new HashMap<Object, Object>(10);
		assertSame("HashMap(10)", map2.values(), map2.values());
	}

	@Test
	public void testPutAll() {
		HashMap<String, String> hm1 = new HashMap<String, String>();
		HashMap<String, String> hm2 = new HashMap<String, String>();

		hm2.put("this", "that");
		hm1.putAll(hm2);
		assertEquals("Should be equal", hm1, hm2);
	}

	@Test
	public void testEqualsWithNullValues() {
		Map<String, String> a = new HashMap<String, String>();
		a.put("a", null);
		a.put("b", null);

		Map<String, String> b = new HashMap<String, String>();
		a.put("c", "cat");
		a.put("d", "dog");

		assertFalse(a.equals(b));
		assertFalse(b.equals(a));
	}

	@Test
	public void testNullsOnViews() {
		Map<String, String> nullHostile = new HashMap<String, String>();

		nullHostile.put("a", "apple");
		testNullsOnView(nullHostile.entrySet());

		nullHostile.put("a", "apple");
		testNullsOnView(nullHostile.keySet());

		nullHostile.put("a", "apple");
		testNullsOnView(nullHostile.values());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void testNullsOnView(Collection<?> view) {
		assertFalse(view.contains(null));

		assertFalse(view.remove(null));

		HashSet setOfNull = new HashSet();
		setOfNull.add(null);
		assertFalse(view.equals(setOfNull));
		assertFalse(view.removeAll(setOfNull));
		assertTrue(view.retainAll(setOfNull)); // destructive
	}
}
