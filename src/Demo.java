
import java.awt.image.BufferedImage;
import java.io.File;
import jimag.Image;
import jimag.ImageRead;
import jimag.ImageShow;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ASUS
 */
public class Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BufferedImage image = ImageRead.read(new File("D:/panoramic.jpg"));        
          
        double [][] gray = Image.rgb2gray(ImageRead.getMatrix(image));
        
        double T = Image.graythresh(gray);
        
        
        double [][] gray_image_histeq = Image.histogramEqualization(gray);
        
        double [][] bw = Image.blackwhite(gray, false,T);
        
        double [][] filt = Image.convolution(gray,Image.KernelSharpening);
        
        int []frekuensi = Image.pixelFrequency(gray);
        int endl = 0;
        new ImageShow(image).setVisible(true);
        new ImageShow(Image.matrix2image(gray)).setVisible(true);
        new ImageShow(Image.matrix2image(gray_image_histeq)).setVisible(true);
        new ImageShow(Image.matrix2image(bw),"Nilai T : "+String.valueOf(T)).setVisible(true);
        new ImageShow(Image.matrix2image(filt)).setVisible(true);
        
        //new ImageShow(Image.rotate(image,45),"gambar frog").setVisible(true);
        
    }
    
}
