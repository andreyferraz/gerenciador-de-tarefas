package br.com.andreyferraz.todolist.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createUserService(UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> error = new HashMap<>();
        error.put("message", "Usuário já existe");
        error.put("code", String.valueOf(status.value()));

        if (user != null) {
            return ResponseEntity.status(status).body(error);
        }

        var passwordHashred = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var userCreated = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
