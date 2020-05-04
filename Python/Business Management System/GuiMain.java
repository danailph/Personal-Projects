// This is the main frame of the GUI

import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class GuiMain extends Frame{
    private Panel main = new Panel();
    
    public GuiMain(String title, ArrayList<Employee> employees, ArrayList<Client> clients, ArrayList<Order> orders){
        super(title);
        this.setLayout(new BorderLayout());
        
        CardLayout cardLayout = new CardLayout();
        main.setLayout(cardLayout);
        main.add(new GuiOrders(employees, clients, orders), "Orders");
        main.add(new GuiFinance(orders, employees), "Finance");
        main.add(new GuiEmployees(employees), "Employees");
        
        this.add(new GuiMenu(main, cardLayout), "North");
        this.add(main, "South");
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                IO.save(employees, clients, orders);
                System.exit(0);
            }
        });
        
        this.pack();
        this.setVisible(true);
    }
}
