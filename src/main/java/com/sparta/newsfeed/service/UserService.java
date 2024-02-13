package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.PasswordRequestDto;
import com.sparta.newsfeed.dto.RegisterRequestDto;
import com.sparta.newsfeed.dto.UserInfoRequestDto;
import com.sparta.newsfeed.dto.UserInfoResponseDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
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



    @Transactional
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
    public UserInfoResponseDto userUpdate(UserDetailsImpl userDetails, UserInfoRequestDto requestDto){
        User user = userDetails.getUser();

        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

        if(findUser.getUsername().equals(requestDto.getUsername())){
            throw new IllegalArgumentException("이전 유저명과 동일합니다.");
        }

        findUser.userInfoUpdate(requestDto);


        return new UserInfoResponseDto(findUser);
    }

    @Transactional
    public UserInfoResponseDto passwordUpdate(UserDetailsImpl userDetails, PasswordRequestDto requestDto) {
        User user = userDetails.getUser();

        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

        if(passwordEncoder.matches(requestDto.getNewPassword(), findUser.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if(requestDto.getNewPassword().equals(requestDto.getCheckPassword())){
            throw new IllegalArgumentException("이전 비밀번호와 일치합니다.");
        }

        findUser.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));

        return new UserInfoResponseDto(findUser);
    }
}
