package User;

//import com.example.demo.gamecatalog.dao.MySQLUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private  UserRepository userRepository;

    @GetMapping("/")
    public List<User> getAllUsers(@RequestHeader("X-UserId") String userID) {
        return userRepository.findAll();
    }
    
    @GetMapping("/{userId}")
    public Optional<User> getUserById(@RequestHeader("X-UserId") String userID, @PathVariable Long userId) {
        return userRepository.findById(userId);
    }

    @PostMapping
    public User addUser(@RequestHeader("X-UserId") String userID, @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestHeader("X-UserId") String userID, @PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Mettre à jour les champs
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());

            // Enregistrer les modifications
            userRepository.save(existingUser);

            return ResponseEntity.ok("User updated successfully.");
        } else {
            // Retourner une réponse 404 si l'utilisateur n'existe pas
            return ResponseEntity.status(404).body("User not found.");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@RequestHeader("X-UserId") String userID, @PathVariable Long id) {
        userRepository.deleteById(id);

    }
}



