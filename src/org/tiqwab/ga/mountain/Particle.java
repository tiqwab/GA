package org.tiqwab.ga.mountain;

import processing.core.PApplet;
import toxi.geom.Vec3D;
import toxi.physics.VerletParticle;

public class Particle extends VerletParticle {

    public Particle(Vec3D vec) {
        super(vec);
    }
    
    void display(PApplet app) {
        app.fill(175);
        app.stroke(0);
        app.pushMatrix();
        app.translate(x, y, z);
        app.sphere(5);
        app.popMatrix();
    }

}
