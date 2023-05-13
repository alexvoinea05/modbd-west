package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.DistrictWest;
import ro.west.repository.DistrictWestRepository;
import ro.west.service.DistrictWestService;
import ro.west.service.dto.DistrictWestDTO;
import ro.west.service.mapper.DistrictWestMapper;

/**
 * Service Implementation for managing {@link DistrictWest}.
 */
@Service
@Transactional
public class DistrictWestServiceImpl implements DistrictWestService {

    private final Logger log = LoggerFactory.getLogger(DistrictWestServiceImpl.class);

    private final DistrictWestRepository districtWestRepository;

    private final DistrictWestMapper districtWestMapper;

    public DistrictWestServiceImpl(DistrictWestRepository districtWestRepository, DistrictWestMapper districtWestMapper) {
        this.districtWestRepository = districtWestRepository;
        this.districtWestMapper = districtWestMapper;
    }

    @Override
    public DistrictWestDTO save(DistrictWestDTO districtWestDTO) {
        log.debug("Request to save DistrictWest : {}", districtWestDTO);
        DistrictWest districtWest = districtWestMapper.toEntity(districtWestDTO);
        districtWest = districtWestRepository.save(districtWest);
        return districtWestMapper.toDto(districtWest);
    }

    @Override
    public DistrictWestDTO update(DistrictWestDTO districtWestDTO) {
        log.debug("Request to update DistrictWest : {}", districtWestDTO);
        DistrictWest districtWest = districtWestMapper.toEntity(districtWestDTO);
        districtWest = districtWestRepository.save(districtWest);
        return districtWestMapper.toDto(districtWest);
    }

    @Override
    public Optional<DistrictWestDTO> partialUpdate(DistrictWestDTO districtWestDTO) {
        log.debug("Request to partially update DistrictWest : {}", districtWestDTO);

        return districtWestRepository
            .findById(districtWestDTO.getId())
            .map(existingDistrictWest -> {
                districtWestMapper.partialUpdate(existingDistrictWest, districtWestDTO);

                return existingDistrictWest;
            })
            .map(districtWestRepository::save)
            .map(districtWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DistrictWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DistrictWests");
        return districtWestRepository.findAll(pageable).map(districtWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DistrictWestDTO> findOne(Long id) {
        log.debug("Request to get DistrictWest : {}", id);
        return districtWestRepository.findById(id).map(districtWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DistrictWest : {}", id);
        districtWestRepository.deleteById(id);
    }
}
