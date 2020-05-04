// This is a GUI form for a new employee

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class NewEmployeeForm extends Frame{
    private Choice employeeClass = new Choice();
    private Label lName = new Label("Name:");
    private TextField name = new TextField();
    private Label lAge = new Label("Age:");
    private TextField age = new TextField();
    private Label lDepartment = new Label("Department:");
    private Choice department = new Choice();
    private Label lTitle = new Label("Title:");
    private TextField title = new TextField();
    private Label lWage = new Label("Wage:");
    private TextField wage = new TextField();
    private Label lSalary = new Label("Salary:");
    private TextField salary = new TextField();
    private Label lBonus = new Label("Percentage Bonus:");
    private TextField bonus = new TextField();
        
    private Button submit = new Button("Submit");

    public NewEmployeeForm(ArrayList<Employee> employees, java.awt.List employeeList){
        super("New employee");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.employeeClass.add("Hourly Employee");
        this.employeeClass.add("Salaried Employee");
        this.employeeClass.add("Executive Employee");
        
        this.department.add("Operations");
        this.department.add("Finance");
        this.department.add("Marketing");
        this.department.add("Human Resources");
        
        this.add(employeeClass);
        this.add(lName);
        this.add(name);
        this.add(lAge);
        this.add(age);
        this.add(lDepartment);
        this.add(department);
        this.add(lTitle);
        this.add(title);
        this.add(lWage);
        this.add(wage);
        this.add(submit);
        
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                String choice = employeeClass.getItem(employeeClass.getSelectedIndex());
                try{
                    if (choice == "Hourly Employee"){
                        employees.add(new HourlyEmployee(name.getText(), age.getText(), department.getSelectedItem(), title.getText(), wage.getText()));    
                    } else if (choice == "Salaried Employee"){
                        employees.add(new SalariedEmployee(name.getText(), age.getText(), department.getSelectedItem(), title.getText(), salary.getText()));    
                    } else if (choice == "Executive Employee"){
                        employees.add(new ExecutiveEmployee(name.getText(), age.getText(), department.getSelectedItem(), title.getText(), salary.getText(), bonus.getText()));     
                    }
                } catch (WrongDetailsException e){
                     new Alert("Invalid Input", e.getMessage());
                } finally {
                    employeeList.removeAll();
                    for (Employee employee : employees)
                        employeeList.add(employee.toString());
                    dispose();
                }
            }
        });
        
        employeeClass.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                String choice = employeeClass.getItem(employeeClass.getSelectedIndex());
                if (choice == "Salaried Employee"){
                    remove(lWage);
                    remove(wage);
                    add(lSalary);
                    add(salary);
                    remove(lBonus);
                    remove(bonus);
                    remove(submit);
                    add(submit);
                    pack();
                } else if (choice == "Executive Employee"){
                    remove(lWage);
                    remove(wage);
                    add(lSalary);
                    add(salary);
                    add(lBonus);
                    add(bonus);
                    remove(submit);
                    add(submit);
                    pack();
                } else if (choice == "Hourly Employee"){
                    add(lWage);
                    add(wage);
                    remove(lSalary);
                    remove(salary);
                    remove(lBonus);
                    remove(bonus);
                    remove(submit);
                    add(submit);
                    pack();
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
