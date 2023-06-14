# Итоговое задание

## Описание модулей приложения

### Controllers

Модуль контроллеры нужен для реализации веб части, для получения
запросов и их обработки.

### Dto

Модуль dto (data transfer object) нужен для корректного обмена данными
между контроллерами и бизнес слоем. В нем будут содержаться объекты оболочки
для пересылки объектов.

### Business

В этом модуле будет описана вся бизнес логика приложения, реализованы сервисы
дл каждой из сушностей и остальной необходимый функционал.

### Dao

В модуле dao (data access object) будут реализованы объекты для связи с базой
данных, получения информации.

### Models

В модуле Models будут описаны модели, которые нужны для обмена данными между
бизнем слоем и dao(слоем доступа к данным). Для каждой из сущностей будет написана
своя модель.

### Commons

В данном модуле будут храниться утилитный и вспомогательный инструментарий
и дополнительные классы, перечисления необходимые для остальных модулей. Например,
статусы.

## Диаграмма архитектуры приложения

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/diagram.png)

## База данных

### Создание

База данных создается автоматически при помощи liquiBase.
Но также в папке resourses есть sql скрипт для альтернативного создания базы.

### Диаграмма базы данных

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/images/dbDiagram.png)

## Отправка уведомлений на почту при создании новой задачи

### Реализация

При создании новой задачи ее исполнитель получает уведомление на почту.
Письма отправляются асинхронно через rabbitMQ.
Для отправки сообщений используется JavaMailSender.
Уже перед отправкой email сотрудника подменятеся на реальный для удобства тестирования.
Email сотрудника указывается в теле самого письма.

### Пример письма с уведомлением на почту сотруднику

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/images/emailExample.png)

### Дополнительные уведомления на почту сотруднику

Каждое утро в 11 часов запускается планировщик задач. По всем открытым задачам(в статусе INPROGRESS/NEW),
у которых подходит дедлайн (меньше 24 часов на выполнение) сотрудникам отправляется дополнительное
напоминание на почту, о подходящем дедлайне. Отправка происходит также асинхронно через брокер сообщений.

### Пример письма с напоминанием на почту сотруднику

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/images/rememberMail.png)

### Очередь в брокере сообщений

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/images/queue.png)

## Аутентификация в приложении

- Логин от админа: ROOT
- Пароль от админа: root
- Аутентификация: Basic Auth
  Для регистрации новых сотрудников важно учитывать, что длина их логина и пароля должны составлять
  не менее 5 и 8 символов соответственно. (Имя, фамилия должны содержать хотя бы 3 символа, а email
  должен быть валидным.)

## Линковка задач

У каждой задачи может быть родительская задача, к которой она относится.
Родительская в свою очередь может иметь несколько дочерних.

При создании задачи в качестве родительской должен быть указан либо ее id,
либо null. Статус родительской задачи должен быть NEW/INPROGRESS, прикрепиться к
завершенной или закрытой задаче нельзя.

Также чтобы перевести задачу в статус DONE - все дочерние задачи должны быть в
статусах DONE/CLOSED, если дочерние задачи еще активны, то завершить родительскую не получится.

## Прикрепление файлов к задаче

Через отдельный endpoint можно прикрепить файл к задаче. При получении карточки задачи
в поле файл будет указана cсылка на него(или null если файл не прикреплялся). При повторном
прикреплении нового файла - старый удалсяется, для эффективного использования памяти. Имя файла
генерируется автоматически для избежения коллизий.

### Демонстрация прикрепления файла

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/images/sendFile.png)

### Демонстрация получени задачи с файлом

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/images/getFile.png)

## Минимальные требования к входным данным

- Для регистрации новых сотрудников важно учитывать, что длина их логина и пароля должны составлять
  не менее 5 и 8 символов соответственно. (Имя, фамилия должны содержать хотя бы символа, а email
  должен быть валидным.)

- Кодовое название и имя проекта должны содержать хотя бы 5 символов, а кодовое название должно быть
  еще и уникальным.

- Название задачи должно содержать хотя бы 5 символов, дедлайн должен быть в будущем, а на
  выполнение задачи должно даваться не менее 1го часа. В качестве родительской задачи: id или null.

## Команды для запуска

### Настройка проекта

~~~
maven install
~~~

### Сборка проекта

~~~
maven clean package
~~~

### Запуск докер контейнеров

~~~
docker-compose up -d 
~~~

## Тестирование

### Ссылка на отчет по функциональному тестированию

https://docs.google.com/spreadsheets/d/1cdgyPRl3j5yEhDI6uSoP2qaPgu6IgF3pCiMT7qQT1zM/edit?usp=sharing
Также в папке resourses лежит pdf вариант этого документа

### Результаты модульного (юнит) тестирования

![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/images/unitTests.png)

### Результаты интеграционного тестирования

Для интеграционного тестирования исполльзовалась зависимость testContainers.
База данных и брокер сообщений помещались также в отдельные тестовые контейнеры.
![App diagram](https://github.com/SsDp812/finalJavaTask/blob/main/images/IntegrationTests.png)
