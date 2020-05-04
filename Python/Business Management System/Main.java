// This is the main class to start the system


import java.util.*;
public class Main{
    public static void main(String[] args){
        
    ArrayList<Employee> employees = new ArrayList<Employee>();
    ArrayList<Client> clients = new ArrayList<Client>();
    ArrayList<Order> orders = new ArrayList<Order>();
    IO.load(employees, clients, orders);
        
    GuiMain gui = new GuiMain("QManufacturing", employees, clients, orders);
    
    
    }
}
