package com.pw29_30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

   public static List<String> withStream(String str) {
      List<String> res = new ArrayList<>();

      Stream
         .of(str.split("[\\p{Punct}\\s]+"))
         .collect(
            Collectors.groupingBy(String::toLowerCase, Collectors.counting())
         )
         .entrySet()
         .stream()
         .sorted(
            (elem1, elem2) -> {
               if (elem1.getValue().equals(elem2.getValue())) {
                  return elem1.getKey().compareTo(elem2.getKey());
               } else {
                  return elem2.getValue().compareTo(elem1.getValue());
               }
            }
         )
         .limit(10)
         .forEach(elem -> res.add(elem.getKey()));

      return res;
   }

   public static List<String> withoutStream(String str) {
      str = str.toLowerCase();
      List<String> words = Arrays.asList(str.split("[\\p{Punct}\\s]+"));
      Map<String, Integer> wordsCounter = new HashMap<>();

      // Карта (слово => кол-во вхождений)
      Integer elem;
      for (String word : words) {
         elem = wordsCounter.get(word);
         if (elem != null) {
            wordsCounter.put(word, elem + 1);
         } else {
            wordsCounter.put(word, 1);
         }
      }

      // Сет из вхождений слов
      List<Integer> amounts = new ArrayList<>();
      for (Integer value : wordsCounter.values()) {
         if (!amounts.contains(value)) {
            amounts.add(value);
         }
      }
      amounts.sort(
         new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
               if (o1 == o2) return 0;
               if (o1 > o2) return -1;
               return 1;
            }
         }
      );

      // Массив массивов по ко-ву вхождений (на основе прошлого сета)
      List<List<String>> temp = new ArrayList<>();
      for (int i = 0; i < amounts.size(); i++) {
         for (String key : wordsCounter.keySet()) {
            if (wordsCounter.get(key) == amounts.get(i)) {
               if (temp.size() == i) {
                  temp.add(new ArrayList<>());
               }

               temp.get(i).add(key);
            }
         }
      }

      // Сортировка вложенных массивов прошлого и выборка первых 10 элементов из всех массивов
      List<String> res = new ArrayList<>();
      for (List<String> list : temp) {
         list.sort(null);
         for (String word : list) {
            if (res.size() == 10) {
               return res;
            } else {
               res.add(word);
            }
         }
      }

      return res;
   }

   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in, "Cp866");
      String str = scanner.nextLine();
      scanner.close();

      long start = System.currentTimeMillis();
      System.out.println("\n" + withoutStream(str));
      System.out.println(
         "Duration without Stream API: " +
         (System.currentTimeMillis() - start) +
         "ms\n"
      );
      start = System.currentTimeMillis();
      System.out.println(withStream(str));
      System.out.println(
         "Duration with Stream API: " +
         (System.currentTimeMillis() - start) +
         "ms\n"
      );
   }
}
