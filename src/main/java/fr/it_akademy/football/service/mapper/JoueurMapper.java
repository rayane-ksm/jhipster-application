package fr.it_akademy.football.service.mapper;

import fr.it_akademy.football.domain.Equipe;
import fr.it_akademy.football.domain.Joueur;
import fr.it_akademy.football.service.dto.EquipeDTO;
import fr.it_akademy.football.service.dto.JoueurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Joueur} and its DTO {@link JoueurDTO}.
 */
@Mapper(componentModel = "spring")
public interface JoueurMapper extends EntityMapper<JoueurDTO, Joueur> {
    @Mapping(target = "equipe", source = "equipe", qualifiedByName = "equipeId")
    JoueurDTO toDto(Joueur s);

    @Named("equipeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EquipeDTO toDtoEquipeId(Equipe equipe);
}
