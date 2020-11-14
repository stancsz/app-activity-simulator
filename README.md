# A tool for project alpaca realtime streaming with Alpakka
Purpose of this program:

This program reads a csv file, then stream it to a mysql db. 


current progress

- [x] able to read csv and print to console
 
- [x] put use insert statements to dump the data to mysql db



Able to stream a csv file to a database with configured delay. currently this is rough prototype retrofitted from one of my school assignment. 

to see how it work, build & run 
```
src/test/java/AppActivitySimulatorTest.java
```


the program run time displays the sql payload being sent:
```sql
2020-11-14 19:17:07.409 log: Tested connection successful
2020-11-14 19:17:08.815 log: a db connection is about to be made to: 	jdbc:mysql://localhost:3306/mysql
2020-11-14 19:17:08.815 log: The driver name is MySQL Connector/J
2020-11-14 19:17:08.815 log: Tested connection successful
INSERT INTO `monroe-county-crash-data2003-to-2015` values(902363382,2015,1,5,'Weekday',0,'2-Car','No injury/unknown','OTHER (DRIVER) - EXPLAIN IN NARRATIVE','1ST & FESS',39.15920668,-86.52587356);
INSERT INTO `monroe-county-crash-data2003-to-2015` values(902364268,2015,1,6,'Weekday',1500,'2-Car','No injury/unknown','FOLLOWING TOO CLOSELY','2ND & COLLEGE',39.16144,-86.534848);
INSERT INTO `monroe-county-crash-data2003-to-2015` values(902364412,2015,1,6,'Weekend',2300,'2-Car','Non-incapacitating','DISREGARD SIGNAL/REG SIGN','BASSWOOD & BLOOMFIELD',39.14978027,-86.56889006);
INSERT INTO `monroe-county-crash-data2003-to-2015` values(902364551,2015,1,7,'Weekend',900,'2-Car','Non-incapacitating','FAILURE TO YIELD RIGHT OF WAY','GATES & JACOBS',39.165655,-86.57595635);
...
```

generated data in the sql:
```sql
select * from `monroe-county-crash-data2003-to-2015`;
```

```text
902363382,2015,1,5,Weekday,0,2-Car,No injury/unknown,OTHER (DRIVER) - EXPLAIN IN NARRATIVE,1ST & FESS,39.1592,-86.5259
902364268,2015,1,6,Weekday,1500,2-Car,No injury/unknown,FOLLOWING TOO CLOSELY,2ND & COLLEGE,39.1614,-86.5349
902364412,2015,1,6,Weekend,2300,2-Car,Non-incapacitating,DISREGARD SIGNAL/REG SIGN,BASSWOOD & BLOOMFIELD,39.1498,-86.5689
902364551,2015,1,7,Weekend,900,2-Car,Non-incapacitating,FAILURE TO YIELD RIGHT OF WAY,GATES & JACOBS,39.1657,-86.576
902364615,2015,1,7,Weekend,1100,2-Car,No injury/unknown,FAILURE TO YIELD RIGHT OF WAY,W 3RD,39.1648,-86.5796
902364664,2015,1,6,Weekday,1800,2-Car,No injury/unknown,FAILURE TO YIELD RIGHT OF WAY,BURKS & WALNUT,39.1267,-86.5314
902364682,2015,1,6,Weekday,1200,2-Car,No injury/unknown,DRIVER DISTRACTED - EXPLAIN IN NARRATIVE,SOUTH CURRY PIKE LOT 71,39.1508,-86.5849
902364683,2015,1,6,Weekday,1400,1-Car,Incapacitating,ENGINE FAILURE OR DEFECTIVE,NORTH LOUDEN RD,39.1993,-86.637
902364714,2015,1,7,Weekend,1400,2-Car,No injury/unknown,FOLLOWING TOO CLOSELY,LIBERTY & W 3RD,39.1646,-86.5791
902364756,2015,1,7,Weekend,1600,1-Car,No injury/unknown,RAN OFF ROAD RIGHT,PATTERSON & W 3RD,39.1634,-86.5513
902364761,2015,1,7,Weekend,1500,2-Car,No injury/unknown,UNSAFE BACKING,S LIBERTY,39.1453,-86.5776
```

