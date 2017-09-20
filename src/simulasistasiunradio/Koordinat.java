/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulasistasiunradio;

import java.awt.Point;

/**
 *
 * @author fadhlan
 */
public class Koordinat {
    int x,y,radius,warna;
    Point point;
    
    public Koordinat(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.point = new Point(x,y);
        this.radius = radius;
    }
    public Koordinat(Point point, int radius){
        this.x = point.x;
        this.y = point.y;
        this.point = point;
        this.radius = radius;
    }
}
