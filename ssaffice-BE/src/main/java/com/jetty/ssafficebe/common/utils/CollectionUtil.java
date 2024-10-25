package com.jetty.ssafficebe.common.utils;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtil {

    private CollectionUtil() {
    }

    public static <F, T> List<T> transform(
            Collection<F> fromList, Function<? super F, ? extends T> function) {
        ArrayList<T> list = new ArrayList<>();
        if (fromList != null) {
            fromList.forEach(src -> list.add(function.apply(src)));
        }
        return list;
    }

    public static <F, T> List<T> transform(
            Iterable<F> fromList, Function<? super F, ? extends T> function) {
        ArrayList<T> list = new ArrayList<>();
        if (fromList != null) {
            fromList.forEach(src -> list.add(function.apply(src)));
        }
        return list;
    }

    public static <F, T> List<T> transformDistinct(
            Collection<F> fromList, Function<? super F, ? extends T> targetFunction) {
        List<T> list = new ArrayList<>();
        if (fromList != null) {
            list =
                    fromList.stream()
                            .map(targetFunction)
                            .filter(x -> !StringUtils.isEmpty(x))
                            .distinct()
                            .collect(Collectors.toList());
        }
        return list;
    }

    public static <F, T, S> void removeNoneMatch(
            Collection<F> srcList,
            Collection<T> matchList,
            Function<? super F, ? extends S> srcKeyFunction,
            Function<? super T, ? extends S> matchKeyFunction) {
        Set<S> keySet = new HashSet<>();
        for (T t : matchList) {
            S key = matchKeyFunction.apply(t);
            if (key != null) {
                keySet.add(key);
            }
        }
        srcList.removeIf(f -> !keySet.contains(srcKeyFunction.apply(f)));
    }

    public static <F, T, S> List<F> findNoneMatch(
            Collection<F> srcList,
            Collection<T> matchList,
            Function<? super F, ? extends S> srcKeyFunction,
            Function<? super T, ? extends S> matchKeyFunction) {
        Set<S> keySet = new HashSet<>();
        for (T t : matchList) {
            S key = matchKeyFunction.apply(t);
            if (key != null) {
                keySet.add(key);
            }
        }
        return srcList.stream()
                      .filter(f -> !keySet.contains(srcKeyFunction.apply(f)))
                      .collect(Collectors.toList());
    }

    public static <F, T, S> void deleteOrUpdateOrInsert(
            Collection<F> targetList, // 원본
            Collection<T> srcList, // 업데이트 대상
            Function<F, S> targetKeyFunction, // 원본 Key
            Function<T, S> srcKeyFunction, // 업데이트 대상 Key
            Consumer<Collection<F>> deleteFunction,
            BiConsumer<F, T> updateFunction,
            Function<T, F> insertFunction) {

        // Update Or Save하고자 하는 Collection을 Map으로 mapping
        Map<S, T> srcMap = new HashMap();
        for (T t : srcList) {
            S key = srcKeyFunction.apply(t);
            if (key != null) {
                srcMap.put(key, t);
            }
        }

        if (deleteFunction != null) {
            // 원본에서 업데이트 할 대상이 없는 건은 삭제
            Collection<F> deleteList =
                    targetList.stream()
                              .filter(f -> !srcMap.containsKey(targetKeyFunction.apply(f)))
                              .collect(Collectors.toList());

            deleteFunction.accept(deleteList);
        }

        // 불필요한 Target 삭제
        targetList.removeIf(f -> !srcMap.containsKey(targetKeyFunction.apply(f)));

        // 원본 Collection을 Map으로 mapping
        Map<S, F> targetMap = new HashMap();
        for (F f : targetList) {
            S key = targetKeyFunction.apply(f);
            if (key != null) {
                targetMap.put(key, f);
            }
        }

        for (T t : srcList) {
            F f = targetMap.get(srcKeyFunction.apply(t));
            if (f != null) {
                if (updateFunction != null) {
                    updateFunction.accept(f, t);
                }
            } else {
                if (insertFunction != null) {
                    F result = insertFunction.apply(t);
                    targetList.add(result);
                    targetMap.put(targetKeyFunction.apply(result), result);
                }
            }
        }
    }

    public static <T> ArrayList<T> generateArrayListOf(T t) {
        ArrayList<T> list = new ArrayList<>();
        list.add(t);
        return list;
    }
}
