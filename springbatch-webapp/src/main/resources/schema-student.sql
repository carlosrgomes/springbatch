DROP TABLE IF EXISTS EXAM_RESULT;
create table EXAM_RESULT (
   id bigint auto_increment,
   student_name VARCHAR(30) NOT NULL,
   dob DATE NOT NULL,
   percentage  double NOT NULL
);