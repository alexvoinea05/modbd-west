package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.JourneyWest;
import ro.west.repository.JourneyWestRepository;
import ro.west.service.JourneyWestService;
import ro.west.service.dto.JourneyWestDTO;
import ro.west.service.mapper.JourneyWestMapper;

/**
 * Service Implementation for managing {@link JourneyWest}.
 */
@Service
@Transactional
public class JourneyWestServiceImpl implements JourneyWestService {

    private final Logger log = LoggerFactory.getLogger(JourneyWestServiceImpl.class);

    private final JourneyWestRepository journeyWestRepository;

    private final JourneyWestMapper journeyWestMapper;

    public JourneyWestServiceImpl(JourneyWestRepository journeyWestRepository, JourneyWestMapper journeyWestMapper) {
        this.journeyWestRepository = journeyWestRepository;
        this.journeyWestMapper = journeyWestMapper;
    }

    @Override
    public JourneyWestDTO save(JourneyWestDTO journeyWestDTO) {
        log.debug("Request to save JourneyWest : {}", journeyWestDTO);
        JourneyWest journeyWest = journeyWestMapper.toEntity(journeyWestDTO);
        journeyWest = journeyWestRepository.save(journeyWest);
        return journeyWestMapper.toDto(journeyWest);
    }

    @Override
    public JourneyWestDTO update(JourneyWestDTO journeyWestDTO) {
        log.debug("Request to update JourneyWest : {}", journeyWestDTO);
        JourneyWest journeyWest = journeyWestMapper.toEntity(journeyWestDTO);
        journeyWest = journeyWestRepository.save(journeyWest);
        return journeyWestMapper.toDto(journeyWest);
    }

    @Override
    public Optional<JourneyWestDTO> partialUpdate(JourneyWestDTO journeyWestDTO) {
        log.debug("Request to partially update JourneyWest : {}", journeyWestDTO);

        return journeyWestRepository
            .findById(journeyWestDTO.getId())
            .map(existingJourneyWest -> {
                journeyWestMapper.partialUpdate(existingJourneyWest, journeyWestDTO);

                return existingJourneyWest;
            })
            .map(journeyWestRepository::save)
            .map(journeyWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JourneyWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JourneyWests");
        return journeyWestRepository.findAll(pageable).map(journeyWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JourneyWestDTO> findOne(Long id) {
        log.debug("Request to get JourneyWest : {}", id);
        return journeyWestRepository.findById(id).map(journeyWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JourneyWest : {}", id);
        journeyWestRepository.deleteById(id);
    }
}
