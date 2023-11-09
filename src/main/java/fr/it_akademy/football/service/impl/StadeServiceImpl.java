package fr.it_akademy.football.service.impl;

import fr.it_akademy.football.domain.Stade;
import fr.it_akademy.football.repository.StadeRepository;
import fr.it_akademy.football.service.StadeService;
import fr.it_akademy.football.service.dto.StadeDTO;
import fr.it_akademy.football.service.mapper.StadeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.football.domain.Stade}.
 */
@Service
@Transactional
public class StadeServiceImpl implements StadeService {

    private final Logger log = LoggerFactory.getLogger(StadeServiceImpl.class);

    private final StadeRepository stadeRepository;

    private final StadeMapper stadeMapper;

    public StadeServiceImpl(StadeRepository stadeRepository, StadeMapper stadeMapper) {
        this.stadeRepository = stadeRepository;
        this.stadeMapper = stadeMapper;
    }

    @Override
    public StadeDTO save(StadeDTO stadeDTO) {
        log.debug("Request to save Stade : {}", stadeDTO);
        Stade stade = stadeMapper.toEntity(stadeDTO);
        stade = stadeRepository.save(stade);
        return stadeMapper.toDto(stade);
    }

    @Override
    public StadeDTO update(StadeDTO stadeDTO) {
        log.debug("Request to update Stade : {}", stadeDTO);
        Stade stade = stadeMapper.toEntity(stadeDTO);
        stade = stadeRepository.save(stade);
        return stadeMapper.toDto(stade);
    }

    @Override
    public Optional<StadeDTO> partialUpdate(StadeDTO stadeDTO) {
        log.debug("Request to partially update Stade : {}", stadeDTO);

        return stadeRepository
            .findById(stadeDTO.getId())
            .map(existingStade -> {
                stadeMapper.partialUpdate(existingStade, stadeDTO);

                return existingStade;
            })
            .map(stadeRepository::save)
            .map(stadeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StadeDTO> findAll() {
        log.debug("Request to get all Stades");
        return stadeRepository.findAll().stream().map(stadeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the stades where Equipe is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StadeDTO> findAllWhereEquipeIsNull() {
        log.debug("Request to get all stades where Equipe is null");
        return StreamSupport
            .stream(stadeRepository.findAll().spliterator(), false)
            .filter(stade -> stade.getEquipe() == null)
            .map(stadeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StadeDTO> findOne(Long id) {
        log.debug("Request to get Stade : {}", id);
        return stadeRepository.findById(id).map(stadeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stade : {}", id);
        stadeRepository.deleteById(id);
    }
}
