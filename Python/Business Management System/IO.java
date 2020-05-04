// This class is used for file input/output to save the WHOLE state of the porgram 

import java.io.*;
import java.util.*;

public class IO{
    public static void load(ArrayList<Employee> employees, ArrayList<Client> clients, ArrayList<Order> orders){
        try{
            FileInputStream fileInput = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            
            //Import Employees
            int numberOfEmployees = objectInput.readInt();
            for (int i=0; i<numberOfEmployees; i++)
                employees.add((Employee) objectInput.readObject());
                
            //Import Clients
            int numberOfClients = objectInput.readInt();
            for (int i=0; i<numberOfClients; i++)
                clients.add((Client) objectInput.readObject());
                
            //Import Orders
            int numberOfOrders = objectInput.readInt();
            for (int i=0; i<numberOfOrders; i++)
                orders.add((Order) objectInput.readObject());
                
            objectInput.close();
            fileInput.close();
            } catch (EOFException e) {
                new Alert("Error", e.getMessage());
            } catch (ClassNotFoundException e) {
                new Alert("Error", e.getMessage());
            }catch (IOException e) {
                new Alert("Error", e.getMessage());
        }
    }
        
    public static void save(ArrayList<Employee> employees, ArrayList<Client> clients, ArrayList<Order> orders){
        try{
            FileOutputStream fileOutput = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

            //Export Employees
            objectOutput.writeInt(employees.size());
            for (Employee employee: employees){
                objectOutput.writeObject(employee);
            }
            
            //Export Clients
            objectOutput.writeInt(clients.size());
            for (Client client: clients){
                objectOutput.writeObject(client);
            }
            
            //Export Orders
            objectOutput.writeInt(orders.size());
            for (Order order: orders){
                objectOutput.writeObject(order);
            }
            
            objectOutput.close();
            fileOutput.close();
        } catch (FileNotFoundException e) {
            new Alert("Error", e.getMessage());
        } catch (IOException e) {
            new Alert("Error", e.getMessage());
        }
    }
}
