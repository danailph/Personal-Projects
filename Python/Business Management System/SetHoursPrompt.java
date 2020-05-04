// This is an input prompt to set the hours for hourly payed employees

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class SetHoursPrompt extends Frame{
    private Button submit = new Button("Submit");
    private ArrayList<TextField> values = new ArrayList<>();

    public SetHoursPrompt(ArrayList<Employee> employees,ArrayList<Order> orders, java.awt.List employeeList){
        super("Insert hourse worked in last month");
        this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
    
        for (int i=0; i<employees.size(); i++){
            Employee employee = employees.get(i);
            if (employee instanceof HourlyEmployee){
                this.add(new Label(employee.getName()));
                TextField input = new TextField(String.valueOf(((HourlyEmployee)employee).getHoursWorked()));
                input.setName(String.valueOf(i));
                this.values.add(input);
                this.add(input);
            }
        }
        this.add(submit);
        
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                for (TextField value : values){
                    HourlyEmployee employee = (HourlyEmployee) employees.get(Integer.parseInt(value.getName()));
                    try { 
                        employee.setHoursWorked(Double.parseDouble(value.getText()));
                    } catch (NumberFormatException e){
                        new Alert("Invalid Input", "Hours should be a number");
                    }
                }
                employeeList.removeAll();
                for (Employee employee : employees)
                    employeeList.add(employee.toStringShort()+": "+employee.calculatePay()+"£");
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
