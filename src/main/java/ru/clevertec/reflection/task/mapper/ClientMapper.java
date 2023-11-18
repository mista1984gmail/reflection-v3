package ru.clevertec.reflection.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.reflection.task.entity.dto.ClientDto;
import ru.clevertec.reflection.task.entity.model.Client;

@Mapper
public interface ClientMapper {

    /**
     * Маппит DTO в Клиента без id
     *
     * @param clientDto - DTO для маппинга
     * @return новый Клиент
     */
    @Mapping(ignore = true, target = "id")
    Client clientDtoToClient(ClientDto clientDto);

    /**
     * Маппит Клиента в DTO без id
     *
     * @param client - Клиент для маппинга
     * @return новый DTO
     */
    ClientDto clientToClientDto(Client client);

}