package ru.rodin.task3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) {

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        Map<String, Long> result = Arrays.stream(RAW_DATA)
                .filter(Objects::nonNull)
                .filter(el -> el.getName() != null)
                .distinct()
                .sorted(Comparator.comparing(Person::getId))
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting()));

        System.out.println("task_1");
        System.out.println();


        for (Map.Entry<String, Long> item : result.entrySet()) {
            System.out.println("Key: " + item.getKey() + "\n" + "Value:" + item.getValue());
        }

        System.out.println();

        System.out.println("----------------------------------------------");
        System.out.println("task_2");

        System.out.println();

 /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */

        taskTwo(new int[]{3, 4, 2, 7},10);

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("task_3");
        System.out.println();
        /*
        Task3
            Реализовать функцию нечеткого поиска

                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
         */

        fuzzySearch("car", "ca6$$#_rtwheel"); // true
        fuzzySearch("cwhl", "cartwheel"); // true
        fuzzySearch("cwhee", "cartwheel"); // true
        fuzzySearch("cartwheel", "cartwheel"); // true
        fuzzySearch("cwheeel", "cartwheel"); // false
        fuzzySearch("lw", "cartwheel"); // false


    }

    public static void taskTwo(int[] array, int number) {

        String result = "";

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = array.length - 1; j > 0; j--) {
                if (array[i] + array[j] == number) {
                    result = "[" + array[i] + ", " + array[j] + "]";
                }
            }
        }

        System.out.println(result);
    }

    public static void fuzzySearch(String originalLine, String searchLine) {

        String[] originalLineArray = originalLine.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "").split("");

        String[] searchLineArray = searchLine.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "").split("");

        StringBuilder result = new StringBuilder();

        for (int j = 0; j < searchLineArray.length; j++) {
            for (int i = 0; i < originalLineArray.length; i++) {
                if (originalLineArray[i].equals(searchLineArray[j])) {
                    result.append(searchLineArray[j]);
                    originalLineArray[i] = "";
                    searchLineArray[j] = "";
                }
            }
            searchLineArray[j] = "";
        }
        System.out.println(result.toString().equals(originalLine));
    }
}
