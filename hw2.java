package com.company;

import java.util.*;


public class Main {

    public static <T> Collection<T> removeDuplicates(Collection<T> collection) {
        return new HashSet<>(collection);
    }

    public static <K, V> Map<V, Collection<K>> invertMap(Map<K, V> map) {
        Map<V, Collection<K>> res = new HashMap<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            K key = (K) pair.getKey();
            V value = (V) pair.getValue();
            if (!res.containsKey(value)) res.put(value, new ArrayList());
            res.get(value).add(key);
            it.remove();
        }
        return res;
    }

    public static String getWinner(String[] players) {
        String winnerName = "П̸̜̼̤͇̬̼̜̖̫̮̋̀͐͑͊̈́̋͘͝͝ͅа̵̙̳̰̖̬̗̠͉͙̻̜̒̍̿̀̓͑̇̈́͒̅̑͘ш̸̧͐͆̉̆͘к̴̹̹̂̋̾̽̿̚а̴̢̳̫̣̱͎̘̦̩̼́̒̎̽͐̑̏̄̌̕̚͝";
        int maxScore = -666;
        HashMap<String, Integer> scoreNameMap = new HashMap<>();

        for (String playerScoreString : players) {
            String[] playerAndScoreSplit = playerScoreString.split("\\s+");

            String name = playerAndScoreSplit[0];

            int previousScore = scoreNameMap.getOrDefault(name, 0);
            int newScore = Integer.parseInt(playerAndScoreSplit[1]);
            int score = previousScore + newScore;

            scoreNameMap.put(name, score);

            if (maxScore < score) {
                maxScore = score;
                winnerName = name;
            }
        }
        return winnerName;
    }

    public static void main(String[] args) {
        System.out.println("Задание 1");
        List<String> randomNames = new ArrayList<>();
        randomNames.add("Лидия Аркадьевна Бубликова");
        randomNames.add("Лидия Аркадьевна Бубликова");
        randomNames.add("Иван Михайлович Серебряков");
        randomNames.add("Дональд Джон Трамп");

        System.out.println("Было так");
        System.out.println(randomNames);

        System.out.println("Стало так");
        System.out.println(removeDuplicates(randomNames));

        System.out.println("Задание 2");
        HashMap<Integer, String> passportsAndNames = new HashMap<>();

        passportsAndNames.put(212133, "Лидия Аркадьевна Бубликова");
        passportsAndNames.put(666666, "Лидия Аркадьевна Бубликова");
        passportsAndNames.put(162348, "Иван Михайлович Серебряков");
        passportsAndNames.put(8082771, "Дональд Джон Трамп");

        System.out.println("Было так");
        System.out.println(passportsAndNames);

        System.out.println("Стало так");
        System.out.println(invertMap(passportsAndNames));

        System.out.println("Задание 3");
        String[] input = new String[]{
                "Ivan 5", "Petr 3", "Alex 10", "Petr 8", "Ivan 6",
                "Alex 5", "Ivan 1", "Petr 5", "Alex 1"
        };
        System.out.println("Победитель - " + getWinner(input));
    }
}
