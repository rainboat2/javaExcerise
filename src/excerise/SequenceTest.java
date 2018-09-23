package excerise;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.io.*;

public class SequenceTest {



    public static void main(String[] args){
        for(int i = 1; i <=3; i++){
            normalTest(new File("普通测试_" + i + ".txt"));
        }
    }

    public static void normalTest(File file){
        SequenceList<Integer> list = new SequenceList<>();
        int num = (int)(Math.random()*100);

        PrintWriter writer = null;
        try {
            FileOutputStream out = new FileOutputStream(file);
            writer = new PrintWriter(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        writer.println("------添加测试-------");
        writer.printf("任意添加%d个数\n", num);
        for(int i = 0; i < num; i++)
            list.add(i);
        writer.printf("添加成功，目前长度为%d，数组为：%s\n", list.size(), list.toString());

        writer.println("------删除测试-------");
        writer.println("随机获得一个list的子串");
        SequenceList<Integer> sub = (SequenceList<Integer>) list.subList(num/4, num/2);
        writer.println("subList: " + sub);
        writer.println("以sublist为参数，删除原list中的子串");
        list.removeAll(sub);
        writer.printf("删除成功，目前长度为%d，数组为：%s\n", list.size(), list.toString());

        writer.println("------清除测试-------");
        writer.println("开始清除");
        list.clear();
        writer.printf("清除成功，是否为空：%b\n", list.isEmpty());

        writer.println("------插入测试-------");
        num = (int)(Math.random()*30) + 5;
        writer.printf("任意插入%d个数\n", num);
        for(int i = 0; i < num; i++){
            int index = (int)(Math.random()*list.size());
            list.add(index, i);
        }
        writer.printf("插入成功，目前长度为%d，数组为：%s\n", list.size(), list.toString());

        writer.println("------get&set------");
        writer.println("测试前：" + list);
        num = list.size()/2;
        writer.printf("测试将list中所有大于%s的数变成零\n", num);
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) > num) list.set(i, 0);
        }
        writer.println("改变成功，现在数组为" + list);

        writer.println("------查找测试------");
        writer.println("找到并删除所有为0的项");
        while (true){
            int index = list.indexOf(0);
            if (index != -1)  list.remove(index);
            else              break;
        }
        writer.println("测试成功，数组变为：" + list);

        writer.println("------拷贝测试------");
        writer.println("将数组拷贝一份并添加到原数组的后面");
        SequenceList<Integer> temp = list.clone();
        list.addAll(temp);
        writer.println("测试成功，数组为：" + list);

        writer.println("-----迭代器测试------");
        writer.println("使用listIterator来对SequenceList进行倒序遍历并打印");
        ListIterator<Integer> lit = list.listIterator(list.size());
        while(lit.hasPrevious()){
            writer.print(lit.previous() + "  ");
        }
        writer.println();
        writer.println("使用listIterator来对SequenceList进行遍历并打印");
        lit = list.listIterator();
        while (lit.hasNext()){
            writer.print(lit.next() + "  ");
        }
        writer.println();
        writer.println("“使用Iterator来对sequenceList进行遍历并打印");
        Iterator<Integer> it = list.listIterator();
        while (it.hasNext()){
            writer.print(it.next() + "  ");
        }
        writer.flush();
        writer.close();
    }


}
