package myClass;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;

import java.util.Arrays;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Harry
 */

public class TextFieldBehavior {
    public static void FocusGained(JTextField tf,String Value)
    {
        if(tf.getText().equals(Value)&&tf.getForeground().equals(new Color(153,153,153)))
        {
            //txtUsername.setText(null);
            tf.setCaretPosition(0);
            //
            
        }
        
        else
        {
            tf.selectAll();
        }
    }
    public static void KeyTyped (JTextField tf,String Value)
    {
        if(tf.isFocusOwner())
        {
        if(tf.getText().equals(Value))
        {
            tf.setText("");
            tf.setForeground(new Color(0,0,0));
            
        }
        }
    }
    public static void KeyReleased (JTextField tf,String Value)
    {
        if(tf.isFocusOwner())
        {
        if(tf.getText().isEmpty())
        {
            tf.setCaretPosition(0);
            tf.setText(Value);
            tf.setForeground(new Color(153,153,153));
            tf.setCaretPosition(0);
            
        }
        }
    }
    public static void FocusLost (JTextField tf, String Value)
    {
        if (tf.getText().isEmpty())
        {
            tf.setText(Value);
            tf.setForeground(new Color(153,153,153));
        }
    }
    
    public static void PasswordKeyTyped (JPasswordField tf,String Value)
    {
        if(tf.isFocusOwner())
        {
        if(Arrays.equals(Value.toCharArray(), tf.getPassword()))
        {
            //tf.setText(null);
            tf.setEchoChar('\u25cf');
            tf.setForeground(new Color(0,0,0));
            tf.setText("");
        } else {
        }
        }
    }
    public static void PasswordFocusGained(JPasswordField tf,String Value)
    {
        if(tf.isFocusOwner())
        {
            if(Arrays.equals(Value.toCharArray(), tf.getPassword()))
            {
                if(tf.getForeground().equals(new Color(153,153,153)))
                {
                    tf.setCaretPosition(0);
                }
                           
            }
            else
            {
                //txtUsername.setText(null);
                tf.selectAll(); 
            }
        }
    }
    public static void PasswordFocusLost (JPasswordField tf, String Value)
    {
        if (Arrays.equals("".toCharArray(), tf.getPassword()))
        {
            tf.setEchoChar((char)0);
            tf.setText(Value);
            tf.setForeground(new Color(153,153,153));
        }
    }
    public static void PasswordKeyReleased (JPasswordField tf,String Value)
    {
        if(tf.isFocusOwner())
        {
        if(Arrays.equals("".toCharArray(), tf.getPassword()))
        {
            tf.setCaretPosition(0);
            tf.setEchoChar((char)0);
            tf.setText(Value);
            tf.setForeground(new Color(153,153,153));
            tf.setCaretPosition(0);
            
        }
        }
    }
    public static void SetTextField(JTextField tf , String Value)
    {
        tf.setText(Value);
        tf.setForeground(new Color(153,153,153));
    }
    private static void SimpleButton()
    {
        try
        {
            for (UIManager.LookAndFeelInfo lnf : 
                UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(lnf.getName())) {
                UIManager.setLookAndFeel(lnf.getClassName());
                break;
                }
        }
        } catch (Exception e) { /* Lazy handling this >.> */ }
        
    }
}
