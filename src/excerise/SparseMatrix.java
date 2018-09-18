package excerise;

public class SparseMatrix<Type> {

    public static void main(String[] args){
        SparseMatrix<Integer> sp = new SparseMatrix<>(7, 6);
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

    private Term[] termList;
    private int cols;
    private int rows;
    private int terms;

    private class Term{
        public int col;
        public int row;
        public Type value;

        public Term(int col, int row, Type value){
            this.col = col;
            this.row = row;
            this.value = value;
        }

        public Term(){}

        public String toString(){
            return col + " " + row + " " + value;
        }
    }

    public SparseMatrix(int cols, int rows){
        this.cols = cols;
        this.rows = rows;
        termList = new Term[10];
    }

    public void set(int row, int col, Type value){
        termList[terms++] = new Term(col, row, value);
    }

    public SparseMatrix<Type> transpose(){
        int[] rowSize = new int[cols];
        int[] rowStart = new int[cols];

        for(int i = 0; i < termList.length; i++){
            rowSize[termList[i].col]++;
        }

        for(int i = 1; i < termList.length; i++){
            rowStart[i] = rowStart[i-1] + rowSize[i-1];
        }

        Term[] newTerms = new Term[10];
        for(int i = 0; i < terms; i++){
            Term temp = termList[i];
            Term newTerm = new Term(temp.row, temp.col, temp.value);  //将原坐标的行列替换完成转置
            newTerms[rowStart[temp.col]++] = newTerm;
        }
        SparseMatrix<Type> sp = new SparseMatrix<>(this.rows, this.cols);
        sp.termList = newTerms;
        sp.terms = this.terms;
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
