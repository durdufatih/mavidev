package com.fatih.mavidev.domain.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * this method return list of user
     *
     * @param pageable
     * @return
     */
    public Page<UserResponse> list(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(item -> apply(item));
    }

    public UserResponse findById(Integer id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id cant be null");
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent())
            throw new IllegalArgumentException("Item not found");
        return apply(userEntity.get());
    }

    /**
     * this method convert entity to response object
     *
     * @param userEntity
     * @return
     */
    public UserResponse apply(UserEntity userEntity) {
        if (Objects.isNull(userEntity)) throw new IllegalArgumentException("User not found");
        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getAge());
    }

    public UserResponse create(UserEntity toEntity) {
        if (Objects.isNull(toEntity)) throw new IllegalArgumentException("User cant be null");
        return this.apply(userRepository.save(toEntity));
    }

    public UserResponse update(Integer id, UserEntity toEntity) {
        if (Objects.isNull(id) || Objects.isNull(toEntity)) throw new IllegalArgumentException("Params cant be null");
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent())
            throw new IllegalArgumentException("Item not found");

        UserEntity userEntityResult = userEntity.get();
        userEntityResult.setSurname(toEntity.getSurname());
        userEntityResult.setName(toEntity.getName());
        userEntityResult.setEmail(toEntity.getEmail());
        userEntityResult.setAge(toEntity.getAge());
        return this.apply(userRepository.save(userEntityResult));
    }

    public void delete(Integer id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id cant be null");
        userRepository.deleteById(id);
    }
}
