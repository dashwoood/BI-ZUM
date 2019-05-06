/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralni_prace_zum;

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
    
    public Population( Evolution evolution, int size ) {
        individuals = new Individual[size] ;
        for ( int i = 0; i < individuals.length; i++ ) {
            individuals[i] = new Individual (evolution, true) ;
            individuals[i].computeFitness() ;
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
            
            for( int k = 0 ; k < 20 ; k++ ) {
                
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
}
