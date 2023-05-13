package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.TrainTypeWest;
import ro.west.repository.TrainTypeWestRepository;
import ro.west.service.TrainTypeWestService;
import ro.west.service.dto.TrainTypeWestDTO;
import ro.west.service.mapper.TrainTypeWestMapper;

/**
 * Service Implementation for managing {@link TrainTypeWest}.
 */
@Service
@Transactional
public class TrainTypeWestServiceImpl implements TrainTypeWestService {

    private final Logger log = LoggerFactory.getLogger(TrainTypeWestServiceImpl.class);

    private final TrainTypeWestRepository trainTypeWestRepository;

    private final TrainTypeWestMapper trainTypeWestMapper;

    public TrainTypeWestServiceImpl(TrainTypeWestRepository trainTypeWestRepository, TrainTypeWestMapper trainTypeWestMapper) {
        this.trainTypeWestRepository = trainTypeWestRepository;
        this.trainTypeWestMapper = trainTypeWestMapper;
    }

    @Override
    public TrainTypeWestDTO save(TrainTypeWestDTO trainTypeWestDTO) {
        log.debug("Request to save TrainTypeWest : {}", trainTypeWestDTO);
        TrainTypeWest trainTypeWest = trainTypeWestMapper.toEntity(trainTypeWestDTO);
        trainTypeWest = trainTypeWestRepository.save(trainTypeWest);
        return trainTypeWestMapper.toDto(trainTypeWest);
    }

    @Override
    public TrainTypeWestDTO update(TrainTypeWestDTO trainTypeWestDTO) {
        log.debug("Request to update TrainTypeWest : {}", trainTypeWestDTO);
        TrainTypeWest trainTypeWest = trainTypeWestMapper.toEntity(trainTypeWestDTO);
        trainTypeWest = trainTypeWestRepository.save(trainTypeWest);
        return trainTypeWestMapper.toDto(trainTypeWest);
    }

    @Override
    public Optional<TrainTypeWestDTO> partialUpdate(TrainTypeWestDTO trainTypeWestDTO) {
        log.debug("Request to partially update TrainTypeWest : {}", trainTypeWestDTO);

        return trainTypeWestRepository
            .findById(trainTypeWestDTO.getId())
            .map(existingTrainTypeWest -> {
                trainTypeWestMapper.partialUpdate(existingTrainTypeWest, trainTypeWestDTO);

                return existingTrainTypeWest;
            })
            .map(trainTypeWestRepository::save)
            .map(trainTypeWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainTypeWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainTypeWests");
        return trainTypeWestRepository.findAll(pageable).map(trainTypeWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainTypeWestDTO> findOne(Long id) {
        log.debug("Request to get TrainTypeWest : {}", id);
        return trainTypeWestRepository.findById(id).map(trainTypeWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainTypeWest : {}", id);
        trainTypeWestRepository.deleteById(id);
    }
}
