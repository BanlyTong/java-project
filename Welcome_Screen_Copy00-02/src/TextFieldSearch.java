
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class TextFieldSearch {
    public static void setTextLook(JTextField text) {
        
        text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                text.setCaretPosition(0);
        
                text.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent evt) {
                    if (text.getText().equals("Search here")) {
                        text.setText("");
                        text.setForeground(Color.black);
                    }
                }

                @Override
                public void keyReleased(KeyEvent evt) {
                    if (text.getText().equals("")) {
                        text.setText("Search here");
                        text.setForeground(Color.gray);
                        text.setCaretPosition(0);
                    }
                }
                });
        
                if (!text.getText().equals("Search here")) {
                    text.setCaretPosition(text.getText().length());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (text.getText().equals("")) {
                text.setForeground(Color.gray);
                text.setText("Search here");
        }
            }
        });
        
    }
}
