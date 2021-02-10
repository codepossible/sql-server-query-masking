USE master;
  GO
  CREATE DATABASE ContactMaster;
  GO
  USE ContactMaster;
  GO
  CREATE TABLE Contacts(
    EmpID INT PRIMARY KEY,
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50) 
      MASKED WITH (FUNCTION = 'default()') NOT NULL,
    Birthdate DATE  
      MASKED WITH (FUNCTION = 'default()') NOT NULL,   
    Email VARCHAR(100) 
		MASKED WITH (FUNCTION = 'email()') NULL);
  GO