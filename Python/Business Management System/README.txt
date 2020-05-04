Student name: Danail Todorov
Student number: 190306579

LEVEL YOU WISH THE MINI-PROJECT TO BE MARKED FOR: 3

Now complete the statements below for each level you wish to be marked. Replace all text in square brackets.

LEVEL ONE

My code demonstrates inheritance in the following way...

I have a superclass called [Employee]

This superclass is extended into at least three subclasses called [HourlyEmployee, SalariedEmployee, ExecutiveEmployee]

For each of the named subclasses complete the following...

Subclass 1.

Subclass [HourlyEmployee] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [hourlyWage, hoursWorked]

These/this new properties/property are used by the subclass in the following way: [These properties are used to calculate the pay (wage x hours)]

Subclass [HourlyEmployee] extends the superclass by overriding the following methods (there must be at least one): [calculatePay(line 30), multiple toString methods(lines 34, 37, 40)]

These overridden methods are used in the working code in the following places: [file GuiFinance, lines 21,45	file GuiEmployees, line 20]

Subclass 2.

Subclass [SalariedEmployee] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [salary]

These/this new properties/property are used by the subclass in the following way: [This property is used to calculate the pay (monthly salary)]

Subclass [SalariedEmployee] extends the superclass by overriding the following methods (there must be at least one): [calculatePay(line 16), multiple toString methods(lines 20, 23, 24)]

!These overridden methods are used in the working code in the following places: [file GuiFinance, lines 21,45	file GuiEmployees, line 20]

Subclass 3.

Subclass [ExecutiveEmployee] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [percentageBonus, valueOfOrders]

These/this new properties/property are used by the subclass in the following way: [ Every order of the business is associated with an Executive. There pay is a combination of a basic salary 
plus a percentage of the value of the orders they manage. These properties are used to calculate the pay (basic salary + valueOfOrders*percentageBonus]

Subclass [ExecutiveEmployee] extends the superclass by overriding the following methods (there must be at least one): [calculatePay(line 29), multiple toStringMethods(lines 33, 36, 39]

!These overridden methods are used in the working code in the following places: [file GuiFinance, lines 21,45	file GuiEmployees, line 20]

[If you have more than three subclasses you want to tell us about, copy and paste the above text for as many subclasses as you want.]

LEVEL TWO

[Substitution allows us to declare an object to its super type and initialise it to its actual type. On a method call, the java compiler does not know which exact method to execute but it is
decided by the JVM at run-time (Dynamic binding)]

[file GuiFinance, lines 21,45	file GuiEmployees, line 20]

[Polymorphism allows me to store all different types of employees in a single array/arrayList. Therefore, I could call the method calculatePay on each Employee object in the array/arrayList 
and the JVM will bind the correct method to be used. Without this feature, I would have needed different arrays/arrayLists to store the different types of employees.]

LEVEL THREE

[Buttons to change the Panel in the main GUI (file GuiMenu, lines 12,13,14);
Buttons to create new objects Employees(file NewEmployeeForm, line 52), Clients(file NewClientForm, line 27), Orders(file NewOrderForm, line 47);
Buttons to change the state of an object (file OrderStatusChangePrompt, line 24), (file SetHoursPrompt, line 27);
Buttons to display objects - various toString methods (file GuiOrders, line 57), (file GuiFinance, lines 41, 51);
Button to promote an employee - get instance fields of one object and create a new one using them plus the additional ones needed (file PromotionPrompt, line 27);
Button to remove an employee from the arrayList - object deleted when application is closed (file GuiEmployees, line 32) ]

[ file WrongDetailsException; file IO, lines 8,39; file Employee, lines 18, 21; file HourlyEmployee, line 11; file SalariedEmployee, line 8;
File ExecutiveEmployee, line 12; file Client, line 12; file Order, line 17; line NewOrderForm, line 49; file SetHoursPrompt, line 31; file NewEmployeeForm, line 55; 
File NewClientForm, line 29; file PromotionPrompt, line 30]

[All objects are stored in three arrayLists(Employee, Client, Order). The arrayLists are passed to the GUI and buttons and textFields are used to manipulate them.
New objects are added to the arrayLists. If an employee is fired, its object is removed from the arrayList and deleted when the program is closed. 
(File Main, lines 8,9,10), (file IO, lines, 15,20,25 and lines 45,51,57), () ]

[There is a class IO with two static methods save and load. When the program starts the load method is passed three arrayLists(employees, clients, orders) and they are populated from 
a file called "myObjects.txt". Then the arrayLists are passed to the GUI. When the program is closed by the user the method save is passed the arrayLists and "myObjects.txt" is
overridden and all existing, modified and new objects are saved. The file "myObjects" keeps track of the number of object. 
(File Main, line 11), (file GuiMain, line 24)]

ANYTHING ELSE

[ All interactions happen trough the GUI. No console is used. This allows me to constrain the user from entering wrong/erroneous data;
File NewTestData has only a main method which initialises some objects and saves them to "myObjects.txt". This allows for testing/demonstrating the system easily; 
Exception handling is used for data validation and if wrong data is entered a pop-up window displays the error;
]
