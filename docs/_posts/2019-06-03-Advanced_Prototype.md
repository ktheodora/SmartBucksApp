
# Advanced Prototype
![enter image description here](https://www.dashdevs.com/images/hero%20%281%29.jpg){:height="60%" width="60%"}

This blog post is for the next version of the SmartBucks which is built to enlighten your wallet in an efficient way. This blog contains the design pattern that we used for creating our application which were solved and proposed earlier by other developers, the coding conventions that were followed while creating the applicaiton, the context of use of the applicaiton with respect to the user personas, the design solutons that were used for building the user interface, along with that the summary of changes that were made from the basic prototype to the advanced prototype and finally we will be presenting you the working prototype of the application. 

## Design Pattern 
 
 In software engineering, commonly occuring problems can be reduced or solved using the resusable solutions which are called as the software design patterns. In the creation of the application there are many problems that we encoutered were able to solve with the help of the design patterns that were already developed by other developers. We had to use structural pattern and behaviour patterns in our development which will be desribed clearly below. 

### Creational Design Pattern:
-  **Prototype:**  Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.
- With the prototyping patter we created the object Expenses with the parameters mentioned as above which were used throughout the program whenever a new expense is created. 

### Structural Design Pattern:
- The Decorator (https://en.wikipedia.org/wiki/Decorator_pattern#cite_note-GoF-3)  design pattern is one of the twenty-three well-known _[GoF design patterns](https://en.wikipedia.org/wiki/Design_Patterns "Design Patterns")_ that describe how to solve recurring design problems to design flexible and reusable object-oriented software, that is, objects that are easier to implement, change, test, and reuse.
- In our application the recurrent objects such as the daily, weekly, monthly, and yearly overview of the expenses design problem was solved using the decorator design pattern, which helped us to reuse these objects throughout the application. 

### Behavioural Design Pattern 
- The **observer pattern** is a [software design pattern](https://en.wikipedia.org/wiki/Design_pattern_(computer_science) "Design pattern (computer science)") in which an [object](https://en.wikipedia.org/wiki/Object_(computer_science)#Objects_in_object-oriented_programming "Object (computer science)"), called the **subject**, maintains a list of its dependents, called **observers**, and notifies them automatically of any state changes, usually by calling one of their [methods](https://en.wikipedia.org/wiki/Method_(computer_science) "Method (computer science)").
- The Observer pattern addresses the following problems:
-   A one-to-many dependency between objects should be defined without making the objects tightly coupled.
-   It should be ensured that when one object changes state an open-ended number of dependent objects are updated automatically.
-   It should be possible that one object can notify an open-ended number of other objects.
- This was used in our application in the case where if the sum of the expenses was less than or equal to the budget specified then this would change/ affect other parameters created in the application. 

## Coding Conventions 

Being a software developer it is very important to follow the coding conventions for various reasons. Few of them includes the readability, ease of debugging and so on. 

The coding standards that we followed are described below.

1. Classes and methods 
- For class names corresponding to the back-end code of activities, we used the camelCase (https://en.wikipedia.org/wiki/Camel_case ) practice, where our classes' names practically contain the first part's letter starting with lowercase letter and the second part starting with uppercase (for example enterExpenses, updateDetails etc).<br/>
![Deadline image]({{site.baseurl}}/images/camelCase.jpeg "camelCase Naming"){:height="50%" width="50%"}
<br/>

- If the name of the activity is defined by a single word and not by multiple, then we add as a second part of the name the word 'activity' (for example mainActivity, loginActivity, preferencesActivity etc). 
-  For the classes names corresponding to object creation, we just named them after their specific object name, in our case the User and the Expenses classes.
2. Variables
- For variables names, we keep the letters lowercase, but depending on whether we want to declare a front end view or button, we add in the end of the variable the word 'view' or 'btn', so that we are able to always separate where the variable refers to. 

3. Clean coding conventions

- Another coding convention we applied is that we tried to separate the extra code needed for specific functions of each activity from its onCreate method , so that there is not a big confusion in too many lines of code for one function. For example, when validating data on the createAccount activity, we created the validate() and the addUser() functions that are called inside the onCreate, or in the updateDetail class we call the updated() function that controls the input and stores the updated user details in the database.

- Comments play an important coding convention in order to cooperate as a team for our coding comprehesion and the next steps. We try to have comments for describing a function's usage, or also when there's a more specific block of code performing a more detailed operation (e.g. variable declaration, checking variable values etc). Furthermore, the TODO comments are inserted when someone wants to pass one task to another indivudual, therefore the future coding work is organized and implemented more efficiently.
<br/>
![Deadline image]({{site.baseurl}}/images/comments.jpeg "Comments"){:height="50%" width="50%"}
<br/>
- Finally, there were gaps or white spaces provided whenever a new block of code is started which serves a new purpose than the other block. This makes the readability of the more easier. 
<br/>
![Deadline image]({{site.baseurl}}/images/gaps.jpeg "Gaps for New Block"){:height="50%" width="50%"}
<br/>

4. Annotaitons
- @Override for methods were used as the annotations. 

5. Formatting 
- Braces were added properly wherever necessary. 
- Indentation was used for easier radability and understanding of the functionality. 
	
##  Context of Use 

Any application that is created has a group of  target audience for the usage of the application. Our target audience tends to be any person with an income and expense. This demography can vary from college student to pensioner in the society. As this is a large crowd of audience we are mainly now focusing on the college students and the office-goers who has an income and multiple expenses for the obtained income which becomes a tedious job to have a track on them. 

To illustrate this we have created two user persona which will describe our target audience. 

### User 1 - Hannah Fischer

#### Demograhic info: 

- Female
- Age : 22
- Student 
- Otto von Guericke
- Magdeburg, Germany. 

### Hard Facts

Hannah is living in a small apartment in Magdeburg, Germany. She’s 22 years old, single, studies , and works as a waiter during her free time.

### Interests and Values

Hannah is a frequent traveler and interested in experiencing new cultures. She recently visited Spain and Portugal. 

She loves to read books at home at night as opposed to going out to bars. She does like to hang out with a small group of friends at home or at quiet coffee shops. She doesn’t care too much about looks and fashion. What matters to her is values and motivations.

In an average day, she tends to drink many cups of tea, and she usually cooks her own healthy dishes. She prefers organic food, however, she’s not always able to afford it.

### Computer, Internet and TV Use

Christie owns a MacBook Air, and a Google Pixel 2. She uses the internet for her studies to conduct the majority of her preliminary research and studies user reviews to help her decide upon which books to read and buy. Christie also streams all of her music and she watches movies online since she does not want to own a TV. She thinks TV’s are outdated and she does not want to waste her time watching TV shows, entertainment, documentaries, or news which she has not chosen and finds 100 % interesting herself.

### Frustations

This busy schedule does not allow Hannah to keep track of the expenses since she is a student and working part time. 
This makes her life miserable at times where she is not able to save enough for her fututre. 

### Goals

Hannah wants to manage all the travelling , studies and the job that she has. But managing the money around all these becomes quite difficult. So her main goal is to keep track of the expenses and the income with a budget that allows her to travel and study without any tension on the financial part. 

### User 2 - Noah Foster

#### Demograhic info: 

- Male
- Age : 29
- Junior Editor
- ABC News
- Magdeburg, Germany. 

### Hard Facts

Noah is living in a comfortable and sophasticated appartment  in Magdeburg, Germany. He's 29 years old, single and works as a Junior Editior in a  news agency named "ABC News".

### Interests and Values

Noah is interested in games and movies. He is interested in playing indoor, outdoor and board games. This includes computer games too. 

He loves to spend most of the time outside the office looking for news that interests him and the agency. In an average day, he tends to eat out mostly due to his work. 

### Computer, Internet and TV Use

Noah owns a Dell XPS 15, and a Samsung Galaxy Note 9. He uses the internet for his gaming, research and news. Noah also listens to music and uses Netflix for video streaming. His usage for computer is mostly for Editing and gaming. 

### Frustations

This lifestyle of work and games does not allow him to keep track of the expenses that he make. Which reflects in the bank balance that he has. No saving equals to no proper management of money. 

### Goals
To save money, keep track of the expenses then and there with the ease of technology. 


## Design solutions

The design of the application was mostly based on the ground fact that it must be easily usable and not complex. The basic prototype also followed the same prinicple and the advanced prototype was also built with the same concern. 

The three main objectives of the design are: 
	- Simple  
	- Intuitive 
	- Quick

These objectives were designed with the help of the following items. 

1. Navigation Toolbar 
- They were used to navigate within and out of the application. This ensured that the user can move smoothly in between the transactions. 

2. Navigation Drawer 
- This was effectively used as menu option for transaction of other activities.
<br/>
![Deadline image]({{site.baseurl}}/images/navDrawer.jpeg "Navigation Drawer"){:height="50%" width="50%"}
<br/>
3. Icons 
- They were used to make the application more intuitive for the users where they can easily understand the activity that needs to be done. 
<br/>
![Deadline image]({{site.baseurl}}/images/createAccountNew.jpeg "Usage of Icons"){:height="50%" width="50%"}
<br/>

4. Statisitcs 
- The graphs presented makes the visualization of the expenses more easy and understandable. They can be filtered with options such as weekly, monthly and daily expenses. 

The workflow of the application with respect to two use cases along with the screen shots are provided. 


## Summary of Changes

There were new features added to the advanced prototype when compared to the basic prototype. 

1. First, the user interface was completly changed with a dynamic view and integration of modern feel like components. 
2. The sign up activity and log in activity was integrated with the Database for user authorization. 
3. Addition of Graph for visualising the expenses. 
4. The filter option to view  the Daily, Weekly, Monthly and yearly expenses.
5. The summary of the Expenses that can be sent to the user as downloadable file. 

### User story   

There are changes in the user stories since there were new features added to the application.
<br/>

![Deadline image]({{site.baseurl}}/images/userStoryChange.jpeg "User Story"){:height="50%" width="50%"}
<br/>

One of the changes in the user story is the activity of the homepage where the expenses and savings can be tracked. 

### Class Diagram 
 
 In the class diagram the major changes are that the whenever the user account is created they are added in the database and whenever a user creates a new expense they are also stored locally in the database. 

The above mentioned are the major changes. 


## Working Prototype 

The advanced prototype of the SmartBucks can be downloaded in the provided link here.

### Screeenshots 

The screen shots of the advanced prototype is provided here for the reference. 
 







 
 



