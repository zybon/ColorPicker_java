/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package szinvalasztos;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Dékán Tamás
 */
public class SzinValasztos extends JFrame{
    String szin = "";
    SzinValaszto szV  = new SzinValaszto(this);
    JButton gomb = new JButton("szinválasztó");
    Color hattSzin = new Color(0xcc33e0);
    JPanel jp = new JPanel();
    
    public SzinValasztos() {
        setSize(400,200);
//        this.setLocationRelativeTo(null);
        setTitle("Zybon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        jp.setBackground(hattSzin);
        jp.setBounds(30,110,50,50);
        gomb.setBounds(30, 30, 150, 30);
        gomb.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                szV.elozoSzin(hattSzin);
                szV.setVisible(true);
                if (szV.getValue()!=null) 
                {
                    hattSzin = szV.getValue();
                    jp.setBackground(hattSzin);
                    jp.updateUI();
                }
            }
    });
        add(jp);
        add(gomb);
        setVisible(true);

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SzinValasztos();
    }
    
}
