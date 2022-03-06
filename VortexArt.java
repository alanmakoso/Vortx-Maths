//Programmed by Alan Makoso
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class VortexArt extends JPanel{

    private static int multiplier;
    private static int modulo;
    private static byte colorChoice;
    public static void main(String args[]) {
        //User input
        System.out.println("Programmed by Alan Makoso (CC) (BY) alanmakoso@gmail.com\nInspired by Mathologer's video:\n\tLink: https://youtu.be/6ZrO90AI0c8 \n");
 
        JTextField multi = new JTextField(5);
        JTextField modu = new JTextField(5);

        ButtonGroup inputColors= new ButtonGroup();
        JRadioButton white = new JRadioButton("White", true);
        JRadioButton red = new JRadioButton("Red", false);
        JRadioButton orange = new JRadioButton("Orange", false);
        JRadioButton yellow = new JRadioButton("Yellow", false);
        JRadioButton green = new JRadioButton("Green", false);
        JRadioButton blue = new JRadioButton("Blue", false);
        JRadioButton violet = new JRadioButton("Violet", false);
        JRadioButton rainbow = new JRadioButton("Rainbow", false);
        inputColors.add(white);
        inputColors.add(red);
        inputColors.add(orange);
        inputColors.add(yellow);
        inputColors.add(green);
        inputColors.add(blue);
        inputColors.add(violet);
        inputColors.add(rainbow);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(15, 0));
        myPanel.add(new JLabel("For Tesla's 3-6-9 vortex, enter 2 for Multiplier and 9 for Modulo"));
        myPanel.add(new JLabel("Multiplier (less than modulo):"));
        myPanel.add(multi);
        myPanel.add(new JLabel("Modulo (greater than 0):"));
        myPanel.add(modu);
        myPanel.add(new JLabel("Pick a display color:"));
        myPanel.add(white);
        myPanel.add(red);
        myPanel.add(orange);
        myPanel.add(yellow);
        myPanel.add(green);
        myPanel.add(blue);
        myPanel.add(violet);
        myPanel.add(rainbow);

        int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "User Input", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            multiplier = Integer.parseInt(multi.getText());
            modulo = Integer.parseInt(modu.getText());

            if(white.isSelected()) {colorChoice = 0;}
            else if(red.isSelected()) {colorChoice = 1;}
            else if(orange.isSelected()) {colorChoice = 2;}
            else if(yellow.isSelected()) {colorChoice = 3;}
            else if(green.isSelected()) {colorChoice = 4;}
            else if(blue.isSelected()) {colorChoice = 5;}
            else if(violet.isSelected()) {colorChoice = 6;}
            else if(rainbow.isSelected()) {colorChoice = 7;}
            else {colorChoice = 0;}
        }

        //Creates GUI
        JFrame frame = new JFrame("Vortex Mathematics Generator");
        frame.setSize(480,480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        VortexArt panel = new VortexArt();
        panel.setBackground(new Color(10,15,25));
        frame.add(panel);
        frame.setVisible(true);
    }

    //returns an ArrayList containing the sequence of lines to draw
    public static ArrayList<Long> sequence(int multi, int modu) {
        //Creates blank array of size modulo
        long[] array = new long[modu];

        //assign multiplier values to array
        for (int i=0; i<array.length; i++)
            array[i] = (long)(Math.pow(multi, i));
        
        //modulo each element
        for (int i=0; i<array.length; i++)
            array[i] = (long)(array[i]%modu);
        
        //cuts array when sequence repeats
        ArrayList<Long> arrCut = new ArrayList<Long>();
        arrCut.add((long)(1)); //first element is always 1 because anything to the pwoer of 0 is 1
        for(int i=1; i<array.length; i++) {
            arrCut.add(array[i]);
            if (array[i] == arrCut.get(0))
                break;
        }
        return arrCut;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawString("Programmed by Alan Makoso (CC) (BY) alanmakoso@gmail.com", 5, getHeight()-30);
        g.drawString("Inspired by Mathologer's video",5,getHeight()-18);
        g.drawString("Link: https://youtu.be/6ZrO90AI0c8", 30, getHeight()-5);
        g.drawString("Run the program again with another combination and see what happens!", 5, 10);

        int originX = getWidth()/2, originY = getHeight()/2;
        int radius = getHeight()*3/8;
        double theta = 2*Math.PI/modulo;
        g.drawOval(originX-radius,originY-radius,radius*2,radius*2);

        //n rows (n = modulo) and 2 columns for x and y coordinate.
        int[][] points = new int[modulo][2];

        /* * Divides circle into n equal arcs and store each split  as a coordinate.
           * x value = radius * sin(theta) since theta=0 is y-axis
           * y value = radius * cos(theta) since theta=0 is x-axis
           * theta = central angle of circle relative to positive y-axis. Value = 360/n or 360/divisor
        */
        for (int i = 0; i<modulo; i++) {
                points[i][0] = (int)(originX + radius * Math.sin(theta*i)); // x-coord
                points[i][1] = (int)(originY - radius * Math.cos(theta*i)); //y-coord
        }

        //draws n points on circle, evenly spaced.
        //-4 becuase oval plotted at top left corner. Adjustment
        for (int i=0; i<modulo; i++) {
            System.out.print("x: " + points[i][0] + " y: " + points[i][1]);
            System.out.println();
            g.fillOval(points[i][0]-4, points[i][1]-4, 8, 8);
        }

        //gives the path a gradient (rainbow) color
        ArrayList<Long> path = sequence(multiplier, modulo);
        float[] hues = new float[path.size()-1];
        float hueInterval = 1.0f/hues.length;
        for(int i=0;i<hues.length;i++)
            hues[i] = hueInterval*i;

        //draws the path generated by the modulo period method
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        int currentPt, nextPt;
        for (int i=0; i<path.size()-1; i++) {
            currentPt = (int)(path.get(i).longValue()); nextPt = (int)(path.get(i+1).longValue());
        System.out.print("val: " +(path.get(i).longValue()));
        System.out.println();
        //sets the color of segments.
        switch(colorChoice){
            case 0: g.setColor(Color.WHITE); break;
            case 1: g.setColor(Color.RED); break;
            case 2: g.setColor(Color.ORANGE); break;
            case 3: g.setColor(Color.YELLOW); break;
            case 4: g.setColor(Color.GREEN); break;
            case 5: g.setColor(Color.BLUE); break;
            case 6: g.setColor(new Color(187,41,187)); break;
            case 7: g.setColor(Color.getHSBColor(hues[i], 0.65f, 0.9f)); break;
            default: g.setColor(Color.WHITE);
        }

        System.out.print("xPath: " + points[currentPt][0] + " yPath: " + points[currentPt][1]);
        System.out.println();
        System.out.print("xPathNxt: " + points[nextPt][0] + " yPathNxt: " + points[nextPt][1]);
        System.out.println();        
        g2.drawLine(points[currentPt][0],points[currentPt][1],points[nextPt][0],points[nextPt][1]);
        }
        System.out.print("Color Code: "+colorChoice);
    }
}