package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.request.PasswordRequestDto;
import com.sparta.newsfeed.dto.request.RegisterRequestDto;
import com.sparta.newsfeed.dto.request.UserUpdateRequestDto;
import com.sparta.newsfeed.dto.response.ResponseDto;
import com.sparta.newsfeed.dto.response.UserInfoResponseDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
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
            throw new DataIntegrityViolationException("중복된 username 입니다.");
        }

        // 이메일 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new DataIntegrityViolationException("이미 사용중인 email 입니다.");
        }

        // 사용자 등록
        User user = new User(username, password, email, mbti);
        userRepository.save(user);

    }

    public ResponseDto getUserInfo(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("유저가 존재하지 않습니다.")
        );

        UserInfoResponseDto responseDto = new UserInfoResponseDto(findUser);

        return ResponseDto.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("유저 조회를 조회했습니다.")
                .data(responseDto)
                .build();
    }

    @Transactional
    public void userUpdate(User user, UserUpdateRequestDto requestDto){

        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new NoSuchElementException("유저가 존재하지 않습니다.")
        );

        findUser.userInfoUpdate(requestDto);
    }

    @Transactional
    public void passwordUpdate(UserDetailsImpl userDetails, PasswordRequestDto requestDto) {
        User user = userDetails.getUser();

        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new NoSuchElementException("유저가 존재하지 않습니다.")
        );

        if(passwordEncoder.matches(requestDto.getNewPassword(), findUser.getPassword())){
            throw new AccessDeniedException("비밀번호가 일치하지 않습니다.");
        }

        if(requestDto.getNewPassword().equals(requestDto.getCheckPassword())){
            throw new DataIntegrityViolationException("이전 비밀번호와 일치합니다.");
        }

        findUser.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
    }
}
