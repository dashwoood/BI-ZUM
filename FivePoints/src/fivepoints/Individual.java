/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fivepoints;

import java.util.ArrayList;
import java.util.HashSet;
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
    private ArrayList<int[][]> symbols ;
            
    private Random random;

    public Individual( Evolution evolution, boolean randomInit, ArrayList<int[][]> symbols ) {
        this.evolution = evolution ;
        this.random = new Random() ;
        this.myPoints = new ArrayList<>() ;
        this.symbols = symbols ;
        
        if ( randomInit ) {
            while ( this.myPoints.size() < 5 ) {
                Integer x = this.random.nextInt( 16 ) ;
                Integer y = this.random.nextInt( 16 ) ;
                Coordinates xy = new Coordinates( x, y ) ;
                if ( this.myPoints.contains( xy )) 
                    continue ;
                else 
                    this.myPoints.add( xy ) ;
            }
        }
    }

    public ArrayList<Coordinates> getMyPoints() {
        return myPoints;
    }
    
    private boolean compare ( int[][] symbol1, int[][] symbol2 ) {
        for ( Coordinates xy : this.myPoints ) {
            if ( symbol1[xy.getX()][xy.getY()] == symbol2[xy.getX()][xy.getY()] ) 
               return false ;
        }
        return true ;
    }
    
    public void computeFitness() {
                
        double result = 0 ;
        HashSet<String> different = new HashSet<>() ;
        
        for ( int[][] symbol : this.symbols ) {
            String tmp = "" ;
            for ( Coordinates xy : this.myPoints ) {
                tmp += symbol[xy.getX()][xy.getY()] ;
            }
            different.add(tmp) ;
        }
        result = different.size() ;
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
        
        
        Individual off1 = new Individual( this.evolution, false, this.symbols ) ;
        Individual off2 = new Individual( this.evolution, false, this.symbols ) ;
        
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
        Individual newOne = new Individual(this.evolution, false, this.symbols ) ;
        newOne.myPoints = (ArrayList<Coordinates>) this.myPoints.clone() ;
        newOne.fitness = this.fitness;
        
        return newOne;
    }

    @Override
    public String toString() {
        return "{" + myPoints + '}';
    }
    

}