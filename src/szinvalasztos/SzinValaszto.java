/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package szinvalasztos;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Dékán Tamás
 */
public class SzinValaszto extends JDialog{
    SzinSkala skala = new SzinSkala();
    SzinArnyalat arnyalat = new SzinArnyalat();
    JButton ok = new JButton("Ok");
    JButton megse = new JButton("Mégse");
    JLabel RLab = new JLabel("R:");
    JTextField RField = new JTextField("");
    JLabel GLab = new JLabel("G:");
    JTextField GField = new JTextField("");
    JLabel BLab = new JLabel("B:");
    JTextField BField = new JTextField("");    
    JLabel hexLab = new JLabel("#");
    JTextField hexField = new JTextField("");
    JPanel elSzinMut = new JPanel();
    JPanel ujSzinMut = new JPanel();
    EgerEsemeny eger = new EgerEsemeny();
    GombEsemeny gomb = new GombEsemeny();
    Color Szin = null;
    int szinInt;
    int skalaInt;
    int[] szinRGB;
    String szinHex;
    
     SzinValaszto(JFrame jf) {
        super(jf,true);
        setSize(500,320);
        setLocation(500,200);
        setTitle("Színválasztó");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        arnyalat.setPosition(70, 20);
        skala.setPosition(20,20);

        //szinmutatók
        ujSzinMut.setBounds(345, 20, 70, 25);
        elSzinMut.setBounds(345, 45, 70, 25);
        //értékek
        RLab.setBounds(345, 80, 15, 25);
        RField.setBounds(360, 80, 30, 25);
        RField.setHorizontalAlignment(JTextField.CENTER);
        RField.setEditable(false);        
        GLab.setBounds(345, 110, 15, 25);
        GField.setBounds(360, 110, 30, 25);
        GField.setHorizontalAlignment(JTextField.CENTER);
        GField.setEditable(false);                
        BLab.setBounds(345, 140, 15, 25);
        BField.setBounds(360, 140, 30, 25);        
        BField.setHorizontalAlignment(JTextField.CENTER);
        BField.setEditable(false);                
        hexLab.setBounds(345, 170, 15, 25);
        hexField.setBounds(360, 170, 70, 25);
        hexField.setHorizontalAlignment(JTextField.CENTER);
        hexField.setEditable(false);
        //gombok
        ok.setBounds(360, 210, 100, 25);
        megse.setBounds(360, 240, 100, 25);        
        add(arnyalat);
        add(skala);
        add(ok);
        add(megse);
        add(elSzinMut);
        add(ujSzinMut);
        add(RLab);
        add(RField);
        add(GLab);
        add(GField);
        add(BLab);
        add(BField);        
        add(hexLab);
        add(hexField);
        skala.addMouseListener(eger);
        skala.addMouseMotionListener(eger);
        arnyalat.addMouseListener(eger);
        arnyalat.addMouseMotionListener(eger);
        ok.addActionListener(gomb);
        megse.addActionListener(gomb);
      
        
    }
    
    public void elozoSzin(Color elSzin) {
        Szin = elSzin;
        elSzinMut.setBackground(Szin);
        elSzinMut.updateUI(); 
        szinInt = ColorToInt(Szin);
        szinKereso(elSzin);
    }
    
     Color getValue() {
        return Szin;
    }
     
    int ColorToInt(Color szin){
        return szin.getRed()<<16|szin.getGreen()<<8|szin.getBlue();
    }
    
    int[] IntToRGB(int szinint){
        int[] rgb = new int[3];
        rgb[0] = szinint>>16&0xff;
        rgb[1] = szinint>>8&0xff;
        rgb[2] = szinint&0xff;
        return rgb;
    }    
    
    void frissites(){
        //paletta
        skalaInt = skala.szinSkalaRGB[skala.szel*skala.kijY];
        skala.repaint();
        arnyalat.RGBAlkotas(skalaInt);
        arnyalat.kepAlkotas();
        arnyalat.repaint();        
        //szövegmezők
        szinInt = arnyalat.RGB[arnyalat.kijX+(arnyalat.kijY*arnyalat.szel)];
        szinHex = Integer.toHexString(szinInt).toUpperCase();
            while (szinHex.length()<6) {
                szinHex = "0"+szinHex;
            }
        hexField.setText(szinHex);
        szinRGB = IntToRGB(szinInt);
        RField.setText(""+szinRGB[0]);
        GField.setText(""+szinRGB[1]);
        BField.setText(""+szinRGB[2]);
        //szinmutató
        Szin = new Color(szinInt);
        ujSzinMut.setBackground(Szin);
        ujSzinMut.updateUI();  

    }
    
    void szinKereso(Color kerSzin){
        int kerInt = ColorToInt(kerSzin);
        int aktInt;
        boolean megVan = false;
        for (int skS = 0;skS<256;skS++) {
            aktInt = skala.szinSkalaRGB[skala.szel*skS];
            arnyalat.RGBAlkotas(aktInt);
            for (int i = 0;i<arnyalat.RGB.length;i++)
            {
                if (kerInt == arnyalat.RGB[i]) {
                arnyalat.kijY = i/arnyalat.szel;    
                arnyalat.kijX = i-(arnyalat.kijY*arnyalat.szel);
                skala.kijY = skS;
                frissites();                
                megVan = true;
                break;
                }
                if (Math.abs(kerInt-arnyalat.RGB[i])<6) {
                arnyalat.kijY = i/arnyalat.szel;    
                arnyalat.kijX = i-(arnyalat.kijY*arnyalat.szel);
                skala.kijY = skS;
                frissites();                
                megVan = true;
                break;
                }
                
            }
            if (megVan) {
                break;
            }
        
        }
        if (!megVan) {
        System.out.println("nincs meg");
        szinInt = 0xff0000;
        frissites();
        }
        
        
        
    }
    
    class SzinSkala extends JPanel {
        BufferedImage kep;
            int szel = 30;
            int mag = 256;
            int[] szinSkalaRGB = new int[szel*mag];
        int kijY = 0;       


        SzinSkala(){
            createSkala();
            kep = new BufferedImage(szel,mag,BufferedImage.TYPE_INT_RGB);
            kep.setRGB(0, 0, szel, mag, szinSkalaRGB, 0, szel);
        }
  
        private void createSkala(){
            int[][] szakaszok = new int[][]{
                new int[]{0,0,0},
                new int[]{1,0,0},
                new int[]{1,1,0},
                new int[]{0,1,0},
                new int[]{0,1,1},
                new int[]{0,0,1},
                new int[]{1,0,1},
                new int[]{1,1,1}
                
            };
            
            int koz = szakaszok.length-1; 
            int szakaszHossz = mag/koz;
            int s = 0;
            int rgb = 0;
            for (int i = 0;i<szakaszok.length-1;i++) 
            {
                rgb = createAtmenetSzakasz(szakaszok[i], szakaszok[i+1], s, szakaszHossz);
                s += szakaszHossz;
            }
            while (s<mag){
                for (int o=0;o<szel;o++)       
                {
                szinSkalaRGB[s*szel+o]=0xffffff;
                }                
                s++;
            }
            kep = new BufferedImage(szel,mag,BufferedImage.TYPE_INT_RGB);
            kep.setRGB(0, 0, szel, mag, szinSkalaRGB, 0, szel);            
        }
        
        private int createAtmenetSzakasz(int[] kezdo, int[] veg, int s, int szakaszHossz){
            float dr = (veg[0]-kezdo[0])*255f/szakaszHossz;
            float dg = (veg[1]-kezdo[1])*255f/szakaszHossz;
            float db = (veg[2]-kezdo[2])*255f/szakaszHossz;
            float r = kezdo[0]*255f;
            float g = kezdo[1]*255f;
            float b = kezdo[2]*255f;
            int rgb = ((int)r)<<16 | ((int)g)<<8 | ((int)b);
            for (int i=0;i<szakaszHossz;i++)
            {
//                System.out.printf("r %f, g %f, b %f %n", r, g, b);
                for (int o=0;o<szel;o++)       
                {
                szinSkalaRGB[s*szel+o]=rgb;
                }                
                r += dr;
                g += dg;
                b += db;
                rgb = ((int)r)<<16 | ((int)g)<<8 | ((int)b);
                s++;
            }
            return rgb;
        }

        
        private void createSkala_er(){
            int r = 255;
            int g = 0;
            int b = 0;
            int dy = mag/6;
            for (int s=0;s<mag;s++) 
            {
                if (0<s&&s<=dy)
                {
                g = 255*s/dy;
                }
                if (dy<s&&s<=dy*2)
                {
                r = 255*(dy-(s-dy))/dy;
                }
                if (dy*2<s&&s<=dy*3)
                {
                b = 255*(s-dy*2)/dy;
                }  
                if (dy*3<s&&s<=dy*4)
                {
                g = 255*(dy-(s-dy*3))/dy;
                }  
                if (dy*4<s&&s<=dy*5)
                {
                r = 255*(s-dy*4)/dy;
                }              
                if (dy*5<s&&s<=dy*6)
                {
                b = 255*(dy-(s-dy*5))/dy;
                }   
                for (int o=0;o<szel;o++)       
                {
                szinSkalaRGB[s*szel+o]=(r<<16|g<<8|b);
                }    
            }        
        }
              
        
        void setPosition(int x, int y) {
            setBounds(x,y,szel,mag);
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(new Color(0xffffff));
            g.fillRect(0,0,szel,mag);
            g.drawImage(kep,0,0,this);
            g.setColor(new Color(0));
            g.drawArc(szel/2-5, kijY-5, 10, 10, 0, 360);
        }

         void skalaPoz(int y) {
            kijY = y;
            frissites();

        }
    }    

    class SzinArnyalat extends JPanel {
        BufferedImage kep;
        Dimension szinArnyalatMeret = new Dimension(256,256);
            int szel = szinArnyalatMeret.width;
            int mag = szinArnyalatMeret.height;
            int[] RGB = new int[szel*mag];
            int kijX = szel-1;
            int kijY = 0;

        SzinArnyalat(){

        } 

        void RGBAlkotas(int JFszin) {
            int[] rgbJF = IntToRGB(JFszin);
                double rTetoD = (double) ((255-(double)rgbJF[0])/255);
                double gTetoD = (double) ((255-(double)rgbJF[1])/255);
                double bTetoD = (double) ((255-(double)rgbJF[2])/255); 
                int r;
                int g;
                int b;
            for (int o=0;o<szel;o++)
            {    
                double rTeto = (double) (255-rTetoD*o);
                double gTeto = (double) (255-gTetoD*o);
                double bTeto = (double) (255-bTetoD*o);
                double rD = (double) rTeto/255;
                double gD = (double) gTeto/255;
                double bD = (double) bTeto/255;
                for (int s=0;s<mag;s++) 
                {
                   r = (int) (rTeto - rD*s); 
                   g = (int) (gTeto - gD*s); 
                   b = (int) (bTeto - bD*s); 
                   RGB[s*szel+o]=(r<<16|g<<8|b);
                }
            }

        }    

        void kepAlkotas() {
            kep = new BufferedImage(szel,mag,BufferedImage.TYPE_INT_RGB);
            kep.setRGB(0, 0, szel, mag, RGB, 0, szel);
        }
        
         void setPosition(int x, int y) {
            setBounds(x,y,szinArnyalatMeret.width,szinArnyalatMeret.height);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(kep,0,0,this);
            g.setColor(new Color(0xfffff));
            g.drawArc(kijX-5, kijY-5, 10, 10, 0, 360);
        }    

         void arnyPoz(int x,int y) {
            if (x<0) {x = 0;}
            if (y<0) {y = 0;}
            if (x>255) {x = 255;}
            if (y>255) {y = 255;}
            kijX = x;
            kijY = y;
            frissites();
        }    
    }    
    
    class EgerEsemeny extends MouseAdapter{
        boolean rajzol = false;
        boolean kijelol = false;

        @Override
        public void mousePressed(MouseEvent e) {
            //bal egér gomb
            if (e.getButton()==MouseEvent.BUTTON1) {
                if (e.getComponent()==skala) {
                    int y = e.getY();
                    if (y>=0&&y<255) {
                        skala.skalaPoz(y);
                    }
                }
                if (e.getComponent()==arnyalat) {
                    int x = e.getX();
                    int y = e.getY();
                    if ((x>=0&&x<255)&&(y>=0&&y<255)) {
                        arnyalat.arnyPoz(x,y);
                    }
                }             
            }
            //jobb egér gomb
            if (e.getButton()==MouseEvent.BUTTON3) {
            }        
        }

        @Override
        public void mouseReleased(MouseEvent e) {


        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
                if (e.getComponent()==skala) {
                    int y = e.getY();
                    if (y>=0&&y<=255) {
                        skala.skalaPoz(y);
                    }
                }
                if (e.getComponent()==arnyalat) {
                    int x = e.getX();
                    int y = e.getY();
                   // if ((x>=0&&x<255)&&(y>=0&&y<255)) {
                        arnyalat.arnyPoz(x,y);
                   // }
                }            
        }    

    }  
    
    class GombEsemeny implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Ok")) {
                dispose();
            }
            if (e.getActionCommand().equals("Mégse")) {
                Szin = null;
                dispose();
            }        


        }

    }    
}
