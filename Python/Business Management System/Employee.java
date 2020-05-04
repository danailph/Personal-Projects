// This is an abstract superclass describing a general employee

public abstract class Employee implements java.io.Serializable{
    protected String name;
    private int age;
    private String department;
    private String title;
    
    
    public Employee(String name, String age, String department, String title) throws WrongDetailsException{
        
        // Input validation using regular expresions
        if ( name.matches("[A-Za-z]+[A-Za-z ]*") && department.matches("[A-Za-z]+[A-Za-z ]*") && title.matches("[A-Za-z]+[A-Za-z ]*") ){
            this.name = name;
            this.department = department;
            this.title = title;
        }
        else throw new WrongDetailsException("Name, Department and title should contain only letters!");
        
        
        try {
            this.age = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            throw new WrongDetailsException("Age should be an integer!");
        }
    }
    
    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    public String getDepartment(){
        return this.department;
    }
    public String getTitle(){
        return this.title;
    }
    
    public String toString(){
        return "Name: " + this.name + "    Age: " + this.age + "   \nDepartment: " + this.department + "   Title: " + this.title;
    }
    
    // Less detailed represntation of an employee used for tha finance panel
    public abstract String toStringShort();
    
    // This method would be used for the polymorphism
    public abstract double calculatePay();
    // More detailed representation of the may describing how it is calculated
    public abstract String payToString();
}
