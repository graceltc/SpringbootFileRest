CREATE TABLE file 
(
  file_id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  file_name varchar(100) NOT NULL,
  file_size bigint,
  PRIMARY KEY (file_id)
);



