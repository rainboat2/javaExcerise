package two;

import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] a = new int[]{};

        for (int i = 48; i < 58; i++){
            System.out.println((char)i);
        }
    }

    private boolean[][] rowMark;
    private boolean[][] columnMark;
    private boolean[][] cellMark;

    public void solveSudoku(char[][] board) {
        if (isValidSudoku(board)) return;

    }


    public boolean isValidSudoku(char[][] board) {
        rowMark = new boolean[9][10];  //数字从1开始，故每行长度为10
        columnMark = new boolean[9][10];
        cellMark = new boolean[9][10];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                char c = board[i][j];
                if (c == '.') continue;
                if (isMarked(i, j, c-48))  return false;
                else                             mark(i, j, c-48);
            }
        }
        return true;
    }

    private boolean isMarked(int row, int col, int num){
        int cellIndex = (row/3)*3 + col/3;
        return rowMark[row][num] || columnMark[col][num] || cellMark[cellIndex][num];
    }

    private void mark(int row, int col, int num){
        int cellIndex = (row/3)*3 + col/3;
        rowMark[row][num] = true;
        columnMark[col][num] = true;
        cellMark[cellIndex][num] = true;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }

    public String toString(){
        return val+"";
    }
}