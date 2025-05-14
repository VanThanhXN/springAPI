package com.example.demo.mapper;

import com.example.demo.dto.Request.UloadAvatarRequest;
import com.example.demo.dto.Request.UserCreateRequest;
import com.example.demo.dto.Request.UserUploadequest;
import com.example.demo.dto.Response.UserCreateRepose;
import com.example.demo.dto.Response.UserResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User toUser(UserCreateRequest request);

  UserResponse toUserResponse(User user);

  UserCreateRepose toUserCreateRepose(User user);
  UserResponse toUploadAvatar(UloadAvatarRequest avatar);

  void toUserUpdateResponse(@MappingTarget User user, UserUploadequest request);

}