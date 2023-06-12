CREATE TABLE employee (
  accountid SERIAL PRIMARY KEY,
  surname varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  middlename varchar(255),
  job_title varchar(255),
  login varchar(255),
  password varchar(255),
  email varchar(255),
  status varchar(255) NOT NULL
);

CREATE TABLE teammember (
  member_id SERIAL PRIMARY KEY,
  employee_id integer NOT NULL,
  member_role varchar(255) NOT NULL,
  team varchar(255) NOT NULL,
  CONSTRAINT FK_TeamMember_Employee FOREIGN KEY (employee_id) REFERENCES Employee (accountid),
  CONSTRAINT FK_team FOREIGN KEY (team) REFERENCES Team (project_code_name)
);

CREATE TABLE project (
  code_name VARCHAR(255) PRIMARY KEY,
  project_name VARCHAR(255) NOT NULL,
  project_description VARCHAR(1000),
  project_status VARCHAR(255) NOT NULL
);

CREATE TABLE team (
  teamid SERIAL PRIMARY KEY,
  project_code_name VARCHAR(255),
  member_id INT,
  CONSTRAINT fk_team_project FOREIGN KEY (project_code_name) REFERENCES Project(code_name),
  CONSTRAINT fk_team_member FOREIGN KEY (member_id) REFERENCES TeamMember(member_id)
);

CREATE TABLE task (
  task_id SERIAL PRIMARY KEY,
  project_code_name VARCHAR(255) NOT NULL,
  task_name VARCHAR(255) NOT NULL,
  task_description VARCHAR(255),
  task_executor INTEGER NOT NULL,
  task_hours_time INTEGER NOT NULL,
  end_time TIMESTAMP NOT NULL,
  task_status VARCHAR(255) NOT NULL,
  task_author INTEGER NOT NULL,
  start_time TIMESTAMP NOT NULL,
  edit_time TIMESTAMP,
  parent INTEGER,
  file VARCHAR(255),
  CONSTRAINT fk_task_executor FOREIGN KEY (task_executor) REFERENCES Employee(accountid),
  CONSTRAINT fk_parent FOREIGN KEY (parent) REFERENCES task(task_id),
  CONSTRAINT fk_task_author FOREIGN KEY (task_author) REFERENCES Employee(accountid),
  CONSTRAINT fk_project_code_name FOREIGN KEY (project_code_name) REFERENCES Project(code_name)
);
