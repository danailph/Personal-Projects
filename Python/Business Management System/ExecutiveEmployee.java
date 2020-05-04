/** This calss describes an Executive employee. Every order of the business is
 * assosiated with an Executive. There pay is a combination of a basic salary
 * plus a percentage of the value of the orders they manage;
*/

public class ExecutiveEmployee extends SalariedEmployee{
    private double percentageBonus;
    private double valueOfOrders;
    
    public ExecutiveEmployee(String name, String age, String department, String title, String salary, String percentageBonus)throws WrongDetailsException{
        super(name, age, department, title, salary);
        try{
            double value = Double.parseDouble(percentageBonus);
            if (value > 0 && value < 100)
                this.percentageBonus = value;
            else
                throw new WrongDetailsException("Percentage bonus should be between 0 and 100!");
            this.valueOfOrders = 0;
        } catch (NumberFormatException e){
            throw new WrongDetailsException("Percentage bonus should be a number!");
        }
    }
    
    public void increaseValueOfOrders(double valueOfOrders){
        this.valueOfOrders += valueOfOrders;
    }
    
    //Overriden methods
    public double calculatePay(){
        return this.salary + this.valueOfOrders*(this.percentageBonus/100);
    }
    
    public String payToString(){
        return super.payToString() + " + Value Of Orders: " + valueOfOrders*(this.percentageBonus/100) + " = " + this.calculatePay();
    }
    public String toString(){
        return "Executive employee:\n " + super.toString().substring(19);
    }
    public String toStringShort(){
        return "Executive employee:\n " + super.toStringShort().substring(19);
    }
}
