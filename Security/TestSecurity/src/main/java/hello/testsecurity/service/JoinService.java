package hello.testsecurity.service;

import hello.testsecurity.dto.JoinDto;
import hello.testsecurity.entity.UserEntity;
import hello.testsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto joinDto){

        // DB에 동일한 username을 가진 회원이 존재하는지?
        if (!userRepository.existsByUsername(joinDto.getUsername())) {

            UserEntity user = new UserEntity();

            user.setUsername(joinDto.getUsername());

            // 비밀번호 해쉬화
            user.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
            user.setRole("ROLE_USER");

            userRepository.save(user);
            System.out.println("로그인 성공");
        }
        else{
            throw new RuntimeException();
        }
    }
}
