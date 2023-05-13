package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.JourneyStatusWest;
import ro.west.repository.JourneyStatusWestRepository;
import ro.west.service.JourneyStatusWestService;
import ro.west.service.dto.JourneyStatusWestDTO;
import ro.west.service.mapper.JourneyStatusWestMapper;

/**
 * Service Implementation for managing {@link JourneyStatusWest}.
 */
@Service
@Transactional
public class JourneyStatusWestServiceImpl implements JourneyStatusWestService {

    private final Logger log = LoggerFactory.getLogger(JourneyStatusWestServiceImpl.class);

    private final JourneyStatusWestRepository journeyStatusWestRepository;

    private final JourneyStatusWestMapper journeyStatusWestMapper;

    public JourneyStatusWestServiceImpl(
        JourneyStatusWestRepository journeyStatusWestRepository,
        JourneyStatusWestMapper journeyStatusWestMapper
    ) {
        this.journeyStatusWestRepository = journeyStatusWestRepository;
        this.journeyStatusWestMapper = journeyStatusWestMapper;
    }

    @Override
    public JourneyStatusWestDTO save(JourneyStatusWestDTO journeyStatusWestDTO) {
        log.debug("Request to save JourneyStatusWest : {}", journeyStatusWestDTO);
        JourneyStatusWest journeyStatusWest = journeyStatusWestMapper.toEntity(journeyStatusWestDTO);
        journeyStatusWest = journeyStatusWestRepository.save(journeyStatusWest);
        return journeyStatusWestMapper.toDto(journeyStatusWest);
    }

    @Override
    public JourneyStatusWestDTO update(JourneyStatusWestDTO journeyStatusWestDTO) {
        log.debug("Request to update JourneyStatusWest : {}", journeyStatusWestDTO);
        JourneyStatusWest journeyStatusWest = journeyStatusWestMapper.toEntity(journeyStatusWestDTO);
        journeyStatusWest = journeyStatusWestRepository.save(journeyStatusWest);
        return journeyStatusWestMapper.toDto(journeyStatusWest);
    }

    @Override
    public Optional<JourneyStatusWestDTO> partialUpdate(JourneyStatusWestDTO journeyStatusWestDTO) {
        log.debug("Request to partially update JourneyStatusWest : {}", journeyStatusWestDTO);

        return journeyStatusWestRepository
            .findById(journeyStatusWestDTO.getId())
            .map(existingJourneyStatusWest -> {
                journeyStatusWestMapper.partialUpdate(existingJourneyStatusWest, journeyStatusWestDTO);

                return existingJourneyStatusWest;
            })
            .map(journeyStatusWestRepository::save)
            .map(journeyStatusWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JourneyStatusWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JourneyStatusWests");
        return journeyStatusWestRepository.findAll(pageable).map(journeyStatusWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JourneyStatusWestDTO> findOne(Long id) {
        log.debug("Request to get JourneyStatusWest : {}", id);
        return journeyStatusWestRepository.findById(id).map(journeyStatusWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JourneyStatusWest : {}", id);
        journeyStatusWestRepository.deleteById(id);
    }
}
