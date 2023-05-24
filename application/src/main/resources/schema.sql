CREATE TABLE Employee(
	accountId SERIAL PRIMARY KEY,
	surname varchar NOT NULL,
	name varchar NOT NULL,
	middlename varchar,
	job_title varchar,
	login varchar,
	password varchar,
	email varchar,
	status varchar NOT NULL
);

CREATE TABLE TeamMember(
	member_id SERIAL PRIMARY KEY,
	employee_id integer NOT NULL,
	member_role varchar NOT NULL,
	FOREIGN KEY (employee_id) REFERENCES Employee (accountId)
);

CREATE TABLE Project(
	code_name varchar PRIMARY KEY,
	project_name varchar NOT NULL,
	project_description varchar,
	project_status varchar NOT NULL
);

CREATE TABLE Team(
	project_code_name varchar,
	member_id integer,
	FOREIGN KEY (project_code_name) REFERENCES Project (code_name),
	FOREIGN KEY (member_id) REFERENCES TeamMember (member_id),
	PRIMARY KEY (project_code_name)

);



CREATE TABLE Task(
	task_id SERIAL PRIMARY KEY,
	project_code_name varchar NOT NULL,
	task_name varchar NOT NULL,
	task_description varchar,
	task_executor integer NOT NULL,
	task_hours_time integer NOT NULL,
	end_time timestamp NOT NULL,
	task_status varchar NOT NULL,
	task_author integer NOT NULL,
	start_time timestamp NOT NULL,
	edit_time timestamp,
	FOREIGN KEY (task_executor) REFERENCES Employee (accountId),
	FOREIGN KEY (task_author) REFERENCES Employee (accountId),
	FOREIGN KEY (project_code_name) REFERENCES Project (code_name)

);
