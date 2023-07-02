package ru.otus.socialnetwork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.socialnetwork.model.PostDto;
import ru.otus.socialnetwork.storage.model.PostEntity;

@Mapper(config = MapperConfiguration.class)
public interface PostMapper {

    @Mapping(target = "userId", source = "userId.id")
    PostDto map(PostEntity source);
}
