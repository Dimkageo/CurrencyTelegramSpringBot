CREATE TABLE IF NOT EXISTS tg(
                                     chatId BIGINT PRIMARY KEY,
                                     firstName VARCHAR(255),
                                     lastName VARCHAR(255),
                                     userName VARCHAR(255),
                                     subscribe VARCHAR(255),
                                     registereAt TIMESTAMP
)