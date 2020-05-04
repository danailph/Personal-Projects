// This class creates sample employees, clients and orders and exports them to a file

import java.util.*;
public class NewTestData{
    public static void main(String[] args) throws WrongDetailsException{
        ArrayList<Employee> employees = new ArrayList<Employee>(); 
        employees.add(new ExecutiveEmployee("Danail", "19", "Finance", "Director", "6000", "5"));
        employees.add(new SalariedEmployee("Simeon", "20", "Marketing", "Team Manager", "4000"));
        employees.add(new HourlyEmployee("Magi", "20", "Finance", "Accountant", "18"));
        
        HourlyEmployee e2 = (HourlyEmployee) employees.get(2);
        e2.setHoursWorked(10);
        
        ArrayList<Client> clients = new ArrayList<Client>();
        clients.add(new Client("QMUL", "0987654321"));
        
        ArrayList<Order> orders = new ArrayList<Order>();
        orders.add(new Order("Medical masks", "2000", "2", "20/04/2020", clients.get(0),((ExecutiveEmployee) employees.get(0))));
        
        IO.save(employees, clients, orders);
    }
}
