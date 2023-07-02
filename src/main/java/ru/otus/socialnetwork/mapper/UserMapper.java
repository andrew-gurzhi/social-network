package ru.otus.socialnetwork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.socialnetwork.model.UserDto;
import ru.otus.socialnetwork.storage.model.UserEntity;


@Mapper(config = MapperConfiguration.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "friends", ignore = true)
    UserEntity map(UserDto source);

    UserDto map(UserEntity source);
}
