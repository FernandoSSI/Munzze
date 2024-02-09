package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.User;
import github.FernandoSSI.Munzze.repositories.UserRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User insert(User user){
        User existingEmail = findByEmail(user.getEmail());

        if(existingEmail==null){
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public User update(User newUser){
        User user = findById(newUser.getId());
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());

        return userRepository.save(user);
    }

    public void delete(String id){
        User user = findById(id);
        if(user!=null){
            userRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("Object not found");
        }
    }
}
