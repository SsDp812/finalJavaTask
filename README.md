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

## Отправка уведомлений на почту при создании новой задачи
### Реализация
При создании новой задачи ее исполнитель получает уведомление на почту.
Письма отправляются асинхронно через rabbitMQ.
Для отправки сообщений используется JavaMailSender.
Уже перед отправкой email сотрудника подменятеся на реальный для удобства тестирования.
Email сотрудника указывается в теле самого письма.
### Пример письма с уведомлением на почту сотруднику
![App diagram](https://github.com/SsDp812/finalJavaTask/blob/fixSender/emailExample.png)

### Очередь в брокере сообщений
![App diagram](https://github.com/SsDp812/finalJavaTask/blob/fixSender/queue.png)

## База данных
### Создание
База данных создается автоматически при помощи liquiBase.
Но также в папке resourses есть sql скрипт для альтернативного создания базы.
### Диаграмма базы данных
![App diagram](https://github.com/SsDp812/finalJavaTask/blob/fixSender/dbDiagram.png)

## Аутентификация в приложении
- Логин от админа: ROOT
- Пароль от админа: root

## Тестирование
### Ссылка на отчет по функциональному тестированию
https://docs.google.com/spreadsheets/d/1cdgyPRl3j5yEhDI6uSoP2qaPgu6IgF3pCiMT7qQT1zM/edit?usp=sharing
Также в папке resourses лежит pdf вариант этого документа

### Результаты модульного (юнит) тестирования
![App diagram](https://github.com/SsDp812/finalJavaTask/blob/fixSender/unitTests.png)

### Результаты интеграционного тестирования
![App diagram](https://github.com/SsDp812/finalJavaTask/blob/fixSender/IntegrationTests.png)
Для интеграционного тестирования исполльзовалась зависимость testContainers.
База данных и брокер сообщений помещались также в отдельные тестовые контейнеры.