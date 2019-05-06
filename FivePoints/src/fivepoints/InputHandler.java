/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fivepoints;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author parkhal1
 */

public class InputHandler {
    
    ArrayList<int[][]> symbols ;

    public InputHandler( String dirPath ) throws IOException {
        symbols = new ArrayList<>() ;
        
        Files.walk(Paths.get( dirPath ))
                .filter(Files::isRegularFile)
                .forEach( filePath -> {
            try {
                Handle( filePath.toString() );
            } catch (IOException ex) {
                Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } ) ;
    }
    
    public void Handle ( String filename ) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename)) ;

        int[][] array2D = new int[image.getHeight()][image.getWidth()] ;

        for (int xPixel = 0; xPixel < image.getHeight(); xPixel++) {
            for (int yPixel = 0; yPixel < image.getWidth(); yPixel++) {
                int color = image.getRGB(yPixel, xPixel) ;
                if (color==Color.BLACK.getRGB()) {
                    array2D[xPixel][yPixel] = 1 ;
                    System.out.print("o") ;
                } else {
                    array2D[xPixel][yPixel] = 0 ; 
                    System.out.print("-") ;
                }
            }
            System.out.print("\n") ;
        }
        this.symbols.add( array2D ) ;
    }

    public ArrayList<int[][]> getSymbols() {
        return symbols;
    }
    
    public Integer getSize() {
        return symbols.size() ;
    }
    
}
