package fr.it_akademy.football.service.mapper;

import fr.it_akademy.football.domain.Entraineur;
import fr.it_akademy.football.service.dto.EntraineurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entraineur} and its DTO {@link EntraineurDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntraineurMapper extends EntityMapper<EntraineurDTO, Entraineur> {}
