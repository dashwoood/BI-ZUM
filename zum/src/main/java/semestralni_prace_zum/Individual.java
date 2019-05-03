/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralni_prace_zum;

import java.util.Random;

/**
 *
 * @author parkhal1
 */

public class Individual {
    
    private double fitness = Double.NaN;
    private Evolution evolution;
    
    private Random random;

    public Individual( Evolution evolution, boolean randomInit ) {
        this.evolution = evolution ;
    }
    
    public void computeFitness() {
                
        double result = 0 ;
        
        // how many pics differ in those 5 points = fitness

        this.fitness = result;
    }
    
    public double getFitness() {
        return this.fitness;
    }
    
    
}
