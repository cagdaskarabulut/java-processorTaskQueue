package com.karabulut.javaprocessortaskqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class JavaProcessorTaskQueueApplication {

    private static Integer currentCycle;
    private static Integer lastActiveCycle;

    public static void main(String[] args) {
        SpringApplication.run(JavaProcessorTaskQueueApplication.class, args);

//        System.out.println();
//        List<Integer> tasks = new ArrayList<>(List.of(2,6,7,1,4));
//        getActiveTaskIndexAtGivenCycle(tasks,6);
//
//        System.out.println();
//        List<Integer> tasks2 = new ArrayList<>(List.of(3,4,4,4,20,7,5));
//        getActiveTaskIndexAtGivenCycleWithDependencies(tasks2,23);

    }

    public static int getActiveTaskIndexAtGivenCycle(List<Integer> tasks,Integer cycle){
        currentCycle=0;
        lastActiveCycle = 0;

        //find lasted active tasks at given cycle position
        List<Integer> result = tasks.stream()
                .sorted()
                .takeWhile(n -> cycle > currentCycle)
                  .map(n-> {
                      lastActiveCycle = n;
                      return currentCycle += n;
                  })
                    .collect(Collectors.toList());

        return lastActiveCycle;
    }

    public static int getActiveTaskIndexAtGivenCycleWithDependencies(List<Integer> tasks,Integer cycle){
        currentCycle=0;
        lastActiveCycle = 0;

        Set<Integer> items = new HashSet<>();

        //find dublicate List
        List dublicateList = tasks.stream()
                .filter(n -> !items.add(n))
                .collect(Collectors.toList());

        //find distinct List
        Map<Integer, Integer> tasksDistictMap = tasks.stream()
                .distinct()
                .collect(Collectors.toMap(Integer::intValue, Integer::intValue));

        //sum duplicate values into same keys
        for (Map.Entry<Integer, Integer> entry : tasksDistictMap.entrySet()) {
            int total = 0;
            if(dublicateList.contains(entry.getKey())){
                for (int i = 0; i <dublicateList.size(); i++) {
                    if(dublicateList.get(i) == entry.getKey()){
                        total+=entry.getValue();
                    }
                }
                tasksDistictMap.put(entry.getKey(),total+entry.getKey());
                dublicateList.removeIf(x-> x ==entry.getKey());
            }
        }

        //find lasted active tasks at given cycle position
        List<Integer> result = tasksDistictMap.keySet().stream()
                .sorted()
                .takeWhile(n -> cycle > currentCycle)
                .map(n-> {
                    lastActiveCycle = n;
                    return currentCycle += tasksDistictMap.get(n);
                })
                .collect(Collectors.toList());

        return lastActiveCycle;
    }
}
