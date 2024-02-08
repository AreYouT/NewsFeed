package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.RegisterRequestDto;
import com.sparta.newsfeed.dto.UserInfoRequestDto;
import com.sparta.newsfeed.dto.UserInfoResponseDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void register(RegisterRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        String mbti = requestDto.getMbti();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        // 이메일 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 email 입니다.");
        }

        // 사용자 등록
        User user = new User(username, password, email, mbti);
        userRepository.save(user);

    }

    @Transactional
    public UserInfoResponseDto userUpdate(User user, UserInfoRequestDto requestDto){
        user.userInfoUpdate(requestDto);

        return new UserInfoResponseDto(user);
    }

    public UserInfoResponseDto passwordUpdate(User user, String formPassword) {

        if(!user.getPassword().equals(formPassword)){
            throw new IllegalArgumentException();
        }

        user.updatePassword(formPassword);

        return new UserInfoResponseDto(user);
    }
}
