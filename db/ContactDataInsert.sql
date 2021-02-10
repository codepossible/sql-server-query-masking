INSERT INTO Contacts VALUES
(1, 'Steven', 'Smith', '12/1/1985', 'steven.smith@cricket.com.au'),
(2, 'Virat', 'Kholi', '05/11/1986', 'virat.kholi@bcci.in'),
(3, 'Rishab', 'Pant', '09/01/1997', 'rishab.pant@bcci.in'),
(4, 'Smriti', 'Mandhana', '10/01/1995', 'smiriti.mandhana@bcci.in')


select * from Contacts

GRANT SELECT ON Contacts TO testdde

EXECUTE AS User='testdde'
select * from Contacts

revert
select * from Contacts