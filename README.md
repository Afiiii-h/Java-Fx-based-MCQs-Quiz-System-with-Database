# Java-Fx-based-MCQs-Quiz-System-with-Database
# Features:

User-friendly quiz interface

Multiple-choice question format

Score calculation and result display

Support for external question banks

Java-based backend with a structured architecture

# Technologies Used:

Java (JDK 8 or higher)

Java Swing for GUI (if applicable)

External libraries (located in lib/ and jars/ folders)

Installation & Setup

Ensure you have Java (JDK 8+) installed on your system.

Clone or download the repository.

Import the project into an IDE like Eclipse or IntelliJ IDEA.

Add the necessary libraries from the lib/ and jars/ folders to the classpath.

Compile and run the project.

# DatabaseCode:
Create Table Users (
   userID int Identity(1,1) Primary Key,  
   username VARCHAR(255) Not Null,         -- User's username
   passwordHash VARCHAR(255) Not Null,     
   email VARCHAR(255) Not Null         
   );

   Insert Into Users (username, passwordHash, email)
VALUES
('Afifa_ habib', 'hashedpassword123', 'afifa.habib@example.com'),
('Anisha_sarhadi', 'hashedpassword456', 'anisha.sarhadi@example.com'),
('waqas_khan', 'hashedpassword789', 'waqas.khan@example.com'),
('kashaf_farhan', 'hashedpassword799', 'kashaf.farhan@example.com'),
('mohammad_ahmed' , 'hashedpassword457', 'mohammad.ahmed@example.com');

ALTER TABLE Users ADD lastScore INT DEFAULT 0;

Create Table Quiz (
   quizID int Identity(1,1) Primary Key,   
   quizTitle VARCHAR(255) Not Null,
);

Insert Into Quiz (quizTitle)
VALUES
('Math Quiz'),
('java Quiz'),   
('Dbms Quiz');

Create Table Questions (
   questionID int Identity(1,1) Primary Key,
   quizID int NOT NULL,
   questionText Text NOT NULL,
   Foreign Key(quizID) REFERENCES Quiz(quizID) On Delete Cascade
);

Insert Into Questions (quizID, questionText)
VALUES
(1, 'What is 2 + 2?'),
(1, 'What is 10 * 5?'),  
(1, 'subtract 18 from 63'),
(1, 'how many days in 3 weeks?'),
(1, 'what is 1/2 + 1/4?'),

(2, 'Which of the following is used to store multiple values in a single variable in Java?'),
(2, 'Which data structure uses a Last-In-First-Out (LIFO) principle?'),
(2, 'What method is used to add an element to an ArrayList in Java?'),
(2, 'Which of the following is the correct way to declare an array in Java?'),
(2, 'Which of the following data structures allows for dynamic resizing in Java?'),

(3, 'What does SQL stand for?'),
(3, 'In a relational database, a table is also known as'),
(3, 'Which of the following commands is used to remove a table from a database?'),
(3, 'In SQL, which of the following statements is used to retrieve data from a database?'),
(3, 'Which SQL command is used to modify an existing record in a table?');

CREATE TABLE AnswerOption (
   answerID int Identity(1,1) Primary Key,
   questionID int Not Null,
   answerText VARCHAR(255) Not Null,
   isCorrect Bit Not Null,
   Foreign Key (questionID) REFERENCES Questions(questionID) On Delete Cascade
);

Insert Into AnswerOption (questionID, answerText, isCorrect)
VALUES
(1, '4', 1),
(1, '5', 0),
(1, '7', 0),
(1, '8', 0),

(2, '50', 1),
(2, '60', 0),
(2, '70', 0),
(2, '80', 0),

(3, '45', 1),
(3, '44', 0),
(3, '43', 0),
(3, '42', 0),

(4, '21', 1),
(4, '18', 0),
(4, '16', 0),
(4, '14', 0),

(5, '0.75', 1),
(5, '1.5', 0),
(5, '2.4', 0),
(5, '3.8', 0),

(6, 'arrays', 1),
(6, 'stack', 0),
(6, 'queue', 0),
(6, 'string', 0),

(7, 'stack', 1),
(7, 'arrays', 0),
(7, 'queue', 0),
(7, 'string', 0),

(8, 'add()', 1),
(8, 'addElement()', 0),
(8, 'addValue()', 0),
(8, 'addContent()', 0),

(9, 'int array[]=new int[size]', 1),
(9, 'int array[]=new', 0),
(9, 'int array[]=new int[]', 0),
(9, 'int array[]=int[]', 0),

(10, 'arraylist', 0),
(10, 'queue', 1),
(10, 'stack', 0),
(10, 'linkedlist', 0),

(11, 'Structured query language', 1),
(11, 'standard query language', 0),
(11, 'standard question language', 0),
(11, 'structured query line', 0),

(12, 'relation', 0),
(12, 'record', 1),
(12, 'field', 0),
(12, 'cell', 0),

(13, 'drop', 0),
(13, 'delete', 1),
(13, 'truncate', 0),
(13, 'remove', 0),

(14, 'delete', 0),
(14, 'select', 1),
(14, 'insert', 0),
(14, 'update', 0),

(15, 'delete', 0),
(15, 'select', 0),
(15, 'insert', 0),
(15, 'update', 1);


Create Index idx_questionID ON AnswerOption(questionID);
Select * from Users
Select * from Quiz
Select * from Questions
Select * from AnswerOption
