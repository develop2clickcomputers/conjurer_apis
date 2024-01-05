This branch is created from conjurer on 28-Feb-2019 

# Conjurer Application(Conjurer_Apis)

This Project provide REST API's for Conjurer Platform. Conjurer platform is finance management system.

##Notes

>- In this project various modules **Overview, Transaction, Budget, Investment, Repository, Insurance, Advisors**.

>- This project also used **Auth_Apis** for Login.

>- **JWTFilter.java** is used for all type of authentication and authorization for all API's. 

>- This project also have functionality to upload PDF statement and extract information from statement that was store in Conjurer database.

>- In this project we also used categorization engine(Repository: "conjurer", Branch:"pimoney_services-2.0.3_advisor") for categorization of transaction description.

>- In this project, we also used auctor services(API's) for advisor module.

## Log files
There will be two kinds of logs:

		* Application log (~/logs/conjurerlog/script.log)
		
Please check doc files for more detail about the project.

## Creating a JAR

For creating a JAR first download or clone this project.
Then change the values in application.proprties file present in src/main/resources

		server.port = 11011
default port is 11011 but you can change this

        spring.datasource.url=jdbc:mysql://localhost:3306/pimoney
		spring.datasource.username=root
		datasource.parser.password=123
The above configuration is for MySQL connection. Assign mysql server url, username and password for mysql user

After this we are ready to create a JAR. We have two options for creating JAR

### Using STS(Spring tool suite)
This is a great tool for development of spring related projects.
Download it from [here](https://spring.io/tools)
    
        Import the project into STS. 
        Right Click on the project and select Run As->Maven build->enter 'clean install' in goals field->Run
        After that jar will be created in the target folder.
    
### Using mvn tool

        Install Maven from https://maven.apache.org/install.html
        run command "mvn clean install" from the root folder of the project.
    
 
After the JAR is created we can run this on server. For that we will need to setup our server.

## Server setup(Ubuntu 18.04)

        sudo apt update

### Install JAVA
		sudo apt install default-jre
		sudo apt install default-jdk
		
### Install MYSQL
		sudo apt update
		sudo apt install mysql-server
		sudo mysql_secure_installation
you can also follow this article [here](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-18-04)

Now create a directory and put your jar inside the directory and 
then run run.sh file. Change the paths and port in run.sh file accordingly before running.

### URLS
http://localhost:11011/auth/aurbatao    
	

