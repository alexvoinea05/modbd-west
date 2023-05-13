package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.AppUserWest;
import ro.west.repository.AppUserWestRepository;
import ro.west.service.AppUserWestService;
import ro.west.service.dto.AppUserWestDTO;
import ro.west.service.mapper.AppUserWestMapper;

/**
 * Service Implementation for managing {@link AppUserWest}.
 */
@Service
@Transactional
public class AppUserWestServiceImpl implements AppUserWestService {

    private final Logger log = LoggerFactory.getLogger(AppUserWestServiceImpl.class);

    private final AppUserWestRepository appUserWestRepository;

    private final AppUserWestMapper appUserWestMapper;

    public AppUserWestServiceImpl(AppUserWestRepository appUserWestRepository, AppUserWestMapper appUserWestMapper) {
        this.appUserWestRepository = appUserWestRepository;
        this.appUserWestMapper = appUserWestMapper;
    }

    @Override
    public AppUserWestDTO save(AppUserWestDTO appUserWestDTO) {
        log.debug("Request to save AppUserWest : {}", appUserWestDTO);
        AppUserWest appUserWest = appUserWestMapper.toEntity(appUserWestDTO);
        appUserWest = appUserWestRepository.save(appUserWest);
        return appUserWestMapper.toDto(appUserWest);
    }

    @Override
    public AppUserWestDTO update(AppUserWestDTO appUserWestDTO) {
        log.debug("Request to update AppUserWest : {}", appUserWestDTO);
        AppUserWest appUserWest = appUserWestMapper.toEntity(appUserWestDTO);
        appUserWest = appUserWestRepository.save(appUserWest);
        return appUserWestMapper.toDto(appUserWest);
    }

    @Override
    public Optional<AppUserWestDTO> partialUpdate(AppUserWestDTO appUserWestDTO) {
        log.debug("Request to partially update AppUserWest : {}", appUserWestDTO);

        return appUserWestRepository
            .findById(appUserWestDTO.getId())
            .map(existingAppUserWest -> {
                appUserWestMapper.partialUpdate(existingAppUserWest, appUserWestDTO);

                return existingAppUserWest;
            })
            .map(appUserWestRepository::save)
            .map(appUserWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppUserWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppUserWests");
        return appUserWestRepository.findAll(pageable).map(appUserWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUserWestDTO> findOne(Long id) {
        log.debug("Request to get AppUserWest : {}", id);
        return appUserWestRepository.findById(id).map(appUserWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUserWest : {}", id);
        appUserWestRepository.deleteById(id);
    }
}
