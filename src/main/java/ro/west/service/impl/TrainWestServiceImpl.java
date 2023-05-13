package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.TrainWest;
import ro.west.repository.TrainWestRepository;
import ro.west.service.TrainWestService;
import ro.west.service.dto.TrainWestDTO;
import ro.west.service.mapper.TrainWestMapper;

/**
 * Service Implementation for managing {@link TrainWest}.
 */
@Service
@Transactional
public class TrainWestServiceImpl implements TrainWestService {

    private final Logger log = LoggerFactory.getLogger(TrainWestServiceImpl.class);

    private final TrainWestRepository trainWestRepository;

    private final TrainWestMapper trainWestMapper;

    public TrainWestServiceImpl(TrainWestRepository trainWestRepository, TrainWestMapper trainWestMapper) {
        this.trainWestRepository = trainWestRepository;
        this.trainWestMapper = trainWestMapper;
    }

    @Override
    public TrainWestDTO save(TrainWestDTO trainWestDTO) {
        log.debug("Request to save TrainWest : {}", trainWestDTO);
        TrainWest trainWest = trainWestMapper.toEntity(trainWestDTO);
        trainWest = trainWestRepository.save(trainWest);
        return trainWestMapper.toDto(trainWest);
    }

    @Override
    public TrainWestDTO update(TrainWestDTO trainWestDTO) {
        log.debug("Request to update TrainWest : {}", trainWestDTO);
        TrainWest trainWest = trainWestMapper.toEntity(trainWestDTO);
        trainWest = trainWestRepository.save(trainWest);
        return trainWestMapper.toDto(trainWest);
    }

    @Override
    public Optional<TrainWestDTO> partialUpdate(TrainWestDTO trainWestDTO) {
        log.debug("Request to partially update TrainWest : {}", trainWestDTO);

        return trainWestRepository
            .findById(trainWestDTO.getId())
            .map(existingTrainWest -> {
                trainWestMapper.partialUpdate(existingTrainWest, trainWestDTO);

                return existingTrainWest;
            })
            .map(trainWestRepository::save)
            .map(trainWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainWests");
        return trainWestRepository.findAll(pageable).map(trainWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainWestDTO> findOne(Long id) {
        log.debug("Request to get TrainWest : {}", id);
        return trainWestRepository.findById(id).map(trainWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainWest : {}", id);
        trainWestRepository.deleteById(id);
    }
}
