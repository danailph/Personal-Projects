// This is an input prompt to promote an employee

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class PromotionPrompt extends Frame{
    private Label lSalary = new Label("New salary:");
    private TextField salary = new TextField();
    private Label lBonus = new Label("New percentage bonus:");
    private TextField bonus = new TextField();
    
    private Button buttonPromote = new Button("Promote");
    
    public PromotionPrompt(ArrayList<Employee> employees, int index, java.awt.List employeeList){
        super("Promotion");
        this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) ); 
        
        this.add(lSalary);
        this.add(salary);
        if (employees.get(index) instanceof SalariedEmployee){
            this.add(lBonus);
            this.add(bonus);
        }
        this.add(buttonPromote);
        
        buttonPromote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                Employee employee = employees.get(index);
                try{
                    if (employee instanceof HourlyEmployee){
                        employees.set(index, new SalariedEmployee(employee.getName(), String.valueOf(employee.getAge()), employee.getDepartment(), employee.getTitle(), salary.getText()));
                    } else {
                        employees.set(index, new ExecutiveEmployee(employee.getName(), String.valueOf(employee.getAge()), employee.getDepartment(), employee.getTitle(), salary.getText(), bonus.getText()));
                    }
                } catch(WrongDetailsException exception){
                    new Alert("Invalid Input", exception.getMessage());
                } finally {
                    employeeList.removeAll();
                        for (Employee emp : employees)
                        employeeList.add(emp.toString());
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
