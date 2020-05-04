// This is the class for the Finance panel inside the main GUI

import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class GuiFinance extends Panel{
    private java.awt.List employeeList = new java.awt.List();
    
    private Panel options = new Panel();
    
    private Button buttonRefresh = new Button("Refresh List");
    private Button buttonSetHours = new Button("Set hours");
    private Button buttonGetDetails = new Button("Get details");
    private Button buttonIncome = new Button("Company income statement");
    
    public GuiFinance(ArrayList<Order> orders, ArrayList<Employee> employees){
        this.setLayout(new BorderLayout());
        
        //Polymorphism - calculatePay() is called on the actual type of variable employee
        for (Employee employee : employees)
                employeeList.add(employee.toStringShort()+": "+employee.calculatePay()+"£");
        
        this.options.add(buttonRefresh);
        this.options.add(buttonSetHours);
        this.options.add(buttonGetDetails);
        this.options.add(buttonIncome);
        
        buttonRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                refreshList(employees);
            }
        });
        
        buttonSetHours.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                new SetHoursPrompt(employees, orders, getEmployeeList());
            }
        });
        
        // Here you can see the details of how calculatePay()
        buttonGetDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                int index = employeeList.getSelectedIndex();
                if (index!=-1)
                    new Alert("Pay details", employees.get(index).payToString()+"£");
                else
                   new Alert("Error", "Please select an item!");
            }
        });
        
        buttonIncome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                double inflows = 0;
                double outflows = 0;
                for (Order order : orders)
                    inflows += order.getValue();
                for (Employee e : employees)
                    outflows += e.calculatePay();
                new Alert("Income statement", ("Inflows: " + inflows + "£   OutFlows: -" + outflows + "£   Cash Netflow: "+(inflows-outflows)+"£"));    
            }
        });
        
        
        
        this.add(employeeList, "Center");
        this.add(options, "South");
    }
    
    public void refreshList(ArrayList<Employee> employees){
        this.employeeList.removeAll();
        for (Employee employee : employees)
                this.employeeList.add(employee.toStringShort()+": "+employee.calculatePay()+"£");
    }
    public java.awt.List getEmployeeList(){
        return this.employeeList;
    }
}
