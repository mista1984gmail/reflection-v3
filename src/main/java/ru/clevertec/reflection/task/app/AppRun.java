package ru.clevertec.reflection.task.app;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.json.parser.JSONParser;
import ru.clevertec.json.parser.impl.JSONParserImpl;

import ru.clevertec.reflection.task.aspect.annotation.CreateTable;
import ru.clevertec.reflection.task.aspect.annotation.LoadCache;
import ru.clevertec.reflection.task.aspect.annotation.SaveCache;
import ru.clevertec.reflection.task.controller.ClientController;
import ru.clevertec.reflection.task.exception.ClientValidateException;
import ru.clevertec.reflection.task.mapper.ClientMapper;
import ru.clevertec.reflection.task.mapper.ClientMapperImpl;
import ru.clevertec.reflection.task.repository.ClientRepository;
import ru.clevertec.reflection.task.repository.impl.ClientRepositoryImpl;
import ru.clevertec.reflection.task.service.ClientService;
import ru.clevertec.reflection.task.service.impl.ClientServiceImpl;
import ru.clevertec.reflection.task.validator.ClientValidator;
import ru.clevertec.reflection.task.validator.impl.ClientValidatorImpl;

import java.util.Scanner;

public class AppRun {

    private static final Logger logger = LoggerFactory.getLogger(AppRun.class);
    private static final XmlMapper XML_MAPPER = new XmlMapper();
    private static final JSONParser JSON_PARSER = new JSONParserImpl();
    public static final ClientRepository CLIENT_REPOSITORY = new ClientRepositoryImpl();
    public static final ClientValidator CLIENT_VALIDATOR = new ClientValidatorImpl();
    private static final ClientMapper CLIENT_MAPPER = new ClientMapperImpl();
    public static final ClientService CLIENT_SERVICE = new ClientServiceImpl(CLIENT_REPOSITORY, CLIENT_MAPPER);
    public static final ClientController CLIENT_CONTROLLER = new ClientController(CLIENT_SERVICE, CLIENT_VALIDATOR, XML_MAPPER, JSON_PARSER);

    {
        XML_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        XML_MAPPER.findAndRegisterModules();
        XML_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @CreateTable
    @LoadCache
    @SaveCache
    public void runApplication() throws Exception {

        int userInput = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            logger.info("_______________________________________________________________________");
            logger.info("МЕНЮ операций над КЛИЕНТАМИ:");
            logger.info("---------------------------");
            logger.info("Введите 0 для выхода из приложения");
            logger.info("Введите 1 для отображения всех клиентов");
            logger.info("Введите 2 для отображения клиента по id");
            logger.info("Введите 3 для добавления клиента через json формат");
            logger.info("Введите 4 для добавления клиента через xml формат");
            logger.info("Введите 5 для обновления клиента");
            logger.info("Введите 6 для удаления клиента");
            logger.info("_______________________________________________________________________");
            userInput = scanner.nextInt();
            switch (userInput) {
                case 0:
                    logger.info("До свидания! Хорошего дня!");
                    break;
                case 1:
                    CLIENT_CONTROLLER.getAllClients();
                    break;
                case 2:
                    CLIENT_CONTROLLER.getClient();
                    break;
                case 3:
                    try {
                        CLIENT_CONTROLLER.addClientFromJson();
                    } catch (ClientValidateException clientValidateException) {
                        logger.error(clientValidateException.getMessage());
                    }
                    break;
                case 4:
                    try {
                        CLIENT_CONTROLLER.addClientFromXml();
                    } catch (ClientValidateException clientValidateException) {
                        logger.error(clientValidateException.getMessage());
                    }
                    break;
                case 5:
                    CLIENT_CONTROLLER.updateClient();
                    break;
                case 6:
                    CLIENT_CONTROLLER.deleteClient();
                    break;
                default:
                    logger.info("Нет такой операции, пожалуйста, попробуйте другую операцию!");
            }
        }
        while (userInput != 0);
    }

}
