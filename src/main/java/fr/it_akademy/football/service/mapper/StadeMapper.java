package fr.it_akademy.football.service.mapper;

import fr.it_akademy.football.domain.Stade;
import fr.it_akademy.football.service.dto.StadeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stade} and its DTO {@link StadeDTO}.
 */
@Mapper(componentModel = "spring")
public interface StadeMapper extends EntityMapper<StadeDTO, Stade> {}
