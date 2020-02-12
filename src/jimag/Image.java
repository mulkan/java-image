/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jimag;

import Jama.Matrix;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author mulkan.ms@gmail.com
 * update : februari 2020
 */
public class Image {
    
     /**
     * 
     * @param mat
     * @return 
     */
    public static BufferedImage matrix2image(double mat [][])
    {
        double [][] matrix = mat.clone();
        mat = null;
        BufferedImage bufferedimage = null;

        WritableRaster writableraster=
                    (bufferedimage = new BufferedImage(
                                matrix[0].length,
                                matrix.length, 10)).getRaster();
        double[] pixel = new double[1];
        for (int i = 0; i < matrix[0].length; i++) 
        {
            for (int j = 0; j < matrix.length; j++) 
            {
                short h = (short) matrix[j][i];
                if(h<0)
                {
                    matrix[j][i] = 0;
                }
                if(h>255)
                {
                    matrix[j][i] = 255;
                }
                if(h>=0 && h<=255)
                {
                    matrix[j][i] = h;
                }
                pixel[0] = matrix[j][i];
                writableraster.setPixel(i, j, pixel);
            }
        }
        bufferedimage.setData(writableraster);

        return bufferedimage;
    }
    /**
     * operasi black white
     * @param matrix
     * @param negasi
     * @param threshold
     * @return 
     */
    public static double[][]blackwhite(double matrix [][], boolean negasi, double threshold)
    {
        double [][]biner = new double[matrix.length][matrix[0].length];
        double pixelBuffer = 0;
        for(int i = 0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix[0].length;j++)
            {

                pixelBuffer = matrix [i][j];
                if (negasi)
                {
                    if(pixelBuffer>=threshold)
                    {
                        biner[i][j]=  255;
                    }else
                    {
                        biner[i][j]=  0;
                    }
                }else
                {
                    if(pixelBuffer<threshold)
                    {
                        biner[i][j]= 255;
                    }else
                    {
                        biner[i][j]=  0;
                    }
                }



            }
        }
        return biner;
    }
    /**
     * operasi equlisasi histogram
     * @param matrix
     * @return 
     */
    public static double [][] histogramEqualization(double [][]matrix)
    {
        double [][] data = new Matrix(matrix).getArrayCopy();
       
        int mn = data.length*data[0].length;
        int bin = 256;
        int [] frekuensi = new int [bin];
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[0].length;j++)
            {
                frekuensi[(int)data[i][j]]++;
            }
        }
        double [] cumulatif = new double [bin];        
        double [] prob =  new double[bin];
        double cum = 0;
        double [] output = new double [bin];
        for(int i=0;i<cumulatif.length;i++)
        {
            
            cum=cum+frekuensi[i];
            cumulatif[i] =  cum;
            prob[i] = cumulatif[i]/mn; 
            output[i]= (double) (Math.floor(prob[i]*bin-1)); //tidak perlu round
            
            
        }
        double [][] result = data.clone();//new short[data.length][data[0].length];
        for(int i=0;i<result.length;i++)
        {
            for(int j=0;j<result[0].length;j++)
            {
                result[i][j] =   output[(int)data[i][j]];
            }
        }
        return result;
    }

    /**
     * convolution dengan array [][]
     * @param source
     * @param kernel
     * @return 
     */
    public static double[][] convolution(double[][] source, float[][] kernel) {
	int barKernel = kernel[0].length;
	int kolKernel = kernel.length;
	int baris2 = barKernel / 2;
	int kolom2 = kolKernel / 2;
	double[][] target1 = new double[source.length][source[0].length];
	int barSource = source.length;
	int kolSource = source[0].length;
	for (int i = 0; i < barSource; i++) {
	    for (int j = 0; j < kolSource; j++) {
		for (int j2 = 0; j2 < kolKernel; j2++) {
		    int a = kolKernel - 1 - j2;
		    for (int i2 = 0; i2 < barKernel; i2++) {
			int b = barKernel - 1 - i2;
			int c = i + (j2 - kolom2);
			int d = j + (i2 - baris2);
			if (c >= 0 && c < barSource && d >= 0
			    && d < kolSource) {
                            target1[i][j] += source[c][d] * kernel[a][b];
                        }

		    }
		}
	    }
	}
	return target1;
    }
    /**
     * Melakukan rotasi citra
     * <br> info lebih lanjut ke
     * <br> http://nayefreza.wordpress.com/2013/05/30/rotating-image-using-java/
     * @param bufferedImage
     * @param angle
     * @return
     */
    public static BufferedImage rotate(BufferedImage bufferedImage, double angle)
    {
        ImageRotate rot = new ImageRotate();
        return rot.getRotatedImage(bufferedImage, angle);

    }
        /**
     *
     * @param rgb : format rgb 3Dimensi
     * @param gray : format gray
     * @return 2Dimensi
     */
    public static double[][] rgb2gray(double rgb[][][])
    {
        double gray[][] = new double[rgb[0].length][rgb[0][0].length];
        try{
            for(int i=0;i<rgb[0].length;i++) //baris
            {
                for(int j=0;j<rgb[0][0].length;j++) //kolom
                {
                    //jika
                    if(rgb.length==3)
                    {
                        //ubah
                        gray[i][j]= (double)(0.2989*rgb[0][i][j]
                                        + 0.5870*rgb[1][i][j]
                                        +0.1140*rgb[2][i][j]);
                    }
                    else
                    {
                        gray[i][j]= (double) rgb[0][i][j];
                    }



                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }


        return gray;
    }

    /**
     * 
     * @param matrix
     * @return 
     */
    public static int [] pixelFrequency(double [][] matrix)
    {
        HistogramDistribution hist = new HistogramDistribution();
        hist.process(matrix);        
        return hist.getFrequency();
    }
    /**
     * menggunakan konsep Otsu method
     * @param matrix yaitu 0 sampai 255
     * @return
     */
    public static short graythresh(double matrix [][])
    {
        //# 1 hitung histogram
        HistogramDistribution hist = new HistogramDistribution();
        hist.process(matrix);
        //# 2 hitung nilai T nya
        int[] histogram = hist.getFrequency();
        int size = matrix.length * matrix[0].length;

        float  w = 0;                // first order cumulative
        float  u = 0;                // second order cumulative
        float  uT = 0;               // total mean level
        int    k = 256;              // maximum histogram index
        int    threshold = 0;        // optimal threshold value
        float  [] histNormalized=new float[256];  // normalized histogram values
        float  buffVariance, variance = 0;		// working variables
        double maxVariance = 0.0;

        // Create normalised histogram values

        // (size=image width * image height)
        for (int I=1; I<=k; I++){
            histNormalized[I-1] = histogram[I-1]/(float)size;
        }


        // Calculate total mean level
        for (int I=1; I<=k; I++){
            uT+=(I*histNormalized[I-1]);
        }


        // Find optimal threshold value

        for (int I=1; I<k; I++) {
            w+=histNormalized[I-1];
            u+=(I*histNormalized[I-1]);
            //buffVariance = (uT * w - u);
            //variance = (buffVariance * buffVariance) / ( w * (1.0f-w) );
            variance=(uT * w - u);
            variance*=variance;
            variance/=( w * (1.0f-w) );
            if (variance>maxVariance) {
                maxVariance=variance;
            }

        }
        //bersihkan memory
        hist = null;
        histogram = null;
        histNormalized = null;

        // Convert the final value to an integer
        return (short)Math.sqrt(maxVariance);
    }
    public static float [][] KernelSharpening = new  float[][]{
             {0,-1,0},
            {-1,5,-1},
            {0,-1,0}
        };

    public static float [][] KernelEdgeX = new float[][]{
             {1,1,1},
            {1,-8,1},
            {1,1,1}
        };
    public static float [][] KernelEdgeY = new float[][]{
             {0,-1,0},
            {-1,4,-1},
            {0,-1,0}
        };
    public static float [][] KernelBlur = new float[][]{
            {0f,0.2f,0f},
            {0.2f,0.2f,0.2f},
            {0f,0.2f,0f}
        };
    public static float [][] Sobel = new float[][]{
        {1,   0,   -1},
        {2,   0,   -2},
        {1,   0,   -1}
        };
}

/**
 *
 * @author nayef
 * http://nayefreza.wordpress.com/2013/05/30/rotating-image-using-java/
 */
class ImageRotate {

    public BufferedImage getRotatedImage(BufferedImage givenImage, double angle) {
        return getRotatedImage(givenImage, givenImage.getWidth() / 2, givenImage.getHeight() / 2, angle);
    }

    private BufferedImage getRotatedImage(BufferedImage givenImage, int rotationCenterX, int rotationCenterY, double angle) {

        BufferedImage image = initRotatedImage(givenImage, rotationCenterX, rotationCenterY, angle);
        int rotatedX;
        int rotatedY;
        int maxY = -99999;
        int maxX = -99999;
        int minX = 99999;
        int minY = 99999;
        int centerYOfImage = image.getHeight() / 2 + 1;
        int centerXOfImage = image.getWidth() / 2 + 1;

        for (int x = 0; x < givenImage.getWidth(); x++) {
            for (int y = 0; y < givenImage.getHeight(); y++) {
                rotatedX = centerXOfImage + calculateRotatedX(x - rotationCenterX, y - rotationCenterY, angle);
                rotatedY = centerYOfImage + calculateRotatedY(x - rotationCenterX, y - rotationCenterY, angle);
                if (maxX < rotatedX) {
                    maxX = rotatedX;
                }
                if (maxY < rotatedY) {
                    maxY = rotatedY;
                }
                if (minX > rotatedX) {
                    minX = rotatedX;
                }
                if (minY > rotatedY) {
                    minY = rotatedY;
                }

                image.setRGB(rotatedX, rotatedY, givenImage.getRGB(x, y));

            }
        }

        return image.getSubimage(minX, minY, maxX - minX, maxY - minY);
    }

    private int calculateRotatedX(int x, int y, double rotatingAngle) {
        double angleBeforeRotation = Math.atan2(y, x);
        double r = getRotatedLength(x, y);
        return (int) Math.round(r * Math.cos(angleBeforeRotation + rotatingAngle));
    }

    private int calculateRotatedY(int x, int y, double rotatingAngle) {
        double angleBeforeRotation = Math.atan2(y, x);
        double r = getRotatedLength(x, y);
        return (int) Math.round(r * Math.sin(angleBeforeRotation + rotatingAngle));
    }

    private double getR(int x0, int x1, int y0, int y1) {
        return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
    }

    private double getRotatedLength(int x, int y) {
        return getR(x, 0, y, 0);
    }

    private BufferedImage initRotatedImage(BufferedImage givenImage, int rotationCenterX, int rotationCenterY, double rotatingAngle) {
        double maxR = -99999;
        double minR = 99999;
        int maxX = givenImage.getWidth() - 1;
        int maxY = givenImage.getHeight() - 1;
        int minX = 0;
        int minY = 0;

        double temp = getR(minX, rotationCenterX, minY, rotationCenterY);
        if (temp > maxR) {
            maxR = temp;
        }
        temp = getR(minX, rotationCenterX, maxY, rotationCenterY);
        if (temp > maxR) {
            maxR = temp;
        }
        temp = getR(maxX, rotationCenterX, maxY, rotationCenterY);
        if (temp > maxR) {
            maxR = temp;
        }
        temp = getR(maxX, rotationCenterX, minY, rotationCenterY);
        if (temp > maxR) {
            maxR = temp;
        }

        int L = (int) Math.round(3 * maxR + 1);
        return new BufferedImage(L, L, givenImage.getType());
    }

}

class HistogramDistribution
{
    private int[] rangeGray;
    private int[] frequency;
    private double[] frequencyRatio;
    private double[][] data2d;



    private double[]data1d;
    private int max;


    /**
     * <p>process melakukan perhitungan histogram </p>
     *
     * @param data
     * @param min
     * @param max
     */
    public void process(double[][] data, int min, int max) {
        max=max+1;
	this.data1d = new double[data.length * data[0].length];
	this.data2d = data;
	int inc = 0;
        //mengubah data array2D menjadi array1D
	for (int i = 0; i < data.length; i++) {
	    for (int j = 0; j < data[0].length; j++) {
		data1d[inc] = data[i][j];
		inc++;
	    }
	}

	rangeGray = new int[max];
	for (int i = 0; i < max; i++) {
	    rangeGray[i] = min;
	    min++;
	}


        /*
         * melakukan normalisasi dan perhitungan frekuensi
         */
        frequency = new int[max];

	for (int i = 0; i < data1d.length; i++) {
	    for (min = 0; min < max; min++) {
		if (data1d[i] == rangeGray[min]){
                    frequency[min]++;
                }
		else{
                    frequency[min] = frequency[min];
                }

	    }
	}
        this.max=max;


    }
    /**
     * <p>process melakukan perhitungan histogram </p>
     * <p>jika merupakan matrix 3Dimensi akan dilalukan konversi
     * <br> 0.2989*pixel[0][i][j]+ 0.5870*pixel[1][i][j]+0.1140*[2]pixel[i][j]
     * @param pixel
     * @param min
     * @param max
     */
    public void process(double[][][] pixel) {
        int max =255;
        int min =0;
        max=max+1;
	this.data1d = new double[pixel[0].length * pixel[0][0].length];


	int inc = 0;
        double pixelBuffer = 0;
        //mengubah data array2D menjadi array1D
	for (int i = 0; i < pixel[0].length; i++) { //baris
	    for (int j = 0; j < pixel[0][0].length; j++) { //kolom
                //lakukan chek apakah 3Dimensi atau 2Dimensi
                if(pixel.length==3)
                {
                    pixelBuffer = (short)(0.2989*pixel[0][i][j]
                                    + 0.5870*pixel[1][i][j]
                                    +0.1140*pixel[2][i][j]);

                    data1d[inc] = pixelBuffer;
                }else
                {
                    data1d[inc] = pixel[0][i][j];
                }

		inc++;
	    }
	}

	rangeGray = new int[max];
	for (int i = 0; i < max; i++) {
	    rangeGray[i] = min;
	    min++;
	}


        /*
         * melakukan normalisasi dan perhitungan frekuensi
         */
        frequency = new int[max];

	for (int i = 0; i < data1d.length; i++) {
	    for (min = 0; min < max; min++) {
		if (data1d[i] == rangeGray[min]){
                    frequency[min]++;
                }
		else{
                    frequency[min] = frequency[min];
                }

	    }
	}
        this.max=max;


    }

    public void process(double[][] data) {
        int min=0;
        max=255;
        max=max+1;
	this.data1d = new double[data.length * data[0].length];

	int inc = 0;
        //mengubah data array2D menjadi array1D
	for (int i = 0; i < data.length; i++) {
	    for (int j = 0; j < data[0].length; j++) {
		data1d[inc] = (short)data[i][j];
		inc++;
	    }
	}

	rangeGray = new int[max];
	for (int i = 0; i < max; i++) {
	    rangeGray[i] = min;
	    min++;
	}


        /*
         * melakukan normalisasi dan perhitungan frekuensi
         */
        frequency = new int[max];

	for (int i = 0; i < data1d.length; i++) {
	    for (min = 0; min < max; min++) {
		if (data1d[i] == rangeGray[min]){
                    frequency[min]++;
                }
		else{
                    frequency[min] = frequency[min];
                }

	    }
	}



    }
    /**
     * <p> mendapatkan nilai range </p>
     * @return int - nilai berisi range dari nilai min dan maksimal nya
     */
    public int[] getRange() {
	return rangeGray;
    }
    /**
     * <p> mendapatkan nilai frekuensi / jumlah kemunculan nilai nya </p>
     * @return int - nilai berisi frekuensi tiap-tiap index
     */
    public int[] getFrequency() {
	return frequency;
    }
    /**
     * <p> mendapatkan nilai frekuensi / jumlah kemunculan nilai nya dengan
     * <br>nilai yang sudah dilakukan normalisasi
     * </p>
     * @return int - nilai berisi frekuensi tiap-tiap index yang telah dinormalisasi
     */
    public double[] getFrequencyRatio() {
        int N = data1d.length;
	frequencyRatio = new double[rangeGray.length];
	for (max = 0; max < frequencyRatio.length; max++){
            frequencyRatio[max] = (double) frequency[max] / (double) N;
        }

	return frequencyRatio;
    }
    /**
     * <p> mendapatkan nilai data </p>
     * @return
     */
    public double[][] getData() {
	return data2d;
    }
}