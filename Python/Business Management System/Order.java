// This class describes an order for the business

import java.util.*;
import java.text.*; //Used for time formating
public class Order implements java.io.Serializable{
    private String description;
    private int amount;
    private double price;
    private String datePlaced;
    private String deadline;
    private String status;
    private ExecutiveEmployee orderManager;
    private Client client;
    
    public Order(String description, String amount, String price, String deadline, Client client, ExecutiveEmployee orderManager) throws WrongDetailsException{
        if (description.matches("[A-Za-z]+[A-Za-z ]*"))
            this.description = description;
        else 
            throw  new WrongDetailsException("Desccription should contain only letters!");
        try{
            this.amount = Integer.parseInt(amount);
            try{
                this.price = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                throw new WrongDetailsException("Price should be a number!");
            }
        } catch (NumberFormatException e){
            throw new WrongDetailsException("Amount should be an integer!");
        }
        this.datePlaced = (new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        if (deadline.matches("[0-9]{2}[/][0-9]{2}/[0-9]{4}"))
            this.deadline = deadline;
        else
            throw new WrongDetailsException("Date should be in format dd/mm/yyyy!");
        this.status = "On Going";
        this.orderManager = orderManager;
        orderManager.increaseValueOfOrders(Integer.parseInt(amount)*Double.parseDouble(price));
        this.client = client;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    public Employee getOrderManager(){
        return this.orderManager;
    }
    public double getValue(){
        return amount*price;
    }
    
    public String toString(){
        return "Item: " + this.description + "   Amount: " + this.amount + "pcs   Price: " + this.price + "£\nDate placed: " + this.datePlaced + "   Deadline: " + this.deadline + "   Status: " + this.status+"\nClient: " + this.client.getName() + "   Order Manager: " + this.orderManager.getName();
    }
    public String toStringShort(){
        return "Item: " + this.description + "   Status: " + this.status + "   Amount: " + this.amount + "pcs   Client: " + this.client.getName();
    }
}
