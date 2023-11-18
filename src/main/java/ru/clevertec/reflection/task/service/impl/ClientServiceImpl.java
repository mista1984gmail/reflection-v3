package ru.clevertec.reflection.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.reflection.task.entity.dto.ClientDto;
import ru.clevertec.reflection.task.entity.model.Client;
import ru.clevertec.reflection.task.exception.ClientNotFoundException;
import ru.clevertec.reflection.task.mapper.ClientMapper;
import ru.clevertec.reflection.task.repository.ClientRepository;
import ru.clevertec.reflection.task.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	private ClientRepository clientRepository;
	private ClientMapper clientMapper;

	public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
	}

	/**
	 * Создаёт нового Клиента из DTO
	 *
	 * @param client DTO с информацией о создании
	 */
	@Override
	public Long create(ClientDto client) throws Exception {
		logger.info("Сохранение КЛИЕНТА: {}", client);
		Client savedClient = clientRepository.save(clientMapper.clientDtoToClient(client));
		String success = savedClient.getId() != 0 ? "" : "не";
		logger.info("КЛИЕНТ {} сохранен: {}", success, client);
		return savedClient.getId();
	}

	/**
	 * Возвращает всех существующих Клиентов
	 *
	 * @return лист с информацией о Клиентах
	 */
	@Override
	public List<ClientDto> getAll() throws Exception {
		return clientRepository.getAll()
							   .stream()
							   .map(clientMapper::clientToClientDto)
							   .collect(Collectors.toList());
	}

	/**
	 * Ищет Клиента по идентификатору
	 *
	 * @param id идентификатор Клиента
	 * @return найденный Клиент
	 * @throws ClientNotFoundException если Клиент не найден
	 */
	@Override
	public ClientDto getById(Long id) throws Exception {
		Client client = getClientById(id);
		return clientMapper.clientToClientDto(client);
	}

	/**
	 * Удаляет существующиего Клиента
	 *
	 * @param id идентификатор Клиента для удаления
	 * @throws ClientNotFoundException если Клиент не найден
	 */
	@Override
	public void delete(Long id) throws Exception {
		Client client = getClientById(id);
		clientRepository.delete(id);
		logger.info("Client with id= '{}' delete", id);
	}

	/**
	 * Обновляет уже существующиего Клиента из информации полученной в DTO
	 *
	 * @param id     идентификатор Клиента для обновления
	 * @param client DTO с информацией об обновлении
	 * @throws ClientNotFoundException если Клиент не найден
	 */
	@Override
	public void update(Long id, ClientDto client) throws Exception {
		Client clientFromDB = getClientById(id);
		logger.info("Обновление КЛИЕНТА с id= '{}'", id);
		clientRepository.update(id, client);
	}

	/**
	 * Ищет Клиента по идентификатору
	 *
	 * @param id идентификатор Клиента
	 * @return найденный Клиент
	 * @throws ClientNotFoundException если Клиент не найден
	 */
	private Client getClientById(Long id) throws Exception {
		Client client = clientRepository.getById(id);
		if (client.getId() == null) {
			throw new ClientNotFoundException(id);
		} else {
			return client;
		}
	}

}
