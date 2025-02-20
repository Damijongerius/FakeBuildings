package com.dami.fakeBuildings.Utils;

public record KeyValuePair<K, V>(K key, V value) {

    public static <K, V> KeyValuePair<K, V> of(K key, V value) {
        return new KeyValuePair<>(key, value);
    }

}
