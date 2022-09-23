package tripDemo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tripDemo.hibernate.TripEntity;
import tripDemo.model.Trip;

@Mapper(uses = TripMapper.class)
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    @Mapping(target = "companyId", source = "company.id")
    Trip toDto (TripEntity tripEntity);

    TripEntity toEntity(Trip trip);
}
