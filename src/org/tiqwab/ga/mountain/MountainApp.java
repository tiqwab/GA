package org.tiqwab.ga.mountain;

import org.jgap.InvalidConfigurationException;

import processing.core.PApplet;
import toxi.physics.VerletPhysics;

public class MountainApp extends PApplet {
    
    public static final int WIDTH_G = /*512*/(int)Math.pow(2, MountainGA.HALF_GENE);
    public static final int HALF_WIDTH_G = WIDTH_G/2;
    public static final int INTERVAL_G = 10;
    
    VerletPhysics physics;
    
    GraphDrawer drawer;
    
    public static final ICalculationFunc[] functions = {new CalculationFuncA(), new CalculationFuncB()};
    ICalculationFunc func = functions[1];
    
    MountainGA mountainGA;
    int populationSize = 100;
    int evolveNum = 50;
    
    int cameraX = -200;
    int cameraY = -200;
    int cameraZ = 200;
    //float t = 0;
    
    
    @Override
    public void setup() {
        size(750, 400, P3D);
        smooth();
        
        physics = new VerletPhysics();
        drawer = new GraphDrawer(this, physics, func, HALF_WIDTH_G, HALF_WIDTH_G);
        
        try {
            mountainGA = new MountainGA(func, drawer, populationSize, evolveNum);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void draw() {
        background(255);
        camera(cameraX, cameraY, cameraZ, 0, 0, 0, 0, 0, -1);
        
        //t += 2 * PI / (frameRate * 5);
        //translate(100, 100, 0);
        //rotateZ(t);
        //translate(-100, -100, 0);
        
        rotate(startAngle + presentAngle);
        
        drawGrid();
        drawAxis();
        
        physics.update();
        
        drawer.displayGraph();
        
        mountainGA.evolve();
        mountainGA.display(this);       
    }
    
    
    public void reset() {
        mountainGA.reset();
        try {
            mountainGA.setup(func, populationSize, evolveNum);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        
        drawer.setup(func);
    }
    
    
    int startAngle = 0;
    int presentAngle = 0;
    int dragStartX = 0;
    
    @Override
    public void mousePressed() {
        dragStartX = mouseX;
    }   
    
    @Override
    public void mouseDragged() {
        presentAngle = (dragStartX - mouseX) / 10;
    }
     
    @Override
    public void mouseReleased() {
        startAngle = presentAngle + startAngle;
        presentAngle = 0;
    }
    
    
    @Override
    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                cameraY += 5;
            } else if (keyCode == DOWN) {
                cameraY -= 5;
            } else if (keyCode == RIGHT) {
                cameraX += 5;
            } else if (keyCode == LEFT) {
                cameraX -= 5;
            }
        } else {
            if (key == 'z' || key == 'Z') {
                cameraZ += 5;
            } else if (key == 'x' || key == 'X') {
                cameraZ -= 5;
            } else if (key == '1') {
                func = functions[0];
                reset();
            } else if (key == '2') {
                func = functions[1];
                reset();
            }
        }
    }
    
    
    public void drawGrid() {
        stroke(125);
        for (int i = -HALF_WIDTH_G/INTERVAL_G; i <= HALF_WIDTH_G/INTERVAL_G; i++) {
            line(-HALF_WIDTH_G, i*INTERVAL_G, HALF_WIDTH_G, i*INTERVAL_G);
            line(i*INTERVAL_G, -HALF_WIDTH_G, i*INTERVAL_G, HALF_WIDTH_G);
        }
    }
    
    
    public void drawAxis() {
        stroke(255, 0, 0);
        fill(255, 0, 0);
        line(-HALF_WIDTH_G, 0, 0, HALF_WIDTH_G, 0, 0);
        pushMatrix();
        translate(HALF_WIDTH_G, 0, 0);
        triangle(10, 0, 0, 5, 0, -5);
        popMatrix();
        
        stroke(0, 255, 0);
        fill(0, 255, 0);
        line(0, -HALF_WIDTH_G, 0, 0, HALF_WIDTH_G, 0);
        pushMatrix();
        translate(0, HALF_WIDTH_G, 0);
        triangle(0, 10, 5, 0, -5, 0);
        popMatrix();
        
        stroke(0, 0, 255);
        fill(0, 0, 255);
        line(0, 0, -HALF_WIDTH_G, 0, 0, HALF_WIDTH_G);
        pushMatrix();
        translate(0, 0, HALF_WIDTH_G/2);
        beginShape();
        vertex(0, 0, 10);
        vertex(-5, 0, 0);
        vertex(5, 0, 0);
        endShape();
        popMatrix();
    }
}
