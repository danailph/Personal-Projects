// This is the menu for the main GUI

import java.awt.*;
import java.awt.event.*;
public class GuiMenu extends Panel{
    private Button buttonOrders = new Button("Orders");
    private Button buttonFinance = new Button("Finance");
    private Button buttonEmployees = new Button("Employees");
    
    public GuiMenu(Panel main, CardLayout cardLayout){
        this.buttonOrders.addActionListener(e -> cardLayout.show(main, "Orders"));
        this.buttonFinance.addActionListener(e -> cardLayout.show(main, "Finance"));
        this.buttonEmployees.addActionListener(e -> cardLayout.show(main, "Employees"));
       
        this.add(buttonOrders);
        this.add(buttonFinance);
        this.add(buttonEmployees);
    }
}
