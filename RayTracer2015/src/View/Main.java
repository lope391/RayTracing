/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import Math.Point;
import Math.Sphere;
import Scene.Scene;
import Scene.Colour;
import Scene.AmbientLight;
import Scene.PointLight;
import Scene.Material;

/**
 *
 * @author htrefftz
 */
public class Main extends JPanel {
    Image image;
    private final boolean DEBUG = false;
    
    public void setImage(Image image) {
        this.image = image;
    }
    
    /**
     * Create all elements in the scene
     * Could be read from a text file
     */
    public void createScene() {
        AmbientLight al = new AmbientLight(new Colour(.2, .2, .2));
        Scene.setAmbientLight(al);
        
        PointLight pl1 = new PointLight(new Point(-100, 100, 0), new Colour(1, 1, 1));
        PointLight pl2 = new PointLight(new Point(+100, 100, 0), new Colour(1, 1, 1));
        Scene.addPointLight(pl1);
        //Scene.addPointLight(pl2);
        
        // A red reflective sphere
        double Ka = 0.2;        // ambient
        double Kd = 0.5;        // difuse
        double Ks = 0.5;          // specular
        int n = 12;
        Colour color = new Colour(1, 0, 0);     // object's color
        double Ko = 0.8;          // Weight of this object's color
        double Kr = 0.2;          // Weight of the reflected color
        double Kt = 0;          // Weight of the refracted color
        Material material1 = new Material(Ka, Kd, Ks, n, color, Ko, Kr, Kt);
        
        Sphere sp1 = new Sphere(new Point(-10, +5, -100), 10, material1);
        Scene.addSphere(sp1);

        // A yellow non-reflective sphere
        Ka = 0.2;        // ambient
        Kd = 0.5;        // difuse
        Ks = 0.5;          // specular
        n = 12;
        color = new Colour(1, 1, 0);     // object's color
        Ko = 0.7;          // Weight of this object's color
        Kr = 0.3;          // Weight of the reflected color
        Kt = 0;          // Weight of the refracted color
        Material material2 = new Material(Ka, Kd, Ks, n, color, Ko, Kr, Kt);
        
        Sphere sp2 = new Sphere(new Point(+10, +5, -100), 10, material2);
        Scene.addSphere(sp2);

        // A blue non-reflective sphere
        Ka = 0.2;        // ambient
        Kd = 0.5;        // difuse
        Ks = 0.5;          // specular
        n = 12;
        color = new Colour(0, 0, 1);     // object's color
        Ko = 1;          // Weight of this object's color
        Kr = 0;          // Weight of the reflected color
        Kt = 0;          // Weight of the refracted color
        Material material3 = new Material(Ka, Kd, Ks, n, color, Ko, Kr, Kt);

        Sphere sp3 = new Sphere(new Point(0, -12, -100), 10, material3);
        Scene.addSphere(sp3);

    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;

        Dimension size = getSize();

        int w = size.width;
        int h = size.height;      
        
        Colour colour;
        for(int i = 0; i < image.height; i++) {
            for(int j = 0; j < image.width; j++) {
                colour = image.image[i][j];
                int red = (int) (colour.getR() * 255);
                int green = (int) (colour.getG() * 255);
                int blue = (int) (colour.getB() * 255);
                // Clamp out of range colors
                if(red > 255) red = 255;
                if(green > 255) green = 255;
                if(blue > 255) blue = 255;
                g2d.setColor(new Color(red, green, blue));
                if(DEBUG)
                    System.out.println(red + " " + green + " " + blue);
                g2d.drawLine(i, j, i, j);
            }
        }        
    }
    
    
    public static void main(String [] args) {
        JFrame frame = new JFrame("Ray Tracer 2015");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Main main = new Main();
        main.createScene();
        Image image = new Image(500, 500);
        image.generateImage();
        main.setImage(image);

        // Draw the result
        frame.add(main);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}
