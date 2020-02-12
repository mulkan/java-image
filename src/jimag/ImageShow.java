/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jimag;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author mulkan.ms@gmail.com
 * update : februari 2020
 */
public class ImageShow extends JFrame
{
    //untuk menampung gambar
    private JButton canvas; 
    private Dimension dimensi;
    private JScrollPane scroll;
    private BufferedImage image;
    public ImageShow()
    {
        initComponent();
    }
    public ImageShow(BufferedImage bi)
    {
        initComponent();        
        canvas.setIcon(new ImageIcon(bi));
    }
    
    public ImageShow(BufferedImage bi,String title)
    {
        initComponent();
        canvas.setIcon(new ImageIcon(bi));
        setTitle(title);
        
    }
    private void initComponent()
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        dimensi = new Dimension((int)(screen.width*0.5),(int)(screen.height*0.5));
        
        this.setSize(dimensi);

        canvas = new JButton();
        canvas.setSize(dimensi);

        
        scroll = new JScrollPane();
        scroll.setViewportView(canvas);
        
        
        //tambah component
        this.add(scroll);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setCenter();
        //this.pack();
        
    }
    private void setCenter()
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int X = (screen.width / 2) - (this.getWidth() / 2); // Center horizontally.
        int Y = (screen.height / 2) - (this.getHeight() / 2); // Center vertically.

        this.setBounds(X,Y , this.getWidth(),this.getHeight());
    }
    public static void main(String [] args)
    {
        new ImageShow().setVisible(true);
    }
}
