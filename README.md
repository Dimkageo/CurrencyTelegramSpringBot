[LinkedIn](https://www.linkedin.com/in/dmytro-chystiakov-994841287/)
---
**Project Name:** Currency Telegram Bot with Spring, Docker Compose, and PostgreSQL

**Description:**

The project is a Telegram bot developed using the Spring Boot framework and incorporating technologies such as Docker Compose, PostgreSQL, and interaction with a bank's API for fetching currency exchange rates.

**Key Features:**

1. **Currency Exchange Rates:**
   - The bot interacts with the bank's API to retrieve real-time currency exchange rates.
   - Users can query the exchange rate of a specific currency using the bot's commands.

2. **Subscription Management:**
   - Users can subscribe to receive daily updates on currency exchange rates.
   - Subscription information is stored in the PostgreSQL database.

3. **User List:**
   - A REST controller is implemented, allowing users to obtain a list of subscribers receiving updates.

4. **Logging:**
   - Logging is implemented using SLF4J to capture events and identify issues.

**Technologies and Tools:**

- **Spring Boot:** Framework for Java application development.
- **Docker Compose:** Convenient tool for container orchestration.
- **PostgreSQL:** Relational database for storing user and subscription information.
- **Telegram API:** Utilized for user interaction through the Telegram messenger.
- **SLF4J:** Simple logging facade for Java.

**Instructions for Running:**

1. Run Docker Compose to set up the containers.
2. Launch the Spring Boot application.
3. Connect the Telegram bot to your account and obtain the token.
4. Use the bot to fetch currency exchange rates and manage subscriptions.

**Note:**
The project is developed for educational and demonstration purposes and is not intended for commercial use.
---

**Назва проекту:** Курсовий Телеграм-бот для отримання валютного курсу

**Опис:**

Проект представляє собою телеграм-бота, розробленого за допомогою фреймворку Spring Boot та з використанням технологій Docker Compose, PostgreSQL і взаємодії з банківським API.

**Основні функції:**

1. **Отримання курсу валют:**
   - Бот взаємодіє з API банку для отримання актуального курсу валют.
   - Користувачі можуть запитати курс конкретної валюти за допомогою команд бота.

2. **Управління підпискою:**
   - Користувачі можуть підписатися на отримання щоденних оновлень курсу валют.
   - Інформація про підписку зберігається в базі даних PostgreSQL.

3. **Список користувачів:**
   - Реалізовано REST-контроллер, який надає можливість отримання списку користувачів, підписаних на отримання оновлень.

4. **Логування:**
   - Застосовано логування за допомогою SLF4J для реєстрації подій та виявлення проблем.

**Технології та інструменти:**

- **Spring Boot:** Фреймворк для розробки Java-застосунків.
- **Docker Compose:** Зручний інструмент для оркестрації контейнерів.
- **PostgreSQL:** Реляційна база даних для зберігання інформації про користувачів і їхні підписки.
- **Telegram API:** Використовується для взаємодії з користувачами через месенджер Telegram.
- **SLF4J:** Простий інтерфейс логування для Java.

**Інструкції з запуску:**

1. Запустіть Docker Compose для підняття контейнерів.
2. Запустіть Spring Boot застосунок.
3. Підключіть телеграм-бота до вашого аккаунту та отримайте токен.
4. Використовуйте бота для отримання курсу валют та керування підпискою.

**Примітка:**
Проект розроблений як освітній і демонстраційний, і не призначений для комерційного використання.

---