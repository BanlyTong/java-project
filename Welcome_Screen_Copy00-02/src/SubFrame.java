
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sathya
 */
public class SubFrame {
    int xx, xy;

    public SubFrame(JPanel p, JFrame f, Color c, int thickness) {    
        p.addMouseMotionListener(new MouseMotionAdapter() {           
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                
                f.setLocation(x - xx, y - xy);
            }
        });
        
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xx = e.getX();
                xy = e.getY();
            }
        });
        
        f.getRootPane().setBorder(BorderFactory.createLineBorder(c, thickness, true));
    }
    
    public SubFrame(JPanel p, JFrame f) {
        p.addMouseMotionListener(new MouseMotionAdapter() {           
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                
                f.setLocation(x - xx, y - xy);
            }
        });
        
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xx = e.getX();
                xy = e.getY();
            }
        });
    }
    
    public void closeButton(JLabel l, JFrame f, Color colorBackExited, Color colorForeExited) {
        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.hide();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                l.setBackground(new Color(232, 17, 35));
                l.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                l.setBackground(colorBackExited);
                l.setForeground(colorForeExited);
            }
        });
    }
}

