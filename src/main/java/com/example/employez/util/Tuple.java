package com.example.employez.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tuple<K, V, M> {
    K k;
    V v;
    M m;
}
