package exercise;

import java.util.Arrays;

public class EightQueen {

    public static void main(String[] args) {
        new EightQueen();
    }

    private int[] queen = new int[8];

    private boolean[] columnMark = new boolean[8];          // 记录每列的有效性
    private boolean[] leftDiagonalMark = new boolean[15];   // 记录每条左对角线的有效性
    private boolean[] rightDiagonalMark = new boolean[15];  // 记录每条右对角线的有效性

    private int count = 0;

    public EightQueen(){
        fill(0);
        System.out.println(count);
    }

    // 未被标记则为有效，即下列三项全为 false时返回 true
    private boolean isValid(int row, int column){
        int a = row + column;
        int b = row - column + 7;
        return !(leftDiagonalMark[a] || rightDiagonalMark[b] || columnMark[column]);
    }

    /*
     * 该方法用于将一个点对应的列、左右对角线标记为无效或有效
     *
     * 对角线上的点满足一次方程，如下：
     * i + j = a (a相等的点在同一条左对角线上）
     * i - j = b (b相等的点在同一条右对角线上)
     */
    private void mark(int row, int column, boolean valid){
        if (valid)  queen[row] = column;
        else        queen[row] = -1;
        columnMark[column] = valid;
        int a = row + column;
        int b = row - column + 7;
        leftDiagonalMark[a] = valid;
        rightDiagonalMark[b] = valid;
    }

    /*
     * 回溯法求八皇后问题
     */
    private void fill(int row){
        if (row == 8) show();
        else{
            for (int i = 0; i < queen.length; i++){
                if (isValid(row, i)){
                    mark(row, i, true);
                    fill(row+1);
                    mark(row, i, false);
                }
            }
        }
    }

    private void show(){
        count++;
        System.out.println(Arrays.toString(queen));
        for (int i = 0; i < queen.length; i++){
            for (int j = 0; j < queen.length; j++){
                if (queen[i] == j)    System.out.print(" * ");
                else                  System.out.print(" . ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
