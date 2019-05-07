/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fivepoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author parkhal1
 */

public class Population {

    private Random random;
    private Individual[] individuals ;
    private int size ;
    private Evolution evolution ;
    private ArrayList<int[][]> symbols ;
    
    
    public Population( Evolution evolution, int size, ArrayList<int[][]> symbols ) {
        this.random = new Random() ;
        this.symbols = symbols ;
        this.evolution = evolution ;
        this.size = size ;
        this.individuals = new Individual[size] ;
        for ( int i = 0; i < this.individuals.length; i++ ) {
            this.individuals[i] = new Individual (evolution, true, symbols) ;
            this.individuals[i].computeFitness() ;
        }
    }

    public int getSize() {
        return size;
    }
    
    public void disaster() {
        this.getBestIndividual().mutate(1) ;
        this.setIndividualAt(0, this.getBestIndividual()) ;
        for ( int i = 1; i < this.individuals.length - 1; i++ ) {
            Individual a = new Individual (this.evolution, true, this.symbols) ;
            a.computeFitness() ;
            this.setIndividualAt(i, a) ;
        }
    }
    
    public Individual getBestIndividual () {
        double bestFitness = Double.NEGATIVE_INFINITY ;
        Individual best = null ;
        
        for( Individual candidate : individuals ) {

            if( candidate.getFitness() > bestFitness ) {
                best = candidate ;
                bestFitness = candidate.getFitness() ;
            }
        }
        return best ;
    }
    
    public List<Individual> selectIndividuals(int count) {
        ArrayList<Individual> selected = new ArrayList<>() ;

        for( int i = 0 ; i < count ; i++ ) {
            
            double bestFitness = Double.NEGATIVE_INFINITY ;
            Individual winner = null ;
            
            for( int k = 0 ; k < 5 ; k++ ) {
                
                Individual candidate = this.individuals[random.nextInt(this.individuals.length)] ;
                if( candidate.getFitness() > bestFitness ) {
                    winner = candidate ;
                    bestFitness = candidate.getFitness() ;
                }
            } 
            selected.add(winner);
        }
        
        return selected;
    }

    void setIndividualAt( int i, Individual ind ) {
        this.individuals[i] = ind.deepCopy() ;
    }

    @Override
    public String toString() {
        return "Population{" + " " + individuals + '}';
    }
    
    
}
