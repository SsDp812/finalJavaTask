# Описание базы данных

## Employee

# accoundId - айди аккаунта сотрудника (integer, primary key),
# suraname - фамилия varchar
# name - имя varchar
# middlename - отчество varchar
# job_title - должность varchar
# login - логин varchar
# password - пароль varchar
# email - почта varchar
# status - статус сотрудника varchar


## TeamMember

# member_id - айди записи primary key integer
# employee_id - айди сотрудника (ссылается на Employee) integer
# member_role - роль varchar


## Team

# project_code_name - кодовое название проекта(ссылается на таблицу проекты) varchar
# member_id - айди участника команды (ссылается на TeamMember) integer
# primary key из обоих полей (составной)

## Task

# task_id - айди задачи integer primary key
# project_code_name - кодовое название проекта varchar (ссылается на Project)
# task_name - название задачи varchar
# task_description - описание varchar
# task_executor - исполнитель integer (ссылается на Employee)
# task_hours_time - количество часов integer
# end_time - дедлайн timestamp
# task_status - статус varchar
# task_author - автор задачи integer (ссылается на Employee)
# start_time - время создания timestamp
# edit_time  - время последнего изменения timestamp