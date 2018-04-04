/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcms;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Harry
 */
public class MyTabbedPaneUI extends javax.swing.plaf.basic.BasicTabbedPaneUI {

    @Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, 
               int tabIndex, Rectangle iconRect, Rectangle textRect) {
        Color savedColor = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(rects[tabIndex].x, rects[tabIndex].y, 
               rects[tabIndex].width, rects[tabIndex].height);
        g.setColor(Color.BLACK);
        g.drawRect(rects[tabIndex].x, rects[tabIndex].y, 
               rects[tabIndex].width, rects[tabIndex].height);
        g.setColor(savedColor);
    }
 }
