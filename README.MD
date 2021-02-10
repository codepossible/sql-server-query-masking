# Executing Queries as a different user than one logged on using JDBC

## Introduction

The objective of this code is to run a query on Microsoft SQL Server using JDBC as a different user than one used to log on to the database server. The primary reason for this exercise is to receive masked data for the user who might be using the application without switching connections.

## Pre-requisites

For this code to successfully run, we need the following:

- Instance of Microsoft SQL Server (preferably the latest ... :)). This code was tested with a docker image of SQL Server 2019. You will find the instruction to download and run the image on this link of [DockerHub](https://hub.docker.com/_/microsoft-mssql-server) and a more detailed version is available on [Microsoft Docs](https://docs.microsoft.com/en-us/sql/linux/quickstart-install-connect-docker?view=sql-server-linux-ver15&pivots=cs1-bash#pullandrun2019)

- Microsoft SQL Server Management Studio (SSMS, preferred) or a database client of your choice.

- Java Development Kit (Version 8 or higher). This code was built and tested using OpenJDK Version 12.0, but may work with lower versions.

- Maven

- Visual Studio Code (preferred) with Java Extensions (recommended) or a IDE of your choice.

## Creating and Setting up the database and users (other than sa)

In this exercise, we will create a database called - "ContactMaster", with a single table - "Contacts", with masking on some of the columns. The SQL code is available in the "db" subfolder.

Logging in as the server administrator (sa), for this exercise, run the following code:

``` sql
USE master;
GO

CREATE DATABASE ContactMaster;
GO

USE ContactMaster;
GO

CREATE TABLE Contacts(
    ContactID INT PRIMARY KEY,
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50)
      MASKED WITH (FUNCTION = 'default()') NOT NULL,
    BirthDate DATE  
      MASKED WITH (FUNCTION = 'default()') NOT NULL,
    Email VARCHAR(100) 
      MASKED WITH (FUNCTION = 'email()') NULL);
  GO
```

Then populate the table with some sample data. I selected the names of my favorite cricket players. (Fake birthdays and e-mails, unfortunately)

``` sql
INSERT INTO Contacts VALUES
(1, 'Steven', 'Smith', '12/1/1985', 'steven.smith@cricket.com.au'),
(2, 'Virat', 'Kholi', '05/11/1986', 'virat.kholi@bcci.in'),
(3, 'Rishab', 'Pant', '09/01/1997', 'rishab.pant@bcci.in'),
(4, 'Smriti', 'Mandhana', '10/01/1995', 'smiriti.mandhana@bcci.in')

```

As a next step, create another uses - "testdde" to the server, added the user to the ContactMaster database. 

Once the user is in the database, GRANT the user SELECT permission on the Contacts table.

``` sql
GRANT SELECT ON Contacts TO testdde
```

Once that is done, Open the source code file _App.java_ in your editor. Modify ```line 23``` to reflect the credentials of the user, you would like to use to log on the server in the application.

Run the code and see the output return as masked.

``` bash
Connecting to the database...
Reteriving list of contacts...

First Name      Last Name
Steven          xxxx
Virat           xxxx
Rishab          xxxx
Smriti          xxxx
```

Modify the SQL code on ```line 26``` to

``` java
String SQL = "SELECT * FROM dbo.Contacts";
```

Run the code and see the output return as unmasked.

``` bash
Connecting to the database...
Reteriving list of contacts...

First Name      Last Name
Steven          Smith
Virat           Kholi
Rishab          Pant
Smriti          Mandhana
```

## Conclusion

As you can see injecting the ```EXECUTE AS <user>``` statement allows the SQL Server to modify the user context and return the output based on the permissions of the specified user and now, you can enable that through Java.

## References

- [Microsoft Docs: JDBC - Handling Complex Statements](https://docs.microsoft.com/en-us/sql/connect/jdbc/handling-complex-statements?view=sql-server-2017)
- [Microsoft Docs: T-SQL - GRANT command](https://docs.microsoft.com/en-us/sql/t-sql/statements/grant-transact-sql?view=sql-server-2017)
- [Microsoft Docs: T-SQL - EXECUTE AS command](https://docs.microsoft.com/en-us/sql/t-sql/statements/execute-as-transact-sql?view=sql-server-2017)
- [Microsoft Docs: SQL Server - Dynamic Data Masking](https://docs.microsoft.com/en-us/sql/relational-databases/security/dynamic-data-masking?view=sql-server-2017)
- [Red Hat: SQL Server - Dynamic Data Masking](https://www.red-gate.com/simple-talk/sql/sql-development/encrypting-sql-server-dynamic-data-masking/)
- [Microsoft Docs: JDBC - Connection Sample](https://docs.microsoft.com/en-us/sql/connect/jdbc/connection-url-sample?view=sql-server-2017)

