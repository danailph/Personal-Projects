// This class describes an employee payed a salary 

public class SalariedEmployee extends Employee{
    protected double salary;
    
    public SalariedEmployee(String name, String age, String department, String title, String salary) throws WrongDetailsException{
        super(name, age, department, title);
        try{
            this.salary = Double.parseDouble(salary);
        } catch (NumberFormatException e){
            throw new WrongDetailsException("Salary should be a number!");
        }
    }
    
    //Overriden methods
    public double calculatePay(){
        return this.salary;
    }
    
    public String payToString(){
        return "Basic salary: " + this.salary;
    }
    public String toString(){
        return "Salaried Employee:\n " + super.toString();
    }
    public String toStringShort(){
        return "Salaried Employee:\n " + this.name;
    }
}
