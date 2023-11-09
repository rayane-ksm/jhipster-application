package fr.it_akademy.football.service.mapper;

import fr.it_akademy.football.domain.Entraineur;
import fr.it_akademy.football.domain.Equipe;
import fr.it_akademy.football.service.dto.EntraineurDTO;
import fr.it_akademy.football.service.dto.EquipeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equipe} and its DTO {@link EquipeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EquipeMapper extends EntityMapper<EquipeDTO, Equipe> {
    @Mapping(target = "entraineur", source = "entraineur", qualifiedByName = "entraineurId")
    EquipeDTO toDto(Equipe s);

    @Named("entraineurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntraineurDTO toDtoEntraineurId(Entraineur entraineur);
}
