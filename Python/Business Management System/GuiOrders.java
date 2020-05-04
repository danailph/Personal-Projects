// This is the class for the Orders panel inside the main GUI

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class GuiOrders extends Panel{
    private java.awt.List listOrders = new java.awt.List();
    private Panel optionsOrders = new Panel();
    private java.awt.List listClients = new java.awt.List();
    
    private Label labelOrders = new Label("Orders");
    private Label labelClients = new Label("Clients");

    private Button buttonNewOrder = new Button("New Order");
    private Button buttonChangeStatus = new Button("Change Status");
    private Button buttonGetDetails = new Button("Get details");
    private Button buttonNewClient = new Button("New Client");
    
    public GuiOrders(ArrayList<Employee> employees, ArrayList<Client> clients, ArrayList<Order> orders){
        this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
        
        for (Order order : orders)
                this.listOrders.add(order.toStringShort());
        
        for (Client client : clients)
                this.listClients.add(client.toString());
        
        optionsOrders.add(buttonNewOrder);
        optionsOrders.add(buttonChangeStatus);
        optionsOrders.add(buttonGetDetails);
        
        
        this.add(labelOrders);
        this.add(listOrders);
        this.add(optionsOrders);
        this.add(labelClients);
        this.add(listClients);
        this.add(buttonNewClient);
        
        
        buttonNewOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                new NewOrderForm(employees, clients, orders, getListOrders());
            }
        });
        
        buttonChangeStatus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                if (listOrders.getSelectedIndex()!=-1)
                    new OrderStatusChangePrompt(orders, listOrders.getSelectedIndex(), getListOrders());
                else
                    new Alert("Error", "Please select an item!");
            }
        });
        
        buttonGetDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                int index = listOrders.getSelectedIndex();
                if (index != -1)
                    new Alert("detail", orders.get(index).toString());
                else
                    new Alert("Error", "Please select an item!");
                }
        });
        
        buttonNewClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                new NewClientForm(clients, getListClients());
            }
        });
    }
    
    public java.awt.List getListOrders(){
        return this.listOrders;
    }
    public java.awt.List getListClients(){
        return this.listClients;
    }
}
