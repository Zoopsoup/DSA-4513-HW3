---Get rid of the procedues if they exist---
DROP PROCEDURE IF EXISTS sp_1;
DROP PROCEDURE IF EXISTS sp_2;
---Signal  the start of the procedure---
GO
---Create Transact-SQL stored procedure for the first insertion case
CREATE PROCEDURE sp_1
---Declares pid, name, and age local parameters of the procedure 
    @pid INT,
    @name VARCHAR(64),
    @age INT
AS
---Begins Transact-SQL statement block
BEGIN
    ---Declares local parameter of the SQL statement for average years of experience
    DECLARE @avg_years_of_experience INT;
    ---Sets average years of experience to the average of years of experience for all performers with an age within +- 10 years of the new performers age
    SET @avg_years_of_experience = (SELECT AVG(years_of_experience) FROM Performer WHERE age < (@age + 10) AND age > (@age - 10));
    ---Checks if years of experience is more than the performers age
    IF @avg_years_of_experience > @age
    ---If so, sets it to be the performers age
        SET @avg_years_of_experience = @age
    ---If years of experience is at least 0 and assigned a value, insert it
    IF @avg_years_of_experience >= 0
        INSERT INTO Performer VALUES (@pid,@name,@avg_years_of_experience,@age);
    ---If we did not assign a value, check if age is at least 18, then set set to 18 less than age value
    ELSE IF @age >= 18
        INSERT INTO Performer VALUES (@pid,@name,@age-18,@age);
    ---Else set years of experience to 0
    ELSE 
        INSERT INTO Performer VALUES (@pid,@name,0,@age);
---Ends Transact-SQL statement block
END

GO
---Create Transact-SQL stored procedure for the second insertion case
CREATE PROCEDURE sp_2
---Declares pid, name, age, and did local parameters of the procedure 
    @pid INT,
    @name VARCHAR(64),
    @age INT,
    @did INT
AS
---Begins Transact-SQL statement block
BEGIN
---Declares local parameter of the SQL statement for average years of experience
    DECLARE @avg_years_of_experience INT;
    ---Sets average years of experience to the average of years of experience for all performers who have acted in a movie that was directed by a director with the did
    SET @avg_years_of_experience = (SELECT AVG(years_of_experience) FROM Performer WHERE EXISTS(SELECT pid FROM Acted WHERE EXISTS(SELECT mname FROM Director WHERE did = @did)));
    ---Checks if years of experience is more than the performers age
    IF @avg_years_of_experience > @age
    ---If so, sets it to be the performers age
        SET @avg_years_of_experience = @age
    ---If years of experience is at least 0 and assigned a value, insert it
    IF @avg_years_of_experience >= 0
        INSERT INTO Performer VALUES (@pid,@name,@avg_years_of_experience,@age);
    ---If we did not assign a value, check if age is at least 18, then set set to 18 less than age value
    ELSE IF @age >= 18
        INSERT INTO Performer VALUES (@pid,@name,@age-18,@age);
    ---Else set years of experience to 0
    ELSE 
        INSERT INTO Performer VALUES (@pid,@name,0,@age);
---Ends Transact-SQL statement block
END