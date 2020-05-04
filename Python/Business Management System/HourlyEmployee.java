// This class describes an employee payed by the hour


public final class HourlyEmployee extends Employee{
    private double hourlyWage;
    private double hoursWorked;


    public HourlyEmployee(String name, String age, String department, String title, String hourlyWage) throws WrongDetailsException{
        super(name, age, department, title);
        try{
            this.hourlyWage = Double.parseDouble(hourlyWage);
        } catch (NumberFormatException e){
            throw new WrongDetailsException("Wage should be a number!");
        }
        this.hoursWorked = 0;
    }

    public double getHourlyWage(){
        return this.hourlyWage;
    }
    public double getHoursWorked(){
        return this.hoursWorked;
    }
    public void setHoursWorked(double hours){
        this.hoursWorked = hours;
    }
    
    //Overriden methods
    public double calculatePay(){
        return Math.round((this.hourlyWage*this.hoursWorked) * 100.0)/ 100.0;
    }
    
    public String payToString(){
        return "Hours: " + this.hoursWorked + " X   Wage: " + this.hourlyWage + " = " + this.calculatePay();
    }
    public String toString(){
        return "Hourly Employee:\n " + super.toString();
    }
    public String toStringShort(){
        return "Hourly Employee:\n " + this.name;
    }
}
