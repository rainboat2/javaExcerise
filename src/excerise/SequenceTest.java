package excerise;

import java.util.*;
import java.io.*;

public class SequenceTest {

    public static void main(String[] args){
        for(int i = 1; i <=3; i++){
            normalTest(new File("普通测试_" + i + ".txt"));
        }
        bigDataTest(new File("bigDataTest.txt"));
    }

    public static void normalTest(File file){
        MyLinkedList<Integer> list = new MyLinkedList<>();
        int num = (int)(Math.random()*100);


        System.out.println("------添加测试-------");
        System.out.printf("任意添加%d个数\n", num);
        for(int i = 0; i < num; i++)
            list.add(i);
        System.out.printf("添加成功，目前长度为%d，数组为：%s\n", list.size(), list.toString());

        System.out.println("------删除测试-------");
        System.out.println("随机获得一个list的子串");
        MyLinkedList<Integer> sub = (MyLinkedList<Integer>) list.subList(num/4, num/2);
        System.out.println("subList: " + sub);
        System.out.println("以sublist为参数，删除原list中的子串");
        list.removeAll(sub);
        System.out.printf("删除成功，目前长度为%d，数组为：%s\n", list.size(), list.toString());

        System.out.println("------清除测试-------");
        System.out.println("开始清除");
        list.clear();
        System.out.printf("清除成功，是否为空：%b\n", list.isEmpty());

        System.out.println("------插入测试-------");
        num = (int)(Math.random()*30) + 5;
        System.out.printf("任意插入%d个数\n", num);
        for(int i = 0; i < num; i++){
            int index = (int)(Math.random()*list.size());
            list.add(index, i);
        }
        System.out.printf("插入成功，目前长度为%d，数组为：%s\n", list.size(), list.toString());

        System.out.println("------get&set------");
        System.out.println("测试前：" + list);
        num = list.size()/2;
        System.out.printf("测试将list中所有大于%s的数变成零\n", num);
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) > num) list.set(i, 0);
        }
        System.out.println("改变成功，现在数组为" + list);

        System.out.println("------查找测试------");
        System.out.println("找到并删除所有为0的项");
        while (true){
            int index = list.indexOf(0);
            if (index != -2)  list.remove(index);
            else              break;
        }
        System.out.println("测试成功，数组变为：" + list);

        System.out.println("------拷贝测试------");
        System.out.println("将数组拷贝一份并添加到原数组的后面");
        MyLinkedList<Integer> temp = list.clone();
        System.out.println(temp);
        list.addAll(temp);
        System.out.println("测试成功，数组为：" + list);

        System.out.println("-----迭代器测试------");
        System.out.println("使用listIterator来对SequenceList进行倒序遍历并打印");
        ListIterator<Integer> lit = list.listIterator(list.size());
        while(lit.hasPrevious()){
            System.out.print(lit.previous() + "  ");
        }
        System.out.println();
        System.out.println("使用listIterator来对SequenceList进行遍历并打印");
        lit = list.listIterator();
        while (lit.hasNext()){
            System.out.print(lit.next() + "  ");
        }
        System.out.println();
        System.out.println("“使用Iterator来对sequenceList进行遍历并打印");
        Iterator<Integer> it = list.listIterator();
        while (it.hasNext()){
            System.out.print(it.next() + "  ");
        }
    }

    public static void bigDataTest(File file) {
        MyLinkedList<String> list = new MyLinkedList<>();
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            FileReader in = new FileReader(new File("bigData.txt"));
            FileOutputStream out = new FileOutputStream(file);
            writer  = new PrintWriter(out);
            reader = new BufferedReader(in);
            String line = null;
            while((line = reader.readLine()) != null){
                String[] a = line.split("[,.;!-\" ]");
                list.addAll(Arrays.asList(a));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("将文本文件中的单词取出并放在list中，统计单词数量");
        boolean flag = true;
        while(flag){
            flag = list.remove("");          //删除标点符号与空格之间的空白字符（无字符）
        }
        //删除所有的数字
        list.removeAll(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        System.out.printf("单词数量为%s\n", list.size());
        System.out.flush();
        System.out.close();
    }
}
