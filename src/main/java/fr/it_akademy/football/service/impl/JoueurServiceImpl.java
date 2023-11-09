package fr.it_akademy.football.service.impl;

import fr.it_akademy.football.domain.Joueur;
import fr.it_akademy.football.repository.JoueurRepository;
import fr.it_akademy.football.service.JoueurService;
import fr.it_akademy.football.service.dto.JoueurDTO;
import fr.it_akademy.football.service.mapper.JoueurMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.football.domain.Joueur}.
 */
@Service
@Transactional
public class JoueurServiceImpl implements JoueurService {

    private final Logger log = LoggerFactory.getLogger(JoueurServiceImpl.class);

    private final JoueurRepository joueurRepository;

    private final JoueurMapper joueurMapper;

    public JoueurServiceImpl(JoueurRepository joueurRepository, JoueurMapper joueurMapper) {
        this.joueurRepository = joueurRepository;
        this.joueurMapper = joueurMapper;
    }

    @Override
    public JoueurDTO save(JoueurDTO joueurDTO) {
        log.debug("Request to save Joueur : {}", joueurDTO);
        Joueur joueur = joueurMapper.toEntity(joueurDTO);
        joueur = joueurRepository.save(joueur);
        return joueurMapper.toDto(joueur);
    }

    @Override
    public JoueurDTO update(JoueurDTO joueurDTO) {
        log.debug("Request to update Joueur : {}", joueurDTO);
        Joueur joueur = joueurMapper.toEntity(joueurDTO);
        joueur = joueurRepository.save(joueur);
        return joueurMapper.toDto(joueur);
    }

    @Override
    public Optional<JoueurDTO> partialUpdate(JoueurDTO joueurDTO) {
        log.debug("Request to partially update Joueur : {}", joueurDTO);

        return joueurRepository
            .findById(joueurDTO.getId())
            .map(existingJoueur -> {
                joueurMapper.partialUpdate(existingJoueur, joueurDTO);

                return existingJoueur;
            })
            .map(joueurRepository::save)
            .map(joueurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JoueurDTO> findAll() {
        log.debug("Request to get all Joueurs");
        return joueurRepository.findAll().stream().map(joueurMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JoueurDTO> findOne(Long id) {
        log.debug("Request to get Joueur : {}", id);
        return joueurRepository.findById(id).map(joueurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Joueur : {}", id);
        joueurRepository.deleteById(id);
    }
}
