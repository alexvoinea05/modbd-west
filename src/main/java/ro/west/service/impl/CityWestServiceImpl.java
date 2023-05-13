package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.CityWest;
import ro.west.repository.CityWestRepository;
import ro.west.service.CityWestService;
import ro.west.service.dto.CityWestDTO;
import ro.west.service.mapper.CityWestMapper;

/**
 * Service Implementation for managing {@link CityWest}.
 */
@Service
@Transactional
public class CityWestServiceImpl implements CityWestService {

    private final Logger log = LoggerFactory.getLogger(CityWestServiceImpl.class);

    private final CityWestRepository cityWestRepository;

    private final CityWestMapper cityWestMapper;

    public CityWestServiceImpl(CityWestRepository cityWestRepository, CityWestMapper cityWestMapper) {
        this.cityWestRepository = cityWestRepository;
        this.cityWestMapper = cityWestMapper;
    }

    @Override
    public CityWestDTO save(CityWestDTO cityWestDTO) {
        log.debug("Request to save CityWest : {}", cityWestDTO);
        CityWest cityWest = cityWestMapper.toEntity(cityWestDTO);
        cityWest = cityWestRepository.save(cityWest);
        return cityWestMapper.toDto(cityWest);
    }

    @Override
    public CityWestDTO update(CityWestDTO cityWestDTO) {
        log.debug("Request to update CityWest : {}", cityWestDTO);
        CityWest cityWest = cityWestMapper.toEntity(cityWestDTO);
        cityWest = cityWestRepository.save(cityWest);
        return cityWestMapper.toDto(cityWest);
    }

    @Override
    public Optional<CityWestDTO> partialUpdate(CityWestDTO cityWestDTO) {
        log.debug("Request to partially update CityWest : {}", cityWestDTO);

        return cityWestRepository
            .findById(cityWestDTO.getId())
            .map(existingCityWest -> {
                cityWestMapper.partialUpdate(existingCityWest, cityWestDTO);

                return existingCityWest;
            })
            .map(cityWestRepository::save)
            .map(cityWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CityWests");
        return cityWestRepository.findAll(pageable).map(cityWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityWestDTO> findOne(Long id) {
        log.debug("Request to get CityWest : {}", id);
        return cityWestRepository.findById(id).map(cityWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CityWest : {}", id);
        cityWestRepository.deleteById(id);
    }
}
