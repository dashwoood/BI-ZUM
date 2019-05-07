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
        
        //Simple random inicialization 
        /*
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
        } */
        
        // Better inicialization 
        if ( randomInit ) {
            Integer x = this.random.nextInt( 16 ) ;
            Integer y = this.random.nextInt( 16 ) ;
            Coordinates xy = new Coordinates( x, y ) ;
            this.myPoints.add( xy ) ;
            
            while ( this.myPoints.size() < 5 ) {
                x = this.random.nextInt( 16 ) ;
                y = this.random.nextInt( 16 ) ;
                xy = new Coordinates( x, y ) ;
                if ( tooNear( xy ) ) 
                    continue ;
                else 
                    this.myPoints.add( xy ) ;
            }
        } 
    }
    
    public boolean tooNear ( Coordinates xy ) {
        
        for ( Coordinates c : myPoints ) {
            if ( Math.sqrt( Math.pow((c.getX() - xy.getX()), 2) + Math.pow((c.getY() - xy.getY()), 2 )) < 2 ) {
                return true ;
            }
        }
        
        return false ;
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
        double rand_num = rand.nextDouble() ;
        
        for ( Coordinates xy : myPoints  ) {
            if ( rand_num < mutationRate ) {
                Coordinates tmp = hillClimbing( xy ) ;
                xy.setX( tmp.getX() ) ;
                xy.setY( tmp.getY() ) ;
            } 
        }
        
    }
    
      public Pair crossover( Individual other ) {
        Random r = new Random();
        
        int p = r.nextInt( this.myPoints.size() ) ;
        
        
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
        
        off1.computeFitness() ; 
        off2.computeFitness() ;
        Pair <Individual,Individual> result = new Pair ( off1, off2 ) ;
        //Pair <Individual,Individual> result = deterministicCrowding( this, other, off1, off2 ) ;
        return result ;
    }
      
    private int countSimilarity( Individual off, Individual parent ) {
        int result = 0 ;
        
        for ( Coordinates c : off.getMyPoints() ) {
            for ( Coordinates xy : parent.getMyPoints() ) {
                result *= Math.sqrt( Math.pow((c.getX() - xy.getX()), 2) + Math.pow((c.getY() - xy.getY()), 2 )) ;
            }  
        }
        
        return result ;
    }
      
    public Pair deterministicCrowding ( Individual parent1, Individual parent2, Individual off1, Individual off2 ) {
        
        Individual survived1, survived2 ;
        
        if ( countSimilarity( off1, parent1 ) < countSimilarity( off1, parent2 ) ) {
            if ( parent1.getFitness() > off1.getFitness() )
                survived1 = parent1.deepCopy() ;
            else 
                survived1 = off1.deepCopy() ;
        } else {
            if ( parent2.getFitness() > off1.getFitness() )
                survived1 = parent2.deepCopy() ;
            else 
                survived1 = off1.deepCopy() ; 
        }
        
        if ( countSimilarity( off2, parent1 ) < countSimilarity( off2, parent2 ) ) {
            if ( parent1.getFitness() > off2.getFitness() )
                survived2 = parent1.deepCopy() ;
            else 
                survived2 = off2.deepCopy() ;
        } else {
            if ( parent2.getFitness() > off2.getFitness() )
                survived2 = parent2.deepCopy() ;
            else 
                survived2 = off2.deepCopy() ; 
        }
        
        Pair <Individual,Individual> result = new Pair( survived1, survived2 ) ;
        
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

    private ArrayList<Coordinates> generateNeighbourhood( Coordinates xy ) {
        ArrayList<Coordinates> neighbourhood = new ArrayList<>() ;
        neighbourhood.add( xy ) ;
        
        if ( xy.getX() != 0 ) {
            Coordinates tmp = new Coordinates( xy.getX() - 1, xy.getY() ) ;
            neighbourhood.add( tmp ) ;
            
            if ( xy.getY() != 0 ) {
                tmp = new Coordinates( xy.getX() - 1, xy.getY() - 1  ) ;
                neighbourhood.add( tmp ) ;
            }
            
            if ( xy.getY() != 15 ) {
                tmp = new Coordinates( xy.getX() - 1, xy.getY() + 1  ) ;
                neighbourhood.add( tmp ) ;
            }
        }
        
        if ( xy.getX() != 15 ) {
            Coordinates tmp = new Coordinates( xy.getX() + 1, xy.getY() ) ;
            neighbourhood.add( tmp ) ;
            
            if ( xy.getY() != 0 ) {
                tmp = new Coordinates( xy.getX() + 1, xy.getY() - 1  ) ;
                neighbourhood.add( tmp ) ;
            }
            
            if ( xy.getY() != 15 ) {
                tmp = new Coordinates( xy.getX() + 1, xy.getY() + 1  ) ;
                neighbourhood.add( tmp ) ;
            }
        }
        
        if ( xy.getY() != 15 ) {
            Coordinates tmp = new Coordinates( xy.getX(), xy.getY() + 1 ) ;
            neighbourhood.add( tmp ) ;
        }
        
        if ( xy.getY() != 0 ) {
            Coordinates tmp = new Coordinates( xy.getX(), xy.getY() - 1 ) ;
            neighbourhood.add( tmp ) ;
        }
        
        return neighbourhood ;
    }
    
    private Coordinates hillClimbing( Coordinates xy ) {
        ArrayList<Coordinates> neighbourhood = generateNeighbourhood( xy ) ;
        Individual tmp = this.deepCopy() ;
        tmp.myPoints.remove(xy) ;
        
        double bestFitness = Double.NEGATIVE_INFINITY ;
        Coordinates winner = null ;
        
        for ( Coordinates neigh : neighbourhood ) {
            tmp.myPoints.add(neigh) ;
            tmp.computeFitness() ;
            if ( tmp.getFitness() > bestFitness ) {
                winner = neigh ;
                bestFitness = tmp.getFitness() ;
            }
            tmp.myPoints.remove( neigh ) ;
        }
        
        return winner ;
    }


    

}
