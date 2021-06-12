/*
 * To change this license header, choose License Headers in Project Properties
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author d
 */
public class DoubleBreakout
{
 

    class Block {
        int x;
        int y;
        boolean minus1 = false;
        Color color;
        Shot shot;
        Rocket rockz;
    }
    
    class Shot {

        int x;
        int y;

    }


    class Rocket {

        int x;
        int y;

    }


    ArrayList<Rocket> rocks = new ArrayList<Rocket>();

    ArrayList<Shot> shots = new ArrayList<Shot>();
    ArrayList<Shot> shots2 = new ArrayList<Shot>();
    
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    Graphics g = null;
    
    ArrayList<Block> arr = new ArrayList<Block>();
    
    ArrayList<Block> arrUp = new ArrayList<Block>();

    boolean on = false;
    
    Thread t = null;
    
    InputStream imgStream;

    BufferedImage myImg;
    

    public DoubleBreakout() {
        try {
            imgStream = DoubleBreakout.class.getResourceAsStream("bg.png");
            myImg = ImageIO.read(imgStream);
        } catch(Exception e) {
            e.printStackTrace();
        }

        frame.setLayout(null);
        panel.setLayout(null);
        frame.setTitle("MODEL:DPRK VS US OF A");
        frame.setBounds(0, 0, 1000, 800);
        panel.setBounds(frame.getBounds());
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        g = panel.getGraphics();

        setup(g);
        setupColors(g);
        setupPlay(g);
        
        g.setColor(new Color(40, 140, 0));
        g.fillRect(0, 0, 1000, 800);

        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        g.setColor(new Color(40, 140, 0));
                        g.fillRect(0, 0, 1000, 800);
                        Thread.sleep(10000);
                    } catch(Exception e) {}
                }
            }
        };
        t.start();
    }
    
    private void displayAll() {
        displayReg(g);
        displayUp(g);

    }
    
    boolean strat = true;

    private void movePlayers() {
        

//        setupDPRKSoldiers(g);

        
        if(strat)
         frame.addKeyListener(new KeyListener(){
             @Override
                public void keyPressed(KeyEvent e) {
                    shootRockets(g);
                }

                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
        });

        strat = false;

        Thread t = new Thread() {
            public void run() {

                while(true) {
                    pauseSystemForSecond();

                    g.drawImage(myImg, 0, 0, 1000, 800, null);

                    try {
                        for(int i=0; i<arr.size(); i++) {
                            if(arr.get(i).y > 0 && arr.get(i).y <= 50)
                                arr.get(i).minus1 = true;
                            else if(arr.get(i).y > 400 && arr.get(i).y <= 455)
                                arr.get(i).minus1 = false;
                            if(arr.get(i).minus1)
                                arr.get(i).y+= 25;
                            else if(!arr.get(i).minus1)
                                arr.get(i).y-= 25;
                            boolean one = false;
                            Random r = new Random();
                            int o = r.nextInt(2);
                            if(o == 0)
                                one = false;
                            else
                                one = true;
                            if(one)
                                arr.get(i).x-= 45;
                            else
                                arr.get(i).x+= 45;
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        for(int i=0; i<arrUp.size(); i++) {
                            if(arrUp.get(i).y < 800 && arrUp.get(i).y >= 750)
                                arrUp.get(i).minus1 = true;
                            else if(arrUp.get(i).y < 400 && arrUp.get(i).y >= 345)
                                arrUp.get(i).minus1 = false;
                            if(arrUp.get(i).minus1)
                                arrUp.get(i).y-= 25;
                            else if(!arrUp.get(i).minus1)
                                arrUp.get(i).y+= 25;
                            boolean one = false;
                            Random r = new Random();
                            int o = r.nextInt(2);
                            if(o == 0)
                                one = false;
                            else
                                one = true;
                            if(one)
                                arrUp.get(i).x-= 45;
                            else
                                arrUp.get(i).x+= 45;
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    
    private void pauseSystemForSecond() {
        try {
            Thread.sleep(250);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupPlay(Graphics g) {

        Thread thread = new Thread() {
            public void run() {
                movePlayers();

                Thread t2 = new Thread() {
                    public void run() {
                        while(true) {
                            try {
                                Thread.sleep(4000);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
setupDPRKSoldiers(g);                            shootGunReg(g);
                            shootGunUp(g);
                            lineit(g);
                        }
                    }
                };
                t2.start();

                while(true) {
                    displayAll();
                }
            }
        };
        thread.start();
    }
    private void shootRockets(Graphics g) {

        Random r = new Random();

        int val = r.nextInt(arrUp.size());

        arrUp.get(val).rockz = new Rocket();
        arrUp.get(val).rockz.x = arrUp.get(val).x;
        arrUp.get(val).rockz.y = arrUp.get(val).y;
        rocks.add(arrUp.get(val).rockz);

        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        for(int i=0; i<rocks.size(); i++) {
                            rocks.get(i).y -= 40;
                            try {
                                InputStream imgStream = DoubleBreakout.class.getResourceAsStream("shap.png");
                                BufferedImage myImg = ImageIO.read(imgStream);
                                g.drawImage(myImg, rocks.get(i).x, rocks.get(i).y, 45, 135, null);
                            } catch(java.io.IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                        Thread.sleep(300);
                    } catch(Exception e) {}
                }
            }
        };
        t.start();
    }

    private void shootGunReg(Graphics g) {
        for(int i=0; i<arr.size(); i++) {
            arr.get(i).shot = new Shot();
            arr.get(i).shot.x = arr.get(i).x;
            arr.get(i).shot.y = arr.get(i).y;
            shots.add(arr.get(i).shot);
        }
        //for(int i=0; i<shots.size(); i++) {
        //    if(shots.get(i).y > 830)
        //        shots.remove(shots.get(i));
        //}
        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        int xx = 0, yy = 0;
                        for(int j=0; j<20; j++) {
                            for(int i=0; i<shots.size(); i++) {
                                shots.get(i).y += 40;
                                g.setColor(Color.WHITE);
                                g.fillRect(shots.get(i).x, shots.get(i).y, 10, 10);
                                if(i == shots.size() - 1) {
                                    xx = shots.get(i).x;
                                    yy = shots.get(i).y;
                                }
                                for(int ii=0; ii<arrUp.size(); ii++) {
                                    if(shots.get(i).x >= arrUp.get(ii).x &&
                                            shots.get(i).y >= arrUp.get(ii).y &&
                                            shots.get(i).x <= arrUp.get(ii).x + 20 &&
                                            shots.get(i).y <= arrUp.get(ii).y + 20) {
                                        g.setColor(new Color(40, 140, 0));
                                        g.fillRect(arrUp.get(ii).x, arrUp.get(ii).y, 15, 15);
                                        arrUp.get(ii).y = 695;
                                        //g.setColor(new Color(40, 140, 0));
                                        //g.fillRect(shots.get(i).x, shots.get(i).y, 10, 40);
                                        //shots.remove(shots.get(i));
                                    }
                                }
                            }
                            Thread.sleep(200);
                        }
                    } catch(Exception e) {}
                }
            }
        };
        t.start();
    }
    
    private void shootGunUp(Graphics g) {
        for(int i=0; i<arrUp.size(); i++) {
            arrUp.get(i).shot = new Shot();
            arrUp.get(i).shot.x = arrUp.get(i).x;
            arrUp.get(i).shot.y = arrUp.get(i).y;
            shots2.add(arrUp.get(i).shot);
        }
        //for(int i=0; i<shots2.size(); i++) {
        //    if(shots2.get(i).y < 0)
        //        shots2.remove(shots2.get(i));
        //}
        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        int xx = 0, yy = 0;
                        for(int j=0; j<20; j++) {
                            for(int i=0; i<shots2.size(); i++) {
                                shots2.get(i).y -= 40;
                                g.setColor(Color.WHITE);
                                g.fillRect(shots2.get(i).x, shots2.get(i).y, 10, 10);
                                if(i == shots2.size() - 1) {
                                    xx = shots2.get(i).x;
                                    yy = shots2.get(i).y;
                                }
                                for(int ii=0; ii<arr.size(); ii++) {
                                    if(shots2.get(i).x >= arr.get(ii).x &&
                                            shots2.get(i).y >= arr.get(ii).y &&
                                            shots2.get(i).x <= arr.get(ii).x + 20 &&
                                            shots2.get(i).y <= arr.get(ii).y + 20) {
                                        g.setColor(new Color(40, 140, 0));
                                        g.fillRect(arr.get(ii).x, arr.get(ii).y, 15, 15);
                                        arr.remove(arr.get(ii));
                                        //g.setColor(new Color(40, 140, 0));
                                        //g.fillRect(shots2.get(i).x, shots2.get(i).y, 10, 40);
                                        //shots2.remove(shots2.get(i));
                                    }
                                }
                            }
                            Thread.sleep(200);
                        }
                    } catch(Exception e) {}
                }
            }
        };
        t.start();
    }

    private void displayReg(Graphics g) {
        for(int i=0; i<arr.size(); i++) {
            try {
                g.setColor(arr.get(i).color);
                g.fillOval(arr.get(i).x, arr.get(i).y, 45, 45);
            } catch(Exception e) {}
        }
    }
    
    private void displayUp(Graphics g) {
        for(int i=0; i<arrUp.size(); i++) {
            try {
                g.setColor(arrUp.get(i).color);
                g.fillRect(arrUp.get(i).x, arrUp.get(i).y, 45, 45);
            } catch(Exception e) {}
        }
    }

    private void setup(final Graphics g) {
        Thread tt = new Thread() {
            public void run() {
                for(int i=0; i<100;) {
                    for(int j=0; j<80;) {
                        on = !on;
                        if(on) {
                            g.setColor(new Color(0, 163, 0));

                        } else {
                            g.setColor(new Color(0, 160, 0));

                        }
                        g.fillRect(i*10, j*10, 100, 100);
                        System.out.println("j:" + j);
                        j+=10;
                    }
                    i+=10;
                }
            }
        };
        //tt.start();
    }
    
    private void lineit(Graphics g) {
        g.setColor(Color.PINK);
        g.drawLine(0, 400, 1000, 400);
    }

    public void setupDPRKSoldiers(Graphics g) {
        if(arr.size() <= 3) {
            arr.clear();

            arr = new ArrayList<Block>();

            Random random = new Random();

            for(int i=0; i<50; i++) {
                int intValue = random.nextInt(255);
                int intValueX = random.nextInt(200);
                Block block = new Block();
                block.x = intValueX * 15;
                int intValuey = random.nextInt(15);
                block.y = intValuey * 15;
                block.color = new Color(100, intValue, 100);
                arr.add(block);
            }
        }
    }
    
    private void setupColors(Graphics g) {
        Random random = new Random();
        
        for(int i=0; i<50; i++) {
            int intValue = random.nextInt(255);
            int intValueX = random.nextInt(200);
            Block block = new Block();
            block.x = intValueX * 15;
            int intValuey = random.nextInt(15);
            block.y = intValuey * 15;
            block.color = new Color(100, intValue, 100);
            arr.add(block);
        }
        
        for(int i=0; i<50; i++) {
            int intValue = random.nextInt(255);
            int intValueX = random.nextInt(200);
            Block block = new Block();
            block.x = intValueX * 15;
            block.y = 800 - 5;
            int intValuey = random.nextInt(15);
            block.y -= intValuey * 15;
            block.color = new Color(intValue, 100 , 100);
            arrUp.add(block);
        }
        
    }

    public static void main(String[] args) {

        DoubleBreakout db = new DoubleBreakout();
    }
}