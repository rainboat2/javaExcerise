package apply;

public class SparseMatrix<Type> {

    public static void main(String[] args){
        SparseMatrix<Integer> sp = new SparseMatrix<>(6, 7);
        sp.set(0, 3, 22);
        sp.set(0, 6, 15);
        sp.set(1, 1, 11);
        sp.set(1, 5, 17);
        sp.set(2, 3, -6);
        sp.set(3, 5, 39);
        sp.set(4, 0, 91);
        sp.set(5, 2, 28);
        System.out.println(sp.transpose());
    }

    private int terms;
    private Term<Type>[] termList;
    private int cols;
    private int rows;

    private class Term<Type>{

        private int col;
        private int row;
        private Type value;

        public Term(int row, int col, Type value){
            this.row = row;
            this.col = col;
            this.value = value;
        }

        public String toString(){
            return row + " " + col + " " + value;
        }
    }

    public SparseMatrix(int rows, int cols){
        this.cols = cols;
        this.rows = rows;
        termList = new Term[10];
    }

    public void set(int row, int col, Type value){
        termList[terms++] = new Term<>(row, col, value);
    }

    public SparseMatrix<Type> transpose(){
        int[] rowSize = new int[cols];
        int[] rowStart = new int[cols];

        for(int i = 0; i < terms; i++){
            Term<Type> temp = termList[i];
            rowSize[temp.col]++;
        }

        for(int i = 1; i < rowStart.length; i++){
            rowStart[i] = rowStart[i-1] + rowSize[i-1];
        }


        Term[] newTermList = new Term[10];
        for(int i = 0; i < terms; i++){
            Term<Type> before = termList[i];
            Term<Type> current = new Term<>(before.col, before.row, before.value);    //将行列互换, 进行转置操作
            newTermList[rowStart[before.col]++] = current;
        }
        SparseMatrix<Type> sp = new SparseMatrix<>(this.cols, this.rows);
        sp.terms = terms;
        sp.termList = newTermList;
        return sp;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < terms; i++){
            s += termList[i] + " \n";
        }
        return s;
    }
}
