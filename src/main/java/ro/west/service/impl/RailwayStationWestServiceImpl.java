package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.RailwayStationWest;
import ro.west.repository.RailwayStationWestRepository;
import ro.west.service.RailwayStationWestService;
import ro.west.service.dto.RailwayStationWestDTO;
import ro.west.service.mapper.RailwayStationWestMapper;

/**
 * Service Implementation for managing {@link RailwayStationWest}.
 */
@Service
@Transactional
public class RailwayStationWestServiceImpl implements RailwayStationWestService {

    private final Logger log = LoggerFactory.getLogger(RailwayStationWestServiceImpl.class);

    private final RailwayStationWestRepository railwayStationWestRepository;

    private final RailwayStationWestMapper railwayStationWestMapper;

    public RailwayStationWestServiceImpl(
        RailwayStationWestRepository railwayStationWestRepository,
        RailwayStationWestMapper railwayStationWestMapper
    ) {
        this.railwayStationWestRepository = railwayStationWestRepository;
        this.railwayStationWestMapper = railwayStationWestMapper;
    }

    @Override
    public RailwayStationWestDTO save(RailwayStationWestDTO railwayStationWestDTO) {
        log.debug("Request to save RailwayStationWest : {}", railwayStationWestDTO);
        RailwayStationWest railwayStationWest = railwayStationWestMapper.toEntity(railwayStationWestDTO);
        railwayStationWest = railwayStationWestRepository.save(railwayStationWest);
        return railwayStationWestMapper.toDto(railwayStationWest);
    }

    @Override
    public RailwayStationWestDTO update(RailwayStationWestDTO railwayStationWestDTO) {
        log.debug("Request to update RailwayStationWest : {}", railwayStationWestDTO);
        RailwayStationWest railwayStationWest = railwayStationWestMapper.toEntity(railwayStationWestDTO);
        railwayStationWest = railwayStationWestRepository.save(railwayStationWest);
        return railwayStationWestMapper.toDto(railwayStationWest);
    }

    @Override
    public Optional<RailwayStationWestDTO> partialUpdate(RailwayStationWestDTO railwayStationWestDTO) {
        log.debug("Request to partially update RailwayStationWest : {}", railwayStationWestDTO);

        return railwayStationWestRepository
            .findById(railwayStationWestDTO.getId())
            .map(existingRailwayStationWest -> {
                railwayStationWestMapper.partialUpdate(existingRailwayStationWest, railwayStationWestDTO);

                return existingRailwayStationWest;
            })
            .map(railwayStationWestRepository::save)
            .map(railwayStationWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RailwayStationWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RailwayStationWests");
        return railwayStationWestRepository.findAll(pageable).map(railwayStationWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RailwayStationWestDTO> findOne(Long id) {
        log.debug("Request to get RailwayStationWest : {}", id);
        return railwayStationWestRepository.findById(id).map(railwayStationWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RailwayStationWest : {}", id);
        railwayStationWestRepository.deleteById(id);
    }
}
