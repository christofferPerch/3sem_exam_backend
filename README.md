**Follow the steps below to set up the start code correctly:**

1. Change the JDK version to 11 in your Project Structure.
2. Add a configuration - Tomcat Local Version 9.
3. Head to your pom.xml file and change the name of the project and its artifact id.
4. While you're in the pom.xml file you should also change the name of the database to the one you will be using.
5. Make sure to add your remote secrets to your GitHub project: REMOTE_PW and REMOTE_USER, otherwise you can't deploy the project.
6. In the mavenworkflow.yml file check that it uses the correct branch main/master.
7. Add a mysql datasource with the database you are going to use.
8. Head to persistence.xml and make sure it uses the correct database and has the right username and password.
9. You can now commit and push and head to GitHub actions, and hopefully it runs with no problems if you followed all the steps above.'

**End points:**

**Create User ( {"userName"  : "x" , "userPass" : "x" , "userEmail" : "x", 
"streetAddress" : "x", "zipCode": "x", "cityName" : "x"} )**
> POST /api/users

**Login ( {"username"  : "x" , "userpass" : "x"} )**
> POST /api/login

**Update a Users Password ( {"userpass" : "x"} )**
> PUT /api/users/{username}

**Add a user to a Training Session ( {"userName" : "x", "trainingSessionId" : "x" } )**
> POST /api/users/addTraining

**Remove a user to a Training Session ( {"userName" : "x", "trainingSessionId" : "x" } )**
> DELETE /api/users/addTraining

**Get Specific User**
> GET /api/users/{username}

**Get All Users**
> GET /api/users/all

**Delete a Specific User**
> DELETE /api/users/{username}

**Create Training Session ( {"title" : "x", "time" : "x", "fullAddress" : "x", "date" : "x", 
"maxParticipants" : "x", "category" : "x"} )** 
> POST /api/training

**Delete a Specific Training Session**
> DELETE /api/training/{trainingSessionId}

**Get Specific Training Session**
> GET /api/training/{trainingSessionId}

**Update a Training Session ( {"x" : "x", "x" : "x"} )**
> PUT /api/training/{trainingSessionId}