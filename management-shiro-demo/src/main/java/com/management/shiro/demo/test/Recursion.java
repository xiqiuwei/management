package com.management.shiro.demo.test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author xiqiuwei
 * @Date Created in 20:45 2020/1/16
 * @Description
 * @Modified By:
 */
public class Recursion {
    public static void mkdir(Integer[][] array) {
        // TODO Auto-generated method stub
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.err.print((array[i])[j]);
            }
            System.err.println();
        }
        // 将二维数组转换成map类型泛型1是所有父节点，泛型2则是所有子目录
        Map<Integer, List<Integer>> parentAndChildDir = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < array.length; i++) {
            // 包含父节点就将当前的子目录作为父节点的value
            if (parentAndChildDir.containsKey((array[i])[0])) {
                parentAndChildDir.get((array[i])[0]).add((array[i])[1]);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add((array[i])[1]);
                parentAndChildDir.put((array[i])[0], list);
            }
        }
        // 取出0索引的文件夹并且去重
        List<Integer> parent = Stream.of(array).map(arr -> arr[0]).distinct().collect(Collectors.toList());
        // 取出1索引的文件夹
        List<Integer> child = Stream.of(array).map(arr -> arr[1]).distinct().collect(Collectors.toList());
        // 取一级目录，取交集并且移除
        parent.removeAll(child);
        List<String> result = new ArrayList<>();
        for (Integer integer : parent) {
            // 递归参数
            // 1.result 当前结果集；
            // 2.parentAndChildDir 所有父子目录；
            // 3.integer 当前递归的目录；
            // 4.目录输出等级 例如根目录0级，
            recursion(result, parentAndChildDir, integer, 0);
        }
        for (String s : result) {
            System.err.println(s);
        }
    }
    /**
     * @param result   结果集
     * @param allDir   所有的文件
     * @param parentDir 父文件夹
     *  递归当前父文件夹，直到子目录下无下级目录if隐式结束条件
     *  level参数就是为了当前递归目录拼接空格而定义，相当于一个空格拼接计数器
     */
    private static void recursion(List<String> result, Map<Integer, List<Integer>> allDir, Integer parentDir, int level) {
        String s = addResult(level) + parentDir;
        result.add(s);
        List<Integer> list = allDir.get(parentDir);
        // 有子文件夹
        if (null != list) {
            for (Integer childDir : list) {
                recursion(result, allDir, childDir, level + 1);
            }
        }
    }
    // 构造空格封装，根据level等级控制空格个数
    private static String addResult(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Integer[][] array = new Integer[][]{{8, 3}, {3, 2}, {3, 7}, {6, 4}, {4, 5}};
        mkdir(array);
    }
}
