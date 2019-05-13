![system_design](http://www.raintecirrigationsystems.com/wp-content/uploads/2010/12/irrigation-system-design.jpg)

# System Design 
 
 The application can only be created when there is a design available that address the whole process of development. This could be achieved by creating the use case diagram,user story, activity diagram and the class diagram. 
  
## Use case diagram
 
 The formulation of the requirement is done by using use case diagram and user stories.The use case diagram helps us to understand the workflow of the application with respect to the user.

![Use case diagram](https://github.com/DBSE-teaching/isee2019-SmartBucks/blob/master/docs/images/useCase.jpg)

The above diagram helps us to understand the various activities involved in the application.

<<"include">> relationship depicts the existential relationship between 2 activities/classes

<<"extends">> relationship depicts the non-existential relationship

Through Use case diagram we depict functionality of our application Money Control using actors, association and generalization.

Ideally User have access to all the functionalities of the application, if the user is new to our application, he will have to first Signup after clicking on Signup he will be routed towards Setup Account activity which cannot exist without Signup activity as they share include relationship between each other.
 
 Setup Account, User will be providing the details regarding his Monthly Income, Monthly Stable Expenses and provide us with basic personal details which will be stored and on the basis of these Information savings will be depicted in homepage, Information provided by the user will be validated by Admin before providing confirmation.

After Completing the Signup, User will be entering Uname and Passwd in Login activity which will be validated at the backed if the information matches then the user will be able to login, if user makes false attempts more than 3 time then he will not be able to login for current session .

After Successfully Logging In, user will be routed towards homepage where he can Add extra Income, Add Expenses apart from the stable one and could also check current savings .

## Activity Diagram
 
The activity diagrams helps us to understand the activity that a user performs at a particular phase in the application. The two activity diagram presented below represents about the signup activity of the user and the expense tracking activity of the user.

### Case 1 -Signup Activity
 
 ![signup activity](https://github.com/DBSE-teaching/isee2019-SmartBucks/blob/master/docs/images/ActivityDiagram-Page-1.jpg)

### Case 2 - Expense Tracking Activity 

![expense tracking activity](https://github.com/DBSE-teaching/isee2019-SmartBucks/blob/master/docs/images/ActivityDiagram-Page-2-3.jpg)

## User Stories

The user stories are used to understand the different scenarios of the application.

![user story](https://github.com/DBSE-teaching/isee2019-SmartBucks/blob/master/docs/images/userStory.png)


## Class Diagram

Class diagram represents the static view of our application Money Control. Class diagram is not only used for visualizing, describing, and documenting different aspects of a system but also for constructing executable code of the software application.

 ![class diagram](https://github.com/DBSE-teaching/isee2019-SmartBucks/blob/master/docs/images/classDiagram.jpg)

Our Application mainly consists of 10 classes.

1.  User Class: consists of private attributes and all the functions are public. User will be providing the Id and Pwd while logging.
    
2.  Setup Account: As explained in our Use case diagram , we have dedicated function for user first time login where he will be setting up account , Multiplicity between User class and Setup Account is set to be (1..* to 1) which mainly means that there can be n numbers of user which will be having only one account 1 account which they will be setting up.
    
3.  Setup Account being the parent class having 3 child classes (userpersonalInfo, stableExpenses, monthlyIncome), child classes will be inheriting all the features from the parent class.
    
4.  Multiplicity between Admin and User is set to be (0...* to 4) as our initial prototype of our application is supported by 4 team mates only.
    
5.  Admin Class will be having access to Manage account function, where admin will be having access to all the classes of our application so that he can provide assistance to the user wherever it is needed
    
6.  Homepage class consist of three child classes (bonusIncome, Expenses, Savings) which are having aggregation relationship with the parent node.
7. bonusIncome class allows user to add up additional income earned in the month.
8. Expenses class allows user to add up the expenses apart from the stable expenses.
9. Savings class allows the user to view current monthly savings.
 
## Summary
 
 The system modelling will help in the basic prototyping of the application. Please look out to the next blog post for the Basic prototype of the Smart Bucks applicaiton.


