package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.TicketWest;
import ro.west.repository.TicketWestRepository;
import ro.west.service.TicketWestService;
import ro.west.service.dto.TicketWestDTO;
import ro.west.service.mapper.TicketWestMapper;

/**
 * Service Implementation for managing {@link TicketWest}.
 */
@Service
@Transactional
public class TicketWestServiceImpl implements TicketWestService {

    private final Logger log = LoggerFactory.getLogger(TicketWestServiceImpl.class);

    private final TicketWestRepository ticketWestRepository;

    private final TicketWestMapper ticketWestMapper;

    public TicketWestServiceImpl(TicketWestRepository ticketWestRepository, TicketWestMapper ticketWestMapper) {
        this.ticketWestRepository = ticketWestRepository;
        this.ticketWestMapper = ticketWestMapper;
    }

    @Override
    public TicketWestDTO save(TicketWestDTO ticketWestDTO) {
        log.debug("Request to save TicketWest : {}", ticketWestDTO);
        TicketWest ticketWest = ticketWestMapper.toEntity(ticketWestDTO);
        ticketWest = ticketWestRepository.save(ticketWest);
        return ticketWestMapper.toDto(ticketWest);
    }

    @Override
    public TicketWestDTO update(TicketWestDTO ticketWestDTO) {
        log.debug("Request to update TicketWest : {}", ticketWestDTO);
        TicketWest ticketWest = ticketWestMapper.toEntity(ticketWestDTO);
        ticketWest = ticketWestRepository.save(ticketWest);
        return ticketWestMapper.toDto(ticketWest);
    }

    @Override
    public Optional<TicketWestDTO> partialUpdate(TicketWestDTO ticketWestDTO) {
        log.debug("Request to partially update TicketWest : {}", ticketWestDTO);

        return ticketWestRepository
            .findById(ticketWestDTO.getId())
            .map(existingTicketWest -> {
                ticketWestMapper.partialUpdate(existingTicketWest, ticketWestDTO);

                return existingTicketWest;
            })
            .map(ticketWestRepository::save)
            .map(ticketWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketWests");
        return ticketWestRepository.findAll(pageable).map(ticketWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketWestDTO> findOne(Long id) {
        log.debug("Request to get TicketWest : {}", id);
        return ticketWestRepository.findById(id).map(ticketWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketWest : {}", id);
        ticketWestRepository.deleteById(id);
    }
}
