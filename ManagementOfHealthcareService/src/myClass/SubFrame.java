package myClass;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

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
    
    public SubFrame(JPanel p, JDialog d, Color c, int thickness) {
        p.addMouseMotionListener(new MouseMotionAdapter() {           
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                
                d.setLocation(x - xx, y - xy);
            }
        });
        
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xx = e.getX();
                xy = e.getY();
            }
        });
        
        d.getRootPane().setBorder(BorderFactory.createLineBorder(c, thickness, true));
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
    
    public void closeButton(JLabel l, JDialog d, Color colorBackExited, Color colorForeExited) {
        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                d.dispose();
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

