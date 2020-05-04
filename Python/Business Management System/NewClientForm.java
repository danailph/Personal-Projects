// This is a GUI form for a new client

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class NewClientForm extends Frame{
    private Label lName = new Label("Name:");
    private TextField name = new TextField();
    private Label lNumber = new Label("Contact Number:");
    private TextField number = new TextField();
    
    private Button submit = new Button("Submit");


    public NewClientForm(ArrayList<Client> clients, java.awt.List clienntsList){
        super("New client");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        
        this.add(lName);
        this.add(name);
        this.add(lNumber);
        this.add(number);
        this.add(submit);
        
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                try{
                    clients.add(new Client(name.getText(), number.getText()));
                } catch (WrongDetailsException e) {
                    new Alert("Invalid Input", e.getMessage());
                } finally {
                    clienntsList.removeAll();
                    for (Client c : clients)
                        clienntsList.add(c.toString());
                        dispose();
                }
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
               dispose();
            }
        });
        
        this.pack();
        this.setVisible(true);
    }
}
