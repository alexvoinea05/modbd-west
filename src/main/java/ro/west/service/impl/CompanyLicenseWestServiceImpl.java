package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.CompanyLicenseWest;
import ro.west.repository.CompanyLicenseWestRepository;
import ro.west.service.CompanyLicenseWestService;
import ro.west.service.dto.CompanyLicenseWestDTO;
import ro.west.service.mapper.CompanyLicenseWestMapper;

/**
 * Service Implementation for managing {@link CompanyLicenseWest}.
 */
@Service
@Transactional
public class CompanyLicenseWestServiceImpl implements CompanyLicenseWestService {

    private final Logger log = LoggerFactory.getLogger(CompanyLicenseWestServiceImpl.class);

    private final CompanyLicenseWestRepository companyLicenseWestRepository;

    private final CompanyLicenseWestMapper companyLicenseWestMapper;

    public CompanyLicenseWestServiceImpl(
        CompanyLicenseWestRepository companyLicenseWestRepository,
        CompanyLicenseWestMapper companyLicenseWestMapper
    ) {
        this.companyLicenseWestRepository = companyLicenseWestRepository;
        this.companyLicenseWestMapper = companyLicenseWestMapper;
    }

    @Override
    public CompanyLicenseWestDTO save(CompanyLicenseWestDTO companyLicenseWestDTO) {
        log.debug("Request to save CompanyLicenseWest : {}", companyLicenseWestDTO);
        CompanyLicenseWest companyLicenseWest = companyLicenseWestMapper.toEntity(companyLicenseWestDTO);
        companyLicenseWest = companyLicenseWestRepository.save(companyLicenseWest);
        return companyLicenseWestMapper.toDto(companyLicenseWest);
    }

    @Override
    public CompanyLicenseWestDTO update(CompanyLicenseWestDTO companyLicenseWestDTO) {
        log.debug("Request to update CompanyLicenseWest : {}", companyLicenseWestDTO);
        CompanyLicenseWest companyLicenseWest = companyLicenseWestMapper.toEntity(companyLicenseWestDTO);
        companyLicenseWest = companyLicenseWestRepository.save(companyLicenseWest);
        return companyLicenseWestMapper.toDto(companyLicenseWest);
    }

    @Override
    public Optional<CompanyLicenseWestDTO> partialUpdate(CompanyLicenseWestDTO companyLicenseWestDTO) {
        log.debug("Request to partially update CompanyLicenseWest : {}", companyLicenseWestDTO);

        return companyLicenseWestRepository
            .findById(companyLicenseWestDTO.getId())
            .map(existingCompanyLicenseWest -> {
                companyLicenseWestMapper.partialUpdate(existingCompanyLicenseWest, companyLicenseWestDTO);

                return existingCompanyLicenseWest;
            })
            .map(companyLicenseWestRepository::save)
            .map(companyLicenseWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyLicenseWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyLicenseWests");
        return companyLicenseWestRepository.findAll(pageable).map(companyLicenseWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyLicenseWestDTO> findOne(Long id) {
        log.debug("Request to get CompanyLicenseWest : {}", id);
        return companyLicenseWestRepository.findById(id).map(companyLicenseWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyLicenseWest : {}", id);
        companyLicenseWestRepository.deleteById(id);
    }
}
