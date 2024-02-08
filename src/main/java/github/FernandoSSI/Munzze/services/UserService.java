package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.User;
import github.FernandoSSI.Munzze.repositories.UserRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private User findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    private User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User insert(User user){
        User existingId = findById(user.getId());
        User existingEmail = findByEmail(user.getEmail());

        if(existingId !=user && existingEmail==null){
            return userRepository.save(user);
        } else {
            return null;
        }
    }


}
