/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jimag;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author mulkan.ms@gmail.com
 * update : februari 2020
 */
public class ImageRead extends BufferedImage{

    public ImageRead(int width, int height, int imageType) 
    {
        super(width, height, imageType);
    }
    public static BufferedImage read(File file)
    {
        try {
            return  ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(ImageRead.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static double [][][] getMatrix(BufferedImage bufferedimage)
    {
        double matrix [][][];
        Raster raster = bufferedimage.getData();
        
        //update nilai
        int kolom = raster.getWidth();
        int baris = raster.getHeight();

        //if true color
        if(bufferedimage.getType()==5){

            //init
            double [][] r = new double[baris][kolom];
            double [][] g = new double[baris][kolom];
            double [][] b = new double [baris][kolom];
            //raster.getNumBands();

                for (int y = 0; y < baris; y++)
                {
                    for (int x = 0; x < kolom; x++)
                    {
                        r[y][x] = (double)(raster.getSample(x, y, 0));
                        g[y][x] = (double)(raster.getSample(x, y, 1));
                        b[y][x] = (short)(raster.getSample(x, y, 2));

                    }
                }
                //init
                matrix = new double[3][r.length][r[0].length];
                //update
                matrix[0]=r;
                matrix[1]=g;
                matrix[2]=b;
        }
        //gray
        else
        {
            //init
            double [][] gray = new double[baris][kolom];
            //raster.getNumBands();
            for (int y = 0; y < baris; y++)
            {
                for (int x = 0; x < kolom; x++)
                {
                    gray[y][x]= (short)(raster.getSample(x, y, 0));
                }
            }
            //init
            matrix = new double[1][gray.length][gray[0].length];
            //update
            matrix[0] = gray;

        }
        return matrix;
    }
    
}
