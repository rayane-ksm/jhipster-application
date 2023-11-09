package fr.it_akademy.football.service.impl;

import fr.it_akademy.football.domain.Entraineur;
import fr.it_akademy.football.repository.EntraineurRepository;
import fr.it_akademy.football.service.EntraineurService;
import fr.it_akademy.football.service.dto.EntraineurDTO;
import fr.it_akademy.football.service.mapper.EntraineurMapper;
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
 * Service Implementation for managing {@link fr.it_akademy.football.domain.Entraineur}.
 */
@Service
@Transactional
public class EntraineurServiceImpl implements EntraineurService {

    private final Logger log = LoggerFactory.getLogger(EntraineurServiceImpl.class);

    private final EntraineurRepository entraineurRepository;

    private final EntraineurMapper entraineurMapper;

    public EntraineurServiceImpl(EntraineurRepository entraineurRepository, EntraineurMapper entraineurMapper) {
        this.entraineurRepository = entraineurRepository;
        this.entraineurMapper = entraineurMapper;
    }

    @Override
    public EntraineurDTO save(EntraineurDTO entraineurDTO) {
        log.debug("Request to save Entraineur : {}", entraineurDTO);
        Entraineur entraineur = entraineurMapper.toEntity(entraineurDTO);
        entraineur = entraineurRepository.save(entraineur);
        return entraineurMapper.toDto(entraineur);
    }

    @Override
    public EntraineurDTO update(EntraineurDTO entraineurDTO) {
        log.debug("Request to update Entraineur : {}", entraineurDTO);
        Entraineur entraineur = entraineurMapper.toEntity(entraineurDTO);
        entraineur = entraineurRepository.save(entraineur);
        return entraineurMapper.toDto(entraineur);
    }

    @Override
    public Optional<EntraineurDTO> partialUpdate(EntraineurDTO entraineurDTO) {
        log.debug("Request to partially update Entraineur : {}", entraineurDTO);

        return entraineurRepository
            .findById(entraineurDTO.getId())
            .map(existingEntraineur -> {
                entraineurMapper.partialUpdate(existingEntraineur, entraineurDTO);

                return existingEntraineur;
            })
            .map(entraineurRepository::save)
            .map(entraineurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntraineurDTO> findAll() {
        log.debug("Request to get all Entraineurs");
        return entraineurRepository.findAll().stream().map(entraineurMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the entraineurs where Equipe is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EntraineurDTO> findAllWhereEquipeIsNull() {
        log.debug("Request to get all entraineurs where Equipe is null");
        return StreamSupport
            .stream(entraineurRepository.findAll().spliterator(), false)
            .filter(entraineur -> entraineur.getEquipe() == null)
            .map(entraineurMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntraineurDTO> findOne(Long id) {
        log.debug("Request to get Entraineur : {}", id);
        return entraineurRepository.findById(id).map(entraineurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entraineur : {}", id);
        entraineurRepository.deleteById(id);
    }
}
