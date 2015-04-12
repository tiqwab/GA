package org.tiqwab.ga.mountain;

import processing.core.PApplet;
import toxi.physics.VerletSpring;

public class Connection extends VerletSpring {

    Connection(Particle p1, Particle p2, float len, float strength) {
        super(p1, p2, len, strength);
    }
    
    void display(PApplet app) {
        app.stroke(55);
        app.line(a.x, a.y, a.z, b.x, b.y, b.z);
    }
}
