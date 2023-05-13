package ro.west.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.west.domain.AddressWest;
import ro.west.repository.AddressWestRepository;
import ro.west.service.AddressWestService;
import ro.west.service.dto.AddressWestDTO;
import ro.west.service.mapper.AddressWestMapper;

/**
 * Service Implementation for managing {@link AddressWest}.
 */
@Service
@Transactional
public class AddressWestServiceImpl implements AddressWestService {

    private final Logger log = LoggerFactory.getLogger(AddressWestServiceImpl.class);

    private final AddressWestRepository addressWestRepository;

    private final AddressWestMapper addressWestMapper;

    public AddressWestServiceImpl(AddressWestRepository addressWestRepository, AddressWestMapper addressWestMapper) {
        this.addressWestRepository = addressWestRepository;
        this.addressWestMapper = addressWestMapper;
    }

    @Override
    public AddressWestDTO save(AddressWestDTO addressWestDTO) {
        log.debug("Request to save AddressWest : {}", addressWestDTO);
        AddressWest addressWest = addressWestMapper.toEntity(addressWestDTO);
        addressWest = addressWestRepository.save(addressWest);
        return addressWestMapper.toDto(addressWest);
    }

    @Override
    public AddressWestDTO update(AddressWestDTO addressWestDTO) {
        log.debug("Request to update AddressWest : {}", addressWestDTO);
        AddressWest addressWest = addressWestMapper.toEntity(addressWestDTO);
        addressWest = addressWestRepository.save(addressWest);
        return addressWestMapper.toDto(addressWest);
    }

    @Override
    public Optional<AddressWestDTO> partialUpdate(AddressWestDTO addressWestDTO) {
        log.debug("Request to partially update AddressWest : {}", addressWestDTO);

        return addressWestRepository
            .findById(addressWestDTO.getId())
            .map(existingAddressWest -> {
                addressWestMapper.partialUpdate(existingAddressWest, addressWestDTO);

                return existingAddressWest;
            })
            .map(addressWestRepository::save)
            .map(addressWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AddressWestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AddressWests");
        return addressWestRepository.findAll(pageable).map(addressWestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressWestDTO> findOne(Long id) {
        log.debug("Request to get AddressWest : {}", id);
        return addressWestRepository.findById(id).map(addressWestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddressWest : {}", id);
        addressWestRepository.deleteById(id);
    }
}
