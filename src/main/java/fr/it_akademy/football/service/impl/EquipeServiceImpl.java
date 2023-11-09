package fr.it_akademy.football.service.impl;

import fr.it_akademy.football.domain.Equipe;
import fr.it_akademy.football.repository.EquipeRepository;
import fr.it_akademy.football.service.EquipeService;
import fr.it_akademy.football.service.dto.EquipeDTO;
import fr.it_akademy.football.service.mapper.EquipeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.football.domain.Equipe}.
 */
@Service
@Transactional
public class EquipeServiceImpl implements EquipeService {

    private final Logger log = LoggerFactory.getLogger(EquipeServiceImpl.class);

    private final EquipeRepository equipeRepository;

    private final EquipeMapper equipeMapper;

    public EquipeServiceImpl(EquipeRepository equipeRepository, EquipeMapper equipeMapper) {
        this.equipeRepository = equipeRepository;
        this.equipeMapper = equipeMapper;
    }

    @Override
    public EquipeDTO save(EquipeDTO equipeDTO) {
        log.debug("Request to save Equipe : {}", equipeDTO);
        Equipe equipe = equipeMapper.toEntity(equipeDTO);
        equipe = equipeRepository.save(equipe);
        return equipeMapper.toDto(equipe);
    }

    @Override
    public EquipeDTO update(EquipeDTO equipeDTO) {
        log.debug("Request to update Equipe : {}", equipeDTO);
        Equipe equipe = equipeMapper.toEntity(equipeDTO);
        equipe = equipeRepository.save(equipe);
        return equipeMapper.toDto(equipe);
    }

    @Override
    public Optional<EquipeDTO> partialUpdate(EquipeDTO equipeDTO) {
        log.debug("Request to partially update Equipe : {}", equipeDTO);

        return equipeRepository
            .findById(equipeDTO.getId())
            .map(existingEquipe -> {
                equipeMapper.partialUpdate(existingEquipe, equipeDTO);

                return existingEquipe;
            })
            .map(equipeRepository::save)
            .map(equipeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipeDTO> findAll() {
        log.debug("Request to get all Equipes");
        return equipeRepository.findAll().stream().map(equipeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipeDTO> findOne(Long id) {
        log.debug("Request to get Equipe : {}", id);
        return equipeRepository.findById(id).map(equipeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipe : {}", id);
        equipeRepository.deleteById(id);
    }
}
