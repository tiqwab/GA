package org.tiqwab.ga.mountain;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import toxi.geom.Vec3D;
import toxi.physics.VerletPhysics;

public class GraphDrawer {
    
    PApplet app;
    
    VerletPhysics physics;
    
    List<Particle> particles;
    List<Connection> springs;
    
    ICalculationFunc calculator;
    
    int maxX;
    int maxY;
    
    float len = 50;
    float strength = 0.125f;
    int interval = 8;
    
    
    GraphDrawer(PApplet app, VerletPhysics physics, ICalculationFunc func, int maxX, int maxY) {
        this.app = app;
        this.physics = physics;
        //this.calculator = func;
        
        //particles = new ArrayList<>();
        //springs = new ArrayList<>();
        
        this.maxX = maxX;
        this.maxY = maxY;
        
        setup(func);
        /*
        for (int i = -maxX; i < maxX; i += interval) {
            for (int j = -maxY; j < maxY; j += interval) {
                Particle p = new Particle(new Vec3D(i, j, calculator.calc(i, j)));
                p.lock();
                particles.add(p);
                physics.addParticle(p);
                
                if (i > -maxX) {
                    Connection c = new Connection(p, particles.get(particles.size() - 2*maxY/interval - 1), len, strength);
                    springs.add(c);
                    physics.addSpring(c);
                }
                
                if (j > -maxY) {
                    Connection c = new Connection(p, particles.get(particles.size()-2), len, strength);
                    springs.add(c);
                    physics.addSpring(c);
                }
            }
        }
        */
        
    }
    
    
    public void setup(ICalculationFunc func) {
        this.calculator = func;
        
        particles = new ArrayList<>();
        springs = new ArrayList<>();
        
        for (int i = -maxX; i < maxX; i += interval) {
            for (int j = -maxY; j < maxY; j += interval) {
                Particle p = new Particle(new Vec3D(i, j, calculator.calc(i, j)));
                p.lock();
                particles.add(p);
                physics.addParticle(p);
                
                if (i > -maxX) {
                    Connection c = new Connection(p, particles.get(particles.size() - 2*maxY/interval - 1), len, strength);
                    springs.add(c);
                    physics.addSpring(c);
                }
                
                if (j > -maxY) {
                    Connection c = new Connection(p, particles.get(particles.size()-2), len, strength);
                    springs.add(c);
                    physics.addSpring(c);
                }
            }
        }
    }
    
    
    public void setCalculator(ICalculationFunc func) {
        this.calculator = func;
    }
    
    
    public void displayGraph() {
        for (Connection c : springs) {
            c.display(app);
        }
    }
    
    /*
    public void displayGene(int x, int y, int z) {
        app.fill(0);
        app.pushMatrix();
        app.translate(x, y, z);
        app.sphere(3);
        app.popMatrix();
    }
    */

}
