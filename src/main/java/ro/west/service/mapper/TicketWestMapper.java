package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.TicketWest;
import ro.west.service.dto.TicketWestDTO;

/**
 * Mapper for the entity {@link TicketWest} and its DTO {@link TicketWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketWestMapper extends EntityMapper<TicketWestDTO, TicketWest> {}
