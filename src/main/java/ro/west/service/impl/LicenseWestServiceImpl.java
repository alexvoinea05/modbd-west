package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.LicenseWest;
import ro.west.repository.LicenseWestRepository;
import ro.west.service.LicenseWestService;
import ro.west.service.dto.LicenseWestDTO;
import ro.west.service.mapper.LicenseWestMapper;

/**
 * Service Implementation for managing {@link LicenseWest}.
 */
@Service
@Transactional
public class LicenseWestServiceImpl implements LicenseWestService {

    private final Logger log = LoggerFactory.getLogger(LicenseWestServiceImpl.class);

    private final LicenseWestRepository licenseWestRepository;

    private final LicenseWestMapper licenseWestMapper;

    public LicenseWestServiceImpl(LicenseWestRepository licenseWestRepository, LicenseWestMapper licenseWestMapper) {
        this.licenseWestRepository = licenseWestRepository;
        this.licenseWestMapper = licenseWestMapper;
    }

    @Override
    public LicenseWestDTO save(LicenseWestDTO licenseWestDTO) {
        log.debug("Request to save LicenseWest : {}", licenseWestDTO);
        LicenseWest licenseWest = licenseWestMapper.toEntity(licenseWestDTO);
        licenseWest = licenseWestRepository.save(licenseWest);
        return licenseWestMapper.toDto(licenseWest);
    }

    @Override
    public LicenseWestDTO update(LicenseWestDTO licenseWestDTO) {
        log.debug("Request to update LicenseWest : {}", licenseWestDTO);
        LicenseWest licenseWest = licenseWestMapper.toEntity(licenseWestDTO);
        licenseWest = licenseWestRepository.save(licenseWest);
        return licenseWestMapper.toDto(licenseWest);
    }

    @Override
    public Optional<LicenseWestDTO> partialUpdate(LicenseWestDTO licenseWestDTO) {
        log.debug("Request to partially update LicenseWest : {}", licenseWestDTO);

        return licenseWestRepository
            .findById(licenseWestDTO.getId())
            .map(existingLicenseWest -> {
                licenseWestMapper.partialUpdate(existingLicenseWest, licenseWestDTO);

                return existingLicenseWest;
            })
            .map(licenseWestRepository::save)
            .map(licenseWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LicenseWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LicenseWests");
        return licenseWestRepository.findAll(pageable).map(licenseWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LicenseWestDTO> findOne(Long id) {
        log.debug("Request to get LicenseWest : {}", id);
        return licenseWestRepository.findById(id).map(licenseWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LicenseWest : {}", id);
        licenseWestRepository.deleteById(id);
    }
}
