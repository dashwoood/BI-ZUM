/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralni_prace_zum;

import java.util.ArrayList;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author parkhal1
 */

public class Individual {
    
    private double fitness = Double.NaN;
    private Evolution evolution;
    private ArrayList<Coordinates> myPoints ;
            
    private Random random;

    public Individual( Evolution evolution, boolean randomInit ) {
        this.evolution = evolution ;
        
        //definition of the points 
    }

    public ArrayList<Coordinates> getMyPoints() {
        return myPoints;
    }
    
    public void computeFitness() {
                
        double result = 0 ;
        // how many pics differ in those 5 points = fitness
        this.fitness = result;
    }
    
    public double getFitness() {
        return this.fitness;
    }
    
    public Coordinates getPoints ( Integer i ) {
        return myPoints.get(i) ;
    }
    
    public void addPoints ( Coordinates xy ) {
        myPoints.add(xy) ;
    }
    
    public void mutate( double mutationRate ) {
        
        Random rand = new Random() ;
        int rand_num = rand.nextInt( myPoints.size() ) ;
        
        for ( Coordinates xy : myPoints  ) {
            if ( rand_num < mutationRate ) 
                xy.swap() ;
        }
        
    }
    
      public Pair crossover( Individual other ) {
        Random r = new Random();
        
        int p = r.nextInt( this.myPoints.size() - 1 ) ;
        
        
        Individual off1 = new Individual( evolution, false ) ;
        Individual off2 = new Individual( evolution, false ) ;
        
        for ( int i = 0 ; i < myPoints.size() ; i++ ) {
            if ( i < p ) {
                off1.addPoints( this.getPoints(i) ) ;
                off2.addPoints( other.getPoints(i)) ;
            } else {
                off2.addPoints( this.getPoints(i) ) ;
                off1.addPoints( other.getPoints(i)) ;
            } 
                
        }
        
        Pair <Individual,Individual> result = new Pair( off1, off2) ;
        return result ;
    }
    
    public Individual deepCopy() {
        Individual newOne = new Individual(evolution, false);
        System.arraycopy(this.myPoints, 0, newOne.myPoints, 0, this.myPoints.size());  
        newOne.fitness = this.fitness;
        
        return newOne;
    }
    
    
}
