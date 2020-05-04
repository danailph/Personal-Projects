// This is the class for the Employees panel inside the main GUI

import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class GuiEmployees extends Panel{
    public java.awt.List emlpoyeeList = new java.awt.List();
    
    private Panel options = new Panel();
    
    private Button buttonNewEmployee = new Button("New Employee");
    private Button buttonFire = new Button("Fire employee");
    private Button buttonPromote = new Button("Promote employee");


    public GuiEmployees(ArrayList<Employee> employees){
        this.setLayout(new BorderLayout());
        
        for (Employee employee : employees)
                emlpoyeeList.add(employee.toString());
                
        this.options.add(buttonNewEmployee);
        this.options.add(buttonFire);
        this.options.add(buttonPromote);
        
        buttonNewEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                new NewEmployeeForm(employees, getEmployeeList());
            }
        });
        
        buttonFire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                int index = emlpoyeeList.getSelectedIndex();
                if (index!=-1){
                    employees.remove(index);
                }
                else
                    new Alert("Error", "Please select an item!");
                updateEmployeeList(employees);
            }
        });
        
        buttonPromote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                int index = emlpoyeeList.getSelectedIndex();
                if (index!=-1) {
                    Employee e = employees.get(index);
                    if (e instanceof ExecutiveEmployee){
                        new Alert("Invalid promotion","Executive employee could not be promoted further!");
                    } else {
                        new PromotionPrompt(employees, index, getEmployeeList());
                    }
                } else {
                    new Alert("Error", "Please select an item!");
                }
                updateEmployeeList(employees);
                
            }
        });
        
        this.add(emlpoyeeList, "Center");
        this.add(options, "South");
    }
    
    public java.awt.List getEmployeeList(){
        return this.emlpoyeeList;
    }
    public void updateEmployeeList(ArrayList<Employee> employees){
        this.emlpoyeeList.removeAll();
        for (Employee employee : employees)
                this.emlpoyeeList.add(employee.toString());
    }
}
