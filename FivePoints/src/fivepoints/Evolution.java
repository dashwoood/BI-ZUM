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
        
        Integer katastrofa = 0 ;
        double previousFit ;
        long start = System.currentTimeMillis() ;  
        population = new Population( this, this.populationSize, this.symbols ) ;
        Random random = new Random() ;
        
        for ( int g = 0 ; g < numberOfGenerations ; g++ ) {
            
            // if the bestFitness is the same for 10 gens - copy the best one and randomly inicialize the others 
            if ( katastrofa == 10 ) {
                System.out.println("Disaster!") ;
                population.disaster() ;
                katastrofa = 0 ; 
            }
            
            previousFit = population.getBestIndividual().getFitness() ;
            if ( previousFit == 26 ) 
                break ;
            
            System.out.print( population.getBestIndividual().getFitness()+ "   " ) ;
            System.out.println( population.getBestIndividual() ) ;

            ArrayList<Individual> newInds = new ArrayList<>() ;
            newInds.add( population.getBestIndividual().deepCopy() ) ;
            
            while(newInds.size() < populationSize) {
                
                List<Individual> parents = population.selectIndividuals(2) ;
                Pair<Individual,Individual> offspring ;

                if( random.nextDouble() < crossoverRate ) {
                    // Deterministic crowding in crossover
                    offspring = parents.get(0).deepCopy().crossover(parents.get(1).deepCopy()) ;
                } else 
                    offspring = new Pair<>( parents.get(0).deepCopy(), parents.get(1).deepCopy()) ;
                
                // HillClimbing in mutation 
                offspring.getKey().mutate( mutationRate ) ;
                newInds.add(offspring.getKey()) ;
                
                if( newInds.size() < populationSize ) {
                    offspring.getValue().mutate( mutationRate ) ;
                    newInds.add(offspring.getValue()) ;
                }
            }
            
            for( int i=0 ; i < newInds.size() ; i++ ) {
                population.setIndividualAt(i, newInds.get(i)) ;
            }
            
            if ( previousFit == population.getBestIndividual().getFitness() )
                katastrofa++ ;
        }
        
        long end = System.currentTimeMillis() ;
        Individual best = population.getBestIndividual() ;

        System.out.println("Evolution has finished after " + (( end - start ) / 1000.0) + " s...") ;
        System.out.println("Best fitness = " + best.getFitness()) ;
        
        result = population.getBestIndividual().getMyPoints() ;

        System.out.println("========== Evolution finished =============") ;
        
    }

    public ArrayList<Coordinates> getResult() {
        return result;
    }
    
}
