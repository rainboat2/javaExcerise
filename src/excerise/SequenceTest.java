package excerise;

public class SequenceTest {

    public static void main(String[] args){
        SequenceList<Integer> list = new SequenceList<>();
        int num = (int)(Math.random()*100);

        System.out.println("------添加测试-------");
        System.out.printf("任意添加%d个数\n", num);
        for(int i = 0; i < num; i++)
            list.add(i);
        System.out.printf("添加成功，目前长度为%d，数组为：%s\n", list.size(), list.toString());

        System.out.println("------删除测试-------");
        num = num/4;
        System.out.printf("任意删除%d个数\n", num);
        for(int i = num; i > 0; i--){
            list.remove(i);
        }
        System.out.printf("删除成功，目前长度为%d，数组为：%s\n", list.size(), list.toString());

        System.out.println("------清除测试-------");
        System.out.println("开始清除");
        list.clearAll();
        System.out.printf("清除成功，是否为空：%b\n", list.isEmpty());

        System.out.println("------插入测试-------");
        num = (int)(Math.random()*30);
        System.out.printf("任意插入%d个数\n", num);
        for(int i = 0; i < num; i++){
            int index = (int)(Math.random()*list.size());
            list.insert(index, i);
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
            if (index != -1)  list.remove(index);
            else              break;
        }
        System.out.println("测试成功，数组变为：" + list);

        System.out.println("------拷贝测试------");
        System.out.println("将数组拷贝一份并添加到原数组的后面");
        SequenceList<Integer> temp = list.clone();
        list.addAll(temp);
        System.out.println("测试成功，数组为：" + list);
    }
}
