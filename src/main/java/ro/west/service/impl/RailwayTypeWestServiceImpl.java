package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.RailwayTypeWest;
import ro.west.repository.RailwayTypeWestRepository;
import ro.west.service.RailwayTypeWestService;
import ro.west.service.dto.RailwayTypeWestDTO;
import ro.west.service.mapper.RailwayTypeWestMapper;

/**
 * Service Implementation for managing {@link RailwayTypeWest}.
 */
@Service
@Transactional
public class RailwayTypeWestServiceImpl implements RailwayTypeWestService {

    private final Logger log = LoggerFactory.getLogger(RailwayTypeWestServiceImpl.class);

    private final RailwayTypeWestRepository railwayTypeWestRepository;

    private final RailwayTypeWestMapper railwayTypeWestMapper;

    public RailwayTypeWestServiceImpl(RailwayTypeWestRepository railwayTypeWestRepository, RailwayTypeWestMapper railwayTypeWestMapper) {
        this.railwayTypeWestRepository = railwayTypeWestRepository;
        this.railwayTypeWestMapper = railwayTypeWestMapper;
    }

    @Override
    public RailwayTypeWestDTO save(RailwayTypeWestDTO railwayTypeWestDTO) {
        log.debug("Request to save RailwayTypeWest : {}", railwayTypeWestDTO);
        RailwayTypeWest railwayTypeWest = railwayTypeWestMapper.toEntity(railwayTypeWestDTO);
        railwayTypeWest = railwayTypeWestRepository.save(railwayTypeWest);
        return railwayTypeWestMapper.toDto(railwayTypeWest);
    }

    @Override
    public RailwayTypeWestDTO update(RailwayTypeWestDTO railwayTypeWestDTO) {
        log.debug("Request to update RailwayTypeWest : {}", railwayTypeWestDTO);
        RailwayTypeWest railwayTypeWest = railwayTypeWestMapper.toEntity(railwayTypeWestDTO);
        railwayTypeWest = railwayTypeWestRepository.save(railwayTypeWest);
        return railwayTypeWestMapper.toDto(railwayTypeWest);
    }

    @Override
    public Optional<RailwayTypeWestDTO> partialUpdate(RailwayTypeWestDTO railwayTypeWestDTO) {
        log.debug("Request to partially update RailwayTypeWest : {}", railwayTypeWestDTO);

        return railwayTypeWestRepository
            .findById(railwayTypeWestDTO.getId())
            .map(existingRailwayTypeWest -> {
                railwayTypeWestMapper.partialUpdate(existingRailwayTypeWest, railwayTypeWestDTO);

                return existingRailwayTypeWest;
            })
            .map(railwayTypeWestRepository::save)
            .map(railwayTypeWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RailwayTypeWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RailwayTypeWests");
        return railwayTypeWestRepository.findAll(pageable).map(railwayTypeWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RailwayTypeWestDTO> findOne(Long id) {
        log.debug("Request to get RailwayTypeWest : {}", id);
        return railwayTypeWestRepository.findById(id).map(railwayTypeWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RailwayTypeWest : {}", id);
        railwayTypeWestRepository.deleteById(id);
    }
}
