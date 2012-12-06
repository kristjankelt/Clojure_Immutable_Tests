package me.test.util;

import java.util.Map;

public final class MapAdder<K, V> {
	
	private final Map<K, V> map;
	
	private MapAdder(Map<K, V> map) {
		this.map = map;
	}

	public static <K, V> MapAdder<K, V> start(Map<K, V> map) {
		return new MapAdder<K, V>(map);
	}
	
	public MapAdder<K, V> add(K k, V v) {
		
		map.put(k, v);
		
		return this;
	}
	
	public Map<K, V> end() {
		return map;
	}
}
