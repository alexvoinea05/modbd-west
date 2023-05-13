package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.CompanyWest;
import ro.west.repository.CompanyWestRepository;
import ro.west.service.CompanyWestService;
import ro.west.service.dto.CompanyWestDTO;
import ro.west.service.mapper.CompanyWestMapper;

/**
 * Service Implementation for managing {@link CompanyWest}.
 */
@Service
@Transactional
public class CompanyWestServiceImpl implements CompanyWestService {

    private final Logger log = LoggerFactory.getLogger(CompanyWestServiceImpl.class);

    private final CompanyWestRepository companyWestRepository;

    private final CompanyWestMapper companyWestMapper;

    public CompanyWestServiceImpl(CompanyWestRepository companyWestRepository, CompanyWestMapper companyWestMapper) {
        this.companyWestRepository = companyWestRepository;
        this.companyWestMapper = companyWestMapper;
    }

    @Override
    public CompanyWestDTO save(CompanyWestDTO companyWestDTO) {
        log.debug("Request to save CompanyWest : {}", companyWestDTO);
        CompanyWest companyWest = companyWestMapper.toEntity(companyWestDTO);
        companyWest = companyWestRepository.save(companyWest);
        return companyWestMapper.toDto(companyWest);
    }

    @Override
    public CompanyWestDTO update(CompanyWestDTO companyWestDTO) {
        log.debug("Request to update CompanyWest : {}", companyWestDTO);
        CompanyWest companyWest = companyWestMapper.toEntity(companyWestDTO);
        companyWest = companyWestRepository.save(companyWest);
        return companyWestMapper.toDto(companyWest);
    }

    @Override
    public Optional<CompanyWestDTO> partialUpdate(CompanyWestDTO companyWestDTO) {
        log.debug("Request to partially update CompanyWest : {}", companyWestDTO);

        return companyWestRepository
            .findById(companyWestDTO.getId())
            .map(existingCompanyWest -> {
                companyWestMapper.partialUpdate(existingCompanyWest, companyWestDTO);

                return existingCompanyWest;
            })
            .map(companyWestRepository::save)
            .map(companyWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyWests");
        return companyWestRepository.findAll(pageable).map(companyWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyWestDTO> findOne(Long id) {
        log.debug("Request to get CompanyWest : {}", id);
        return companyWestRepository.findById(id).map(companyWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyWest : {}", id);
        companyWestRepository.deleteById(id);
    }
}
