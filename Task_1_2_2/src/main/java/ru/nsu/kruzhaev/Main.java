package ru.nsu.kruzhaev;

import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        HashTable<String, Integer> htable = new HashTable<>();
        System.out.println(htable.put("aboba", 15));

        System.out.println(htable.put("oao", 128));

        System.out.println(htable.put("qwertyui", 12345678));

        System.out.println(htable.put("qwertyui", 156));

        System.out.println(htable.get("qwertyui"));

        System.out.println(htable.containsKey("qwertyui"));

        System.out.println(htable.remove("qwertyui", 156));

        System.out.println(htable.containsKey("qwertyui"));

        for (HashNode<String, Integer> node : htable) {
            System.out.println("Key: " + node.key + ", value: " + node.value);
        }
    }
}
