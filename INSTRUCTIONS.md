Java Developer Test - Setup Instruction
========================================

Execution of maven project
---------------------------
On execution of the following command go-euro-test-1.0-SNAPSHOT.jar will be generated under "target" directory.

mvn clean install



Command Line tool
---------------------------
One can create csv file for specific city by execution following command.

java -jar go-euro-test-1.0-SNAPSHOT.jar "CITY_NAME"
e.g. java -jar go-euro-test-1.0-SNAPSHOT.jar Berlin



Design Choices and Key Notes
-----------------------------

(1) Used lightweight Spring framework.
(2) Used gson library for json parsing.
(3) Created application.properties for configuration of JSON API and csv output directory.
(4) Programing to Interface.
(5) Proper test cases for all services.
(6) Feature to provide destination directory from command line argument.
(7) Used proper logger.
(8) Proper naming of csv file. Format CityName_dd.MM.yyyy'T'HH.mm.ss.SSS.csv.


