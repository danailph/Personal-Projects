// This is an input prompt to change the status of an order

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class OrderStatusChangePrompt extends Frame{
    private Choice status = new Choice();
    private Button buttonChangeStatus = new Button("Change status");

    
    public OrderStatusChangePrompt(ArrayList<Order> orders, int index, java.awt.List ordersList){
        super("Order status");
        this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
        
        status.add("On Going");
        status.add("In delivery");
        status.add("Completed");
        status.select(0);
        this.add(status);
        this.add(buttonChangeStatus);
        
        buttonChangeStatus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                orders.get(index).setStatus(status.getSelectedItem());
                ordersList.removeAll();
                for (Order order : orders)
                    ordersList.add(order.toStringShort());
                dispose();
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
