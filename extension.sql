DROP PROCEDURE IF EXISTS sp_1;
GO
CREATE PROCEDURE sp_1
    @pid INT,
    @name VARCHAR(64),
    @age INT
AS
BEGIN
    DECLARE @avg_years_of_experience INT;
    SET @avg_years_of_experience = (SELECT AVG(years_of_experience) FROM Performer WHERE age < (@age + 10) AND age > (@age - 10));
    IF @avg_years_of_experience > @age
        SET @avg_years_of_experience = @age
    IF @avg_years_of_experience > 0
        INSERT INTO Performer VALUES (@pid,@name,@avg_years_of_experience,@age);
    ELSE IF @age >= 18
        INSERT INTO Performer VALUES (@pid,@name,@age-18,@age);
    ELSE 
        INSERT INTO Performer VALUES (@pid,@name,0,@age);;
END
DROP PROCEDURE IF EXISTS sp_2;
GO
CREATE PROCEDURE sp_2
    @pid INT,
    @name VARCHAR(64),
    @age INT,
    @did INT
AS
BEGIN
    DECLARE @avg_years_of_experience INT;
    SET @avg_years_of_experience = (SELECT AVG(years_of_experience) FROM Performer WHERE EXISTS(SELECT pid FROM Acted WHERE EXISTS(SELECT mname FROM Director WHERE did = @did)));
    IF @avg_years_of_experience > @age
        SET @avg_years_of_experience = @age
    IF @avg_years_of_experience > 0
        INSERT INTO Performer VALUES (@pid,@name,@avg_years_of_experience,@age);
    ELSE IF @age >= 18
        INSERT INTO Performer VALUES (@pid,@name,@age-18,@age);
    ELSE 
        INSERT INTO Performer VALUES (@pid,@name,0,@age);
END