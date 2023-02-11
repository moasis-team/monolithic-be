package site.moasis.monolithicbe.domain.useraccount;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

@Mapper()
public interface UserAccountMapper {
	UserAccountMapper INSTANCE = Mappers.getMapper(UserAccountMapper.class);
	UserAccount fromUserAccountUpdateRequestDto(UserAccountDto.UserAccountUpdateRequestDto userAccountUpdateRequestDto);
}
