package PrinterSimulation;

import javax.swing.*;
import java.awt.*;

public class Test {

    public static void main(String[] args){
        Test t = new Test();
        t.run();
    }

    private JFrame frame = new JFrame("结果分析");
    private MyDrawPanel draw = new MyDrawPanel();
    private Fifo_2 fifo = new Fifo_2();
    private String bigRun = "myFile/Experiment 1/bigfirst.run";
    private String smallRun = "myFile/Experiment 1/arbitrary.run";

    public void run(){
        normalTest();
        multitudeTest();
        priorityTest();
    }

    private void normalTest(){
        fifo.simulator(bigRun, "myFile/result/bigfirst.Out");
        fifo.simulator(smallRun, "myFile/result/arbitrary.Out");
    }

    private void multitudeTest(){
        fifo.simulator(bigRun, "myFile/result/bigfirst.mOut", Fifo_2.FIFO, 3);
        fifo.simulator(smallRun, "myFile/result/arbitrary.mOut", Fifo_2.FIFO, 2);
    }

    private void priorityTest(){
        int[] a = new int[6];
        for (int i = 0; i < a.length; i++)
            a[i] = fifo.simulator(bigRun, "myFile/bigfirst.pOut", i, 1);
        draw(a);
    }

    private class MyDrawPanel extends JPanel{

        private int[] array;

        public void setArray(int[] array){
            this.array = array;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            for (int i = 1; i <= array.length; i++)
                g.fillRect(i, 0, 10,array[i]);
        }
    }

    private void draw(int[] array){
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(BorderLayout.CENTER, draw);
        draw.setArray(array);
        draw.paintComponent(draw.getGraphics());
        frame.setVisible(true);
    }
}
