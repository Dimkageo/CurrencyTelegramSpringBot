package org.example.currencyspringbot.servise;

import lombok.extern.slf4j.Slf4j;
import org.example.currencyspringbot.Privat.CurrencyRertrievalPrivatService;
import org.example.currencyspringbot.Privat.CurrencyRertrievalService;
import org.example.currencyspringbot.Privat.PrettyRateResponseService;
import org.example.currencyspringbot.config.BotConfig;
import org.example.currencyspringbot.model.User;
import org.example.currencyspringbot.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.vdurmont.emoji.EmojiParser;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private CurrencyRertrievalService currencyRertrievalService = new CurrencyRertrievalPrivatService();

    @Autowired
    private UserRepository userRepository;

    final BotConfig config;
    static final String HELP_TEXT = " Привіт, користувач  !\n" +
            "команда /stsrt -запускає бота, \n" +
            "команда /privat -виводить курс вказаний PrivatBank,\n" +
            "команда /subscribe -підключає розсилку повідомлень курсу валюти " +
            "кожного дня о 8.00\n" +
            "команда /unsubscribe -відміняє розсилку повідомлень\n" +
            " (@Currency123TestBot)";

    static final String ERROR_TEXT = "Error occurred: ";

    public TelegramBot(BotConfig config) {
        this.config = config;
        //створюємо меню
        List<BotCommand> listofComands = new ArrayList<>();
        listofComands.add(new BotCommand("/start", "Запуск"));
        listofComands.add(new BotCommand("/privat", "Дізнатись курс Privat"));
        listofComands.add(new BotCommand("/subscribe", "Підписатися на отримання о 8.00 та 20.00"));
        listofComands.add(new BotCommand("/unsubscribe", "відписатися від розсилання"));
        listofComands.add(new BotCommand("/help", "Допомога"));
//        listofComands.add(new BotCommand("/review", "Залишити відгук"));
        try {
            this.execute(new SetMyCommands(listofComands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        // Проверяем есть ли в обновлении сообщение и в сообщении есть текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getFirstName();

            //примусова відправка всім від адміна
            if (messageText.contains("/send") && config.getOwnerId() == chatId) {
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                var users = userRepository.findAll();
                for (User user : users) {
                    sendMessage(user.getChatId(), textToSend);
                }
            }

            switch (messageText) {
                case "/start" -> {
                    registerUser(update.getMessage());
                    startCommandReceived(chatId, userName);
                }
                case "/privat" -> {
                    getPrivatKard(chatId);
                }
                case "/subscribe" -> {
                    subscribeStatus(update.getMessage().getChatId(), 1);
                    sendMessage(chatId, "Дякуемо за підписку");
                }
                case "/unsubscribe" -> {
                    subscribeStatus(update.getMessage().getChatId(), 0);
                    sendMessage(chatId, "Дякуємо що користувались.");
                }
                case "/help" -> {
                    sendMessage(chatId, HELP_TEXT);
                }
                default -> {
                    sendMessage(chatId, "Скористайтесь командами в меню");
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            log.error(callbackData, messageId, chatId);
        }
    }

    private void subscribeStatus(Long chatId, int i) {

        User user = userRepository.findById(chatId).orElse(null);
        user.setSubscribe(i);
        userRepository.save(user);

    }

    private void getPrivatKard(long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Для карток: \n" +
                PrettyRateResponseService.formRateRecponse(
                        currencyRertrievalService.getCurencyRates(0)
                ) + "У відділеннях: \n" +
                PrettyRateResponseService.formRateRecponse(
                        currencyRertrievalService.getCurencyRates(1)
                )
        );
        message.setChatId(chatId);
        executeMessage(message);
    }

    private void startCommandReceived(long chatId, String userName) {

        SendMessage message = createMessage("Hi, " + userName + ". \n");
        message.setChatId(chatId);

        executeMessage(message);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        executeMessage(message);
    }

    private void executeMessage(SendMessage message) {
        try {//відправляємо
            createMessage(String.valueOf(message));
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void registerUser(Message message) {

        if (userRepository.findById(message.getChatId()).isEmpty()) {

            var chatId = message.getChatId();
            var chat = message.getChat();
            sendMessage(config.getOwnerId(), "Новий юзер \n" + message.getFrom().getFirstName() +
                    ", " + message.getFrom().getUserName());

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setUserName(chat.getUserName());
            user.setRegistereAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public SendMessage createMessage(String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        return message;
    }

    @Scheduled(cron = "${cron.scheduler}")//час запуску
    private void sendAds() {

        var users = userRepository.findAll();

        for (User user : users) {
            if (user.getSubscribe() == 1) {
                getPrivatKard(user.getChatId());
            }
        }

    }
}
