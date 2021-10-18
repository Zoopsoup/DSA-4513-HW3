---1.1---
---a)
INSERT INTO Movie VALUES 
('Jurassic Park','Action',125,1984,2,3.5);
---b)
INSERT INTO Movie VALUES
(NULL,'Action',125,1984,2,3.5);
---c)
INSERT INTO Movie VALUES
('The Avengers','Action',125,1984,5,3.5);
DELETE FROM Director WHERE did = 1;

UPDATE Movie SET did = 5;
---d)
SELECT dname FROM Director WHERE dname = 5;


---1.2---
---We chose the director id in the movies table as our search key for indexing as a secondary index.---
---The movies table has the most attributes of all of the tables in our database.---
---This would allow us to see the data sorted by the director id and easily see which directors are associated to each movie.---
---Therefore, an index for movies would benefit in searching on our database. Below is a screenshot for creation of the index.---

CREATE INDEX index_did ON Movie (did)

---We re-ran the following query since it includes the did attribute under Movie.---
---QUERY to Rerun---

UPDATE Director SET earnings = earnings * 0.9 WHERE did = (SELECT did FROM Movie WITH(INDEX(index_did)) WHERE mname = 'Up');
