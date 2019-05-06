/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fivepoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.util.Pair;

public class Evolution {
    
    private final Integer populationSize ;
    private final Integer numberOfGenerations ;
    private final Double mutationRate ;
    private final Double crossoverRate ;
    private ArrayList<Coordinates> result ;
    ArrayList<int[][]> symbols ;
    
    Population population;

    public Evolution( Integer populationSize, Integer numberOfGenerations, Double mutationRate, Double crossoverRate, ArrayList<int[][]> symbols ) { 
        this.populationSize = populationSize ;
        this.mutationRate = mutationRate ;
        this.crossoverRate = crossoverRate ;
        this.numberOfGenerations = numberOfGenerations ;
        this.symbols = symbols ;
    }
    
    public void start ( ) {
        
        long start = System.currentTimeMillis() ;
        population = new Population( this, this.populationSize, this.symbols ) ;
        Random random = new Random() ;
        
        for ( int g = 0 ; g < numberOfGenerations ; g++ ) {
            System.out.println( population.getBestIndividual() ) ;

            ArrayList<Individual> newInds = new ArrayList<>() ;
            newInds.add( population.getBestIndividual().deepCopy() ) ;
            
            while(newInds.size() < populationSize) {
                
                List<Individual> parents = population.selectIndividuals(2) ;
                Pair<Individual,Individual> offspring ;

                if( crossoverRate < random.nextDouble() ) 
                    offspring = parents.get(0).deepCopy().crossover(parents.get(1).deepCopy()) ;
                else 
                    offspring = new Pair<>( parents.get(0).deepCopy(), parents.get(1).deepCopy()) ;

                offspring.getKey().mutate( mutationRate ) ;
                offspring.getKey().computeFitness() ;
                newInds.add(offspring.getKey()) ;
                
                if( newInds.size() < populationSize ) {
                    offspring.getValue().mutate( mutationRate ) ;
                    offspring.getValue().computeFitness() ;
                    newInds.add(offspring.getValue()) ;
                }
            }
            
            for( int i=0 ; i < newInds.size() ; i++ ) {
                population.setIndividualAt(i, newInds.get(i)) ;
            }
        }
        
        long end = System.currentTimeMillis() ;
        Individual best = population.getBestIndividual() ;

        System.out.println("Evolution has finished after " + (( end - start ) / 1000.0) + " s...") ;
        System.out.println("Best fitness = " + population.getBestIndividual().getFitness()) ;
        
        // add some result printing ...
        result = population.getBestIndividual().getMyPoints() ;

        System.out.println("========== Evolution finished =============") ;
        
    }

    public ArrayList<Coordinates> getResult() {
        return result;
    }
    
}
