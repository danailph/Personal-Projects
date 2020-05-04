// Gui for an Alert pop-up window 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Alert extends Frame{
    private Button b = new Button("OK");
    private TextArea message = new TextArea();
    
    public Alert(String title, String text){
        super(title);
        setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
        
        this.message.setText(text);
        this.message.setEditable(false);
        
        b.addActionListener ( new ActionListener()  {  
            public void actionPerformed( ActionEvent e )  
            {  
                dispose(); 
            }  
        });
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                dispose();
            }
        });
        
        this.add(b);
        this.add(message);
        this.pack();
        this.setVisible(true);
    }
}
