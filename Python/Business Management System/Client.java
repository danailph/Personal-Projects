// This class describes a client of the business

public class Client implements java.io.Serializable{
    private String name;
    private String contactNumber;
    
    public Client(String name, String contactNumber) throws WrongDetailsException{
        this.name = name;
        if (contactNumber.matches("[0]{1}[0-9]{9}"))
            this.contactNumber = contactNumber;
        else
            throw new WrongDetailsException("Phone number should be in format [0]{1}[0-9]{9}");
    }
    
    public String getName(){
        return this.name;
    }
    public String getContactNumber(){
        return this.contactNumber;
    }
    
    public String toString(){
        return this.name + "    Contact number: " + this.contactNumber;
    }
}
