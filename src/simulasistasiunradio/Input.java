/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulasistasiunradio;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author fadhlan
 */
public class Input extends JPanel {
    public ArrayList<Koordinat> points;
    private Point now, hapus;
    public ArrayList<Point> tabrakan = new ArrayList<>();
    int radiusLuar = 30, radiusPusat = 5;
    public int state = 0, maxColor = 0;
    public boolean enable = true, clicked = false;
    private GetColor color = new GetColor();
    private static final String abjad = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<JLabel> label_huruf;
    public static int CANVAS_WIDTH = 640;
    public static int CANVAS_HEIGHT = 480;
    
    private String imgFileName = "SimulasiStasiunRadio/Peta.png";
    private Image img;
    
    public Input(){
    points = new ArrayList();
        label_huruf = new ArrayList();
        loadImage();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(enable) {
                    if(e.getButton() == MouseEvent.BUTTON1) {
                        now = new Point(e.getX(), e.getY());
                        points.add(new Koordinat(now,radiusLuar));
                    }
                    else if(e.getButton() == MouseEvent.BUTTON3) {
                        hapus = new Point(e.getX(), e.getY());
                        delete(hapus);
                    }
                    repaint();
                }
            }
        });
}
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawImage(img, 0, 0, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.black);
        
        if(state == 1 || state == 2) gambarGaris(g);
        boolean x = true;
        for(int i=0; i < points.size(); i++) {
            int temp_warna;
            if(state == 0 || state == 1) temp_warna = 0;
            else {
                temp_warna = points.get(i).warna;
                if(maxColor < temp_warna) maxColor = temp_warna;
            }
            
            gambarPusat(g,i,points.get(i),temp_warna);
            if(state != 1) gambarLingkaran(g,points.get(i).point,points.get(i).radius,temp_warna);
            if(state == 2 && x) {
                x=false;
            }
        }
    }
     public void cariTabrakan() {
        tabrakan.clear();
        for(int i=0;i<points.size();i++) {
            for(int j=i+1;j<points.size();j++) {
                if(berpotongan(points.get(i).point,points.get(j).point,points.get(i).radius,points.get(j).radius)) {
                    tabrakan.add(new Point(i,j));
                    tabrakan.add(new Point(j,i));
                }
            }
        }
    }
    
    private void delete(Point hapus) {
        int i=0,k=-1;
        for(i=0;i<points.size();i++) {
            if(dekat(points.get(i).point,hapus)) {
                points.remove(points.get(i));
                break;
            }
        }
    }
    
    public void clear() {
        points.clear();
        this.label_huruf.clear();
    }
    
    private boolean dekat(Point a,Point b) {
        if((a.x - b.x)*(a.x - b.x)+(a.y - b.y)*(a.y - b.y) <= 4*radiusPusat*radiusPusat) return true;
        else return false;
    }
    
    private String text(int ke) {
        String hasil = "";
        hasil += abjad.charAt(ke%26);
        while(ke >= 26) {
            ke /= 26;
            hasil = abjad.charAt(ke%26-1) + hasil;
        }
        return hasil;
    }
    public void gambarPusat(Graphics g,int ke,Koordinat point,int warna) {
        g.setColor(Color.decode(color.cariwarna(warna)));
        g.fillOval(point.point.x-this.radiusPusat,point.point.y-this.radiusPusat,this.radiusPusat*2,this.radiusPusat*2);
        g.drawString(text(ke), point.x-point.radius/2, point.y-point.radius/2);
        g.setColor(Color.BLACK);
    }
    public void gambarLingkaran(Graphics g,Point pusat,int radius,int warna) {
        g.setColor(Color.decode(color.cariwarna(warna)));
        g.drawOval(pusat.x-radius,pusat.y-radius,radius*2,radius*2);
    }
    
    public void gambarGaris(Graphics g) {
        for(int i = 0; i < tabrakan.size(); i++) {
            int x1 = points.get(tabrakan.get(i).x).x;
            int y1 = points.get(tabrakan.get(i).x).y;
            int x2 = points.get(tabrakan.get(i).y).x;
            int y2 = points.get(tabrakan.get(i).y).y;
            g.drawLine(x1,y1,x2,y2);
        }
    }
    
    private boolean berpotongan(Point pusat1,Point pusat2,int radius1, int radius2) {
        if((pusat1.x - pusat2.x)*(pusat1.x - pusat2.x)+(pusat1.y - pusat2.y)*(pusat1.y - pusat2.y) <= (radius1+radius2)*(radius1+radius2)) return true;
        else return false;
    }
    
    public void print() {
        ArrayList<Point> x = tabrakan;
        ArrayList<String> data = new ArrayList();
        for(int i=0;i<points.size();i++) {
            String a = i+":";
            for(int j=0;j<x.size();j++) {
                if(x.get(j).x == i) {
                    a+="-"+x.get(j).y;
                }
            }
            data.add(a);
        }
        Graph2 graph = new Graph2(data);
        graph.colourVertices();
        for(int i=0;i<points.size();i++) {
            points.get(i).warna = graph.color[i]-1;
        }
    }
    public void loadImage() {
        URL imgUrl = getClass().getClassLoader().getResource(imgFileName);
        if (imgUrl == null) {
            System.err.println("Couldn't find file: " + imgFileName);
        } else {
            try {
                img = ImageIO.read(imgUrl);
                CANVAS_WIDTH = img.getWidth(this);
                CANVAS_HEIGHT = img.getHeight(this)+35;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.add(new Input());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
                frame.setVisible(true);
            }
        });
    }
    
}
