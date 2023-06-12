        //________      ___    ___ ________  ________      
        // |\   __  \    |\  \  /  /|\   __  \|\   ___  \    
        // \ \  \|\  \   \ \  \/  / | \  \|\  \ \  \\ \  \   
        //  \ \   __  \   \ \    / / \ \   __  \ \  \\ \  \  
        //   \ \  \ \  \   \/  /  /   \ \  \ \  \ \  \\ \  \ 
        //    \ \__\ \__\__/  / /      \ \__\ \__\ \__\\ \__\
        //     \|__|\|__|\___/ /        \|__|\|__|\|__| \|__|
        //   
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.io.*;
import java.net.*;
import java.util.List;
import javax.swing.Timer;
import java.awt.event.*;
//import 2d graphics
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//sounds
import java.io.File;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
public class ClientScreen extends JPanel implements ActionListener, MouseListener {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    boolean ran;
    private String hostName = "localhost";
    private int portNumber = 1025;
    private int clientNumber;
    private JButton start;
    private JButton instructions;
    private JButton back;
    private JButton restart;
    private Color baseColors[];
    private String status;
    private int points; 
    private int round;
    int j;
    private int correct_x;
    private int correct_y;

    private MyArrayList<Color> color_grid;
    private MyArrayList<Color> r1color_grid;
    private MyArrayList<Color> r2color_grid;
    private MyArrayList<Color> r3color_grid;
    private int r1correct_x;
    private int r1correct_y;
    private int r2correct_x;
    private int r2correct_y;
    private int r3correct_x;
    private int r3correct_y;
     MyArrayList<Color> color_grid_fixed;
    private int num_tiles;
    private int num_row;
    private int num_col;

    private int correct_color;
    private boolean ran1;
    private int width;
    private int height;
    private int margin;
    Timer timer;
    int timeElapsed;
    
    private int muligan;

    public ClientScreen() {
        timeElapsed=0;
        color_grid_fixed = new MyArrayList<Color>();
        points=2;
        this.setLayout(null);
        num_col = 3;
        num_row = 3;
        r1color_grid = new MyArrayList<Color>();
        r2color_grid = new MyArrayList<Color>();
        r3color_grid = new MyArrayList<Color>();
        clientNumber = 0;
        //intialize the color grids to random colors
        for (int i = 0; i < 9; i++) {
            r1color_grid.add(Math.random() < 0.5 ? Color.black : Color.white);
        }
        for (int i = 0; i < 16; i++) {
            r2color_grid.add(Color.black);
        }
        for (int i = 0; i < 25; i++) {
            r3color_grid.add(Color.black);
        }
        for(int i=0;i<9;i++){
            r1color_grid.set(i,
                    new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
        for(int i=0;i<16;i++){
            r2color_grid.set(i,
                    new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
        for(int i=0;i<25;i++){
            r3color_grid.set(i,
                    new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
        //initalize r1correct_x and r1correct_y
        r1correct_x = (int) (Math.random() * num_row);
        r1correct_y = (int) (Math.random() * num_col);
        r2correct_x = (int) (Math.random() * 4);
        r2correct_y = (int) (Math.random() * 4);
        r3correct_x = (int) (Math.random() * 5);
        r3correct_y = (int) (Math.random() * 5);
        num_tiles = 9;
        muligan = 0;
        status = "MAIN_MENU";
        round = 1;
        correct_x = 0;
        width = 200;
        height = 200;
        margin = 10;
        correct_y = 0;
        color_grid = new MyArrayList<Color>();
        j=1;
        for (int i = 0; i < num_tiles; i++) {
            color_grid.add(Color.black);
        }
        this.addMouseListener(this);
        //initialize restart
        restart = new JButton("restart");
        restart.setBounds(350, 335, 100, 30); // sets the location and size
        restart.addActionListener(this); // add the listener
        this.add(restart); // add to JPanel
        restart.setVisible(false);
        baseColors = new Color[64];
        //initialize to random solid colors
        for (int i = 0; i < 64; i++) {
            baseColors[i] = new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
                    (int) (Math.random() * 255));
        }
        start = new JButton("start");
        start.setBounds(350, 335, 100, 30); // sets the location and size
        start.addActionListener(this); // add the listener
        this.add(start); // add to JPanel

        instructions = new JButton("instructions");
        instructions.setBounds(350, 365, 100, 30); // sets the location and size
        instructions.addActionListener(this); // add the listener
        this.add(instructions); // add to JPanel

        back = new JButton("back");
        back.setBounds(350, 500, 100, 30); // sets the location and size
        back.addActionListener(this); // add the listener
        setGrid();
        this.setFocusable(true);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, 800, 1000);

        // g.drawString( chatMessage, 100, 50 );
        //set background color to black
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 1000);
        g.setColor(Color.white);
        //display time elapsed
        //set cooler fonts
        Font title = new Font("Arial", Font.BOLD, 50);
        Font body = new Font("Arial", Font.BOLD, 20);

        if (status.equals("MAIN_MENU")) {
            g.setFont(title);
            g.drawString("Pixel Picker", 275, 100);
        } else if (status.equals("GAME")) {
            g.setFont(title);
            g.drawString("Round: " + round, 300, 100);
            g.setFont(body);
            //display time elapsed
            timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            timeElapsed++;
                            repaint();
                        }
                    });
                    
                    if(ran!=true){
                   timer.start();
                     ran=true;
                    }
            //as the value of timeElapsed gets closer to 7 the color of the text gets more red
            if(timeElapsed<7){
                g.setColor(new Color(255, 255 - timeElapsed * 36, 255 - timeElapsed * 36));
            }
            if(timeElapsed>=7){
                g.setColor(Color.red);
            }
            g.drawString("Time Elapsed: " + timeElapsed, 100, 50);
            g.setColor(Color.white);
            //play sound if round increases
            g.drawString("Points: " + points, 100, 100);
            
            //start timer

            
            paintGrid(g);
            // color to pick
            g.setColor(color_grid.get(correct_color));
            g.drawString("Color to pick", 100, 150);

        } else if (status.equals("INSTRUCTIONS")) {
            g.setFont(title);
            g.drawString("Instructions", 300, 100);
            g.setFont(body);
            g.drawString("Click on the color that matches the color to pick", 100, 200);
            g.drawString("If you pick the correct color within 7 seconds, you get 3 points", 100, 250);
            g.drawString("If you pick the correct color but after this time period, you get 1 point", 100, 300);
            g.drawString("If you pick the wrong color, you lose 2 points", 100, 350);
            g.setFont(body);


        }else if(status.equals("lose")){
            g.setColor(Color.black);
            g.drawRect(correct_x, correct_y, WIDTH, HEIGHT);
            g.setColor(Color.red);
            g.setFont(title);
            g.drawString("You Lose", 300, 100);
            g.setFont(body);
            g.drawString("You got " + points + " points", 100, 400);
            restart.setVisible(true);
        } else if(status.equals("win")){
            g.setColor(Color.black);
            g.drawRect(correct_x, correct_y, WIDTH, HEIGHT);
            g.setColor(Color.red);
            g.setFont(title);
            g.drawString("You Win", 300, 100);
            g.setFont(body);
            //play sound once
            int d=0;
            if(d==0){
                playSound("win.wav");
                d++;
            }
            g.drawString("You got " + points + " points", 100, 400);
            restart.setVisible(true);
        }
        else if(status.equals("waiting")){
            // g.setColor(Color.black);
            // g.drawRect(correct_x, correct_y, WIDTH, HEIGHT);
            // g.setColor(Color.red);
            // g.setFont(title);
            // g.drawString("Waiting for other players", 300, 100);
            // g.setFont(body);
            // g.drawString("You got " + points + " points", 100, 400);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw a colorful background
            //set background color to white
            g2d.setColor(Color.white);
            
            int gradientHeight = HEIGHT / 2;
            for (int y = 0; y < gradientHeight; y++) {
                float ratio = (float) y / gradientHeight;
                Color color = new Color(1 - ratio, ratio, 1);
                g2d.setColor(color);
                g2d.drawLine(0, y, WIDTH, y);
                g2d.drawLine(0, HEIGHT - y, WIDTH, HEIGHT - y);
            }

            g2d.setColor(Color.white);
            g2d.setFont(title);
            g2d.drawString("Waiting for other players", 100, 200);
            g2d.setFont(body);
            g2d.drawString("You got " + points + " points", 100, 400);
            //draw a ascii art that says winner
            g2d.setFont(body);
              
            
            

        }else if(status.equals("tie")){
            g.setColor(Color.black);
            g.drawRect(correct_x, correct_y, WIDTH, HEIGHT);
            g.setColor(Color.red);
            g.setFont(title);
            g.drawString("You Tied", 300, 100);
            g.setFont(body);
            g.drawString("You got " + points + " points", 100, 400);
            restart.setVisible(true);
        }
    }
    
    void sendScore(){
        String d = "" + points;
                
                try {
                    out.reset();
                    out.writeObject(d);
                    out.flush();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
    }
    public Dimension getPreferredSize() {
        return new Dimension(800, 1000);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == instructions) {
            this.remove(start);
            this.remove(instructions);
            this.add(back);
            status = "INSTRUCTIONS";
        }
        if (e.getSource() == back) {
            this.remove(back);
            this.add(instructions);
            this.add(start);
            status = "MAIN_MENU";
        }
        if (e.getSource() == start) {
            this.remove(instructions);
            this.remove(start);
            //start timer 

            status = "GAME";
        }
        if(e.getSource()==restart){
            //reset everything
            timer.stop();
            timeElapsed=0;
            color_grid_fixed = new MyArrayList<Color>();
            points=2;
            this.setLayout(null);
            num_col = 3;
            num_row = 3;
            r1color_grid = new MyArrayList<Color>();
            r2color_grid = new MyArrayList<Color>();
            r3color_grid = new MyArrayList<Color>();
            clientNumber = 0;
            //intialize the color grids to random colors
            for (int i = 0; i < 9; i++) {
                r1color_grid.add(Math.random() < 0.5 ? Color.black : Color.white);
            }
            for (int i = 0; i < 16; i++) {
                r2color_grid.add(Color.black);
            }
            for (int i = 0; i < 25; i++) {
                r3color_grid.add(Color.black);
            }
            for(int i=0;i<9;i++){
                r1color_grid.set(i,
                        new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            }
            for(int i=0;i<16;i++){
                r2color_grid.set(i,
                        new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            }
            for(int i=0;i<25;i++){
                r3color_grid.set(i,
                        new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            }
            //initalize r1correct_x and r1correct_y
            r1correct_x = (int) (Math.random() * num_row);
            r1correct_y = (int) (Math.random() * num_col);
            r2correct_x = (int) (Math.random() * 4);
            r2correct_y = (int) (Math.random() * 4);
            r3correct_x = (int) (Math.random() * 5);
            r3correct_y = (int) (Math.random() * 5);
            num_tiles = 9;
            muligan = 0;
            status = "MAIN_MENU";
            round = 1;
            correct_x = 0;
            width = 200;
            height = 200;
            margin = 10;
            correct_y = 0;
            color_grid = new MyArrayList<Color>();
            j=1;
            for (int i = 0; i < num_tiles; i++) {
                color_grid.add(Color.black);
            }

            this.add(instructions);
            this.add(start);
            restart.setVisible(false);

            setGrid();
        }

        repaint();
    }

    public void poll() throws IOException {

        String hostName = "localhost";
		int portNumber = 1025;
        System.out.println("Client trying to get info.");
		Socket serverSocket = new Socket(hostName, portNumber);
		out = new ObjectOutputStream(serverSocket.getOutputStream());
		in = new ObjectInputStream(serverSocket.getInputStream());
        try {
            while(true){                
                //input from server
                String input="";
                try {
                    //if input=integer
                    input = (String) in.readObject();
                    if(input!=""){
                        status=input;
                        repaint();
                    }
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Client received: " + input);
                
        }
       
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    public void setGrid() {
        if (round == 1) {
            num_tiles = 9;
            num_row = 3;
            num_col = 3;
        }
        if (round == 2) {
            color_grid = new MyArrayList<Color>();
            //set all the colors to black
            num_tiles = 16;
            for (int i = 0; i < num_tiles; i++) {
                color_grid.add(Color.black);
            }
            
            num_row = 4;
            num_col = 4;
        }
        if (round == 3) {
            color_grid = new MyArrayList<Color>();
            //set all the colors to black
            num_tiles = 25;
            for (int i = 0; i < num_tiles; i++) {
                color_grid.add(Color.black);
            }
            
            num_row = 5;
            num_col = 5;
        }
        
        for (int i = 0; i < num_tiles; i++) {
           
            if(round==1){
                color_grid.set(i, r1color_grid.get(i));
            }
            if(round==2){
                color_grid.set(i, r2color_grid.get(i));
            }
            if(round==3){
                color_grid.set(i, r3color_grid.get(i));
            }
        }
        //set the correct color
        if(round==1){
            correct_x = r1correct_x;
            correct_y = r1correct_y;
        }
        if(round==2){
            correct_x = r2correct_x;
            correct_y = r2correct_y;
        }
        if(round==3){
            correct_x = r3correct_x;
            correct_y = r3correct_y;
        }
        correct_color = correct_x * num_row + correct_y;
        color_grid.set(correct_color, color_grid.get(correct_x * num_row + correct_y));

    }
    
    
    public void paintGrid(Graphics g) {
        int x = 100;
        int y = 200;
        int width = 200;
        int height = 200;
        int margin = 10;
        if(round==1){
            x=100;
            y=200;
            width=200;
            height=200;
            margin=10;
            setGrid();
        }
        if(round==2){
            //make it so that it is proportional to the screen size
            x=100;
            y=200;
            width=150;
            height=150;
            margin=10;
            setGrid();
        }
        if(round==3){
            x=100;
            y=200;
            width=125;
            height=125;
            margin=10;
            setGrid();
        }
        for (int i = 0; i < num_row; i++) {
            for (int j = 0; j < num_col; j++) {
                g.setColor(color_grid.get(i * num_col + j));
                g.fillRect(x + i * (width + margin), y + j * (height + margin), width, height);
            }
        }
    }
    
  
    private void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
@Override
public void mouseClicked(MouseEvent e) {
    // Calculate the boundaries of the correct color coordinate
    playSound("hit.wav");
    int x=100;
    int y=200;
    
    if(round==2){
        x=100;
        y=200;
        width=150;
        height=150;
        margin=10;
        muligan=0;
    }
    if(round==3){
        x=100;
        y=200;
        width=125;
        height=125;
        margin=10;
        muligan=0;
    }
    int startX = x + correct_x * (width + margin);
    int endX = startX + width;
    int startY = y + correct_y * (height + margin);
    int endY = startY + height;
    // Check if the click event occurred within the correct color coordinate
    if (e.getX() >= startX && e.getX() <= endX && e.getY() >= startY && e.getY() <= endY) {
        //if it is clicked in 7 seconds of the round starting then increment points by 4
        if(round==3){
            status="waiting";
            if(ran1!=true){
                
                sendScore();
                ran1=true;
            }
        }else{
        round++;
        System.out.println(round);
        //start timer
        System.out.println("correct");
        if(timeElapsed<7){
            points=points+3;
        }
        else{
            points=points+1;
        }
        timeElapsed=0;
        }
        repaint();
    }else{
        if(status!="waiting"){
       points=points-2;
        }
    }
    
}
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

  @Override
    public void mouseEntered(MouseEvent e) {
        
    }

 @Override
    public void mouseExited(MouseEvent e) {
        
    }

}