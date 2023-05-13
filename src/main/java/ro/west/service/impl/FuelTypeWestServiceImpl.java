package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.FuelTypeWest;
import ro.west.repository.FuelTypeWestRepository;
import ro.west.service.FuelTypeWestService;
import ro.west.service.dto.FuelTypeWestDTO;
import ro.west.service.mapper.FuelTypeWestMapper;

/**
 * Service Implementation for managing {@link FuelTypeWest}.
 */
@Service
@Transactional
public class FuelTypeWestServiceImpl implements FuelTypeWestService {

    private final Logger log = LoggerFactory.getLogger(FuelTypeWestServiceImpl.class);

    private final FuelTypeWestRepository fuelTypeWestRepository;

    private final FuelTypeWestMapper fuelTypeWestMapper;

    public FuelTypeWestServiceImpl(FuelTypeWestRepository fuelTypeWestRepository, FuelTypeWestMapper fuelTypeWestMapper) {
        this.fuelTypeWestRepository = fuelTypeWestRepository;
        this.fuelTypeWestMapper = fuelTypeWestMapper;
    }

    @Override
    public FuelTypeWestDTO save(FuelTypeWestDTO fuelTypeWestDTO) {
        log.debug("Request to save FuelTypeWest : {}", fuelTypeWestDTO);
        FuelTypeWest fuelTypeWest = fuelTypeWestMapper.toEntity(fuelTypeWestDTO);
        fuelTypeWest = fuelTypeWestRepository.save(fuelTypeWest);
        return fuelTypeWestMapper.toDto(fuelTypeWest);
    }

    @Override
    public FuelTypeWestDTO update(FuelTypeWestDTO fuelTypeWestDTO) {
        log.debug("Request to update FuelTypeWest : {}", fuelTypeWestDTO);
        FuelTypeWest fuelTypeWest = fuelTypeWestMapper.toEntity(fuelTypeWestDTO);
        fuelTypeWest = fuelTypeWestRepository.save(fuelTypeWest);
        return fuelTypeWestMapper.toDto(fuelTypeWest);
    }

    @Override
    public Optional<FuelTypeWestDTO> partialUpdate(FuelTypeWestDTO fuelTypeWestDTO) {
        log.debug("Request to partially update FuelTypeWest : {}", fuelTypeWestDTO);

        return fuelTypeWestRepository
            .findById(fuelTypeWestDTO.getId())
            .map(existingFuelTypeWest -> {
                fuelTypeWestMapper.partialUpdate(existingFuelTypeWest, fuelTypeWestDTO);

                return existingFuelTypeWest;
            })
            .map(fuelTypeWestRepository::save)
            .map(fuelTypeWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelTypeWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FuelTypeWests");
        return fuelTypeWestRepository.findAll(pageable).map(fuelTypeWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelTypeWestDTO> findOne(Long id) {
        log.debug("Request to get FuelTypeWest : {}", id);
        return fuelTypeWestRepository.findById(id).map(fuelTypeWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FuelTypeWest : {}", id);
        fuelTypeWestRepository.deleteById(id);
    }
}
