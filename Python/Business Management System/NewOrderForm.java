// This is a GUI form for a new order

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class NewOrderForm extends Frame{
    private Label lDescription = new Label("Description:");
    private TextField description = new TextField();
    private Label lAmount = new Label("Amount:");
    private TextField amount = new TextField();
    private Label lPrice = new Label("Price:");
    private TextField price = new TextField();
    private Label lDeadline = new Label("Deadline:");
    private TextField deadline = new TextField();
    private Label lManagers = new Label("Order manager:");
    private Choice managers = new Choice();
    private Label lClient = new Label("Client:");
    private Choice client = new Choice();
    
    private Button submit = new Button("Submit");
    
    public NewOrderForm(ArrayList<Employee> employees, ArrayList<Client> clients, ArrayList<Order> orders,  java.awt.List ordersList){
        super("New employee");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        for (Employee employee : employees)
            if (employee instanceof ExecutiveEmployee)
                this.managers.add(employee.getName());
        for (Client c : clients)
            this.client.add(c.getName());
            
        this.add(lDescription);
        this.add(description);
        this.add(lAmount);
        this.add(amount);
        this.add(lPrice);
        this.add(price);
        this.add(lDeadline);
        this.add(deadline);
        this.add(lManagers);
        this.add(managers);
        this.add(lClient);
        this.add(client);
        this.add(submit);
        
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                try{
                    orders.add(new Order(description.getText(), amount.getText(), price.getText(), deadline.getText(), clients.get(client.getSelectedIndex()),(ExecutiveEmployee) employees.get(getEmployeeIndex(employees, managers.getSelectedItem()))));
                    
                } catch (WrongDetailsException e){
                    new Alert("Invalid Input", e.getMessage());
                } catch (ClassCastException e){
                    new Alert("Invalid Input", "First add a new Executive employee");
                } finally {
                    ordersList.removeAll();
                        for (Order order : orders)
                        ordersList.add(order.toStringShort());
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
    
    private int getEmployeeIndex(ArrayList<Employee> employees, String name){
        int index = 0;
        for (int i=0; i<employees.size(); i++){
            if (employees.get(i).getName().equals(name))
                index = i;
        }
        return index;
    }
}
