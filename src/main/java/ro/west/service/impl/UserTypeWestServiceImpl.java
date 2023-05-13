package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.UserTypeWest;
import ro.west.repository.UserTypeWestRepository;
import ro.west.service.UserTypeWestService;
import ro.west.service.dto.UserTypeWestDTO;
import ro.west.service.mapper.UserTypeWestMapper;

/**
 * Service Implementation for managing {@link UserTypeWest}.
 */
@Service
@Transactional
public class UserTypeWestServiceImpl implements UserTypeWestService {

    private final Logger log = LoggerFactory.getLogger(UserTypeWestServiceImpl.class);

    private final UserTypeWestRepository userTypeWestRepository;

    private final UserTypeWestMapper userTypeWestMapper;

    public UserTypeWestServiceImpl(UserTypeWestRepository userTypeWestRepository, UserTypeWestMapper userTypeWestMapper) {
        this.userTypeWestRepository = userTypeWestRepository;
        this.userTypeWestMapper = userTypeWestMapper;
    }

    @Override
    public UserTypeWestDTO save(UserTypeWestDTO userTypeWestDTO) {
        log.debug("Request to save UserTypeWest : {}", userTypeWestDTO);
        UserTypeWest userTypeWest = userTypeWestMapper.toEntity(userTypeWestDTO);
        userTypeWest = userTypeWestRepository.save(userTypeWest);
        return userTypeWestMapper.toDto(userTypeWest);
    }

    @Override
    public UserTypeWestDTO update(UserTypeWestDTO userTypeWestDTO) {
        log.debug("Request to update UserTypeWest : {}", userTypeWestDTO);
        UserTypeWest userTypeWest = userTypeWestMapper.toEntity(userTypeWestDTO);
        userTypeWest = userTypeWestRepository.save(userTypeWest);
        return userTypeWestMapper.toDto(userTypeWest);
    }

    @Override
    public Optional<UserTypeWestDTO> partialUpdate(UserTypeWestDTO userTypeWestDTO) {
        log.debug("Request to partially update UserTypeWest : {}", userTypeWestDTO);

        return userTypeWestRepository
            .findById(userTypeWestDTO.getId())
            .map(existingUserTypeWest -> {
                userTypeWestMapper.partialUpdate(existingUserTypeWest, userTypeWestDTO);

                return existingUserTypeWest;
            })
            .map(userTypeWestRepository::save)
            .map(userTypeWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTypeWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserTypeWests");
        return userTypeWestRepository.findAll(pageable).map(userTypeWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserTypeWestDTO> findOne(Long id) {
        log.debug("Request to get UserTypeWest : {}", id);
        return userTypeWestRepository.findById(id).map(userTypeWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserTypeWest : {}", id);
        userTypeWestRepository.deleteById(id);
    }
}
