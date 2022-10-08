package sit.int221.oasip.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasip.DTO.*;
import sit.int221.oasip.Entity.User;
import sit.int221.oasip.Error.ValidationHandler;
import sit.int221.oasip.Repository.UserRepository;
import sit.int221.oasip.Utils.ListMapper;
import sit.int221.oasip.config.JwtTokenUtil;
//import sit.int221.oasip.config.JwtTokenUtil;
//import sit.int221.oasip.model.JwtResponse;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;


    public List<UserDTO> getAllUser(String sortBy) {
        List<User> list = repository.findAll(Sort.by(sortBy).ascending());
        return listMapper.mapList(list, UserDTO.class, modelMapper);
    }

    public UserDTO getUserWithId(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User id " + id +
                        "Does Not Exist !!!"
                ));

        return modelMapper.map(user, UserDTO.class);
    }


    public User save(AddUserDTO newUser) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        newUser.setPassword(encoder.encode(newUser.getPassword()));

        newUser.setName(newUser.getName().trim());
        newUser.setEmail(newUser.getEmail().trim());
        newUser.setPassword(newUser.getPassword().trim());

        User user = modelMapper.map(newUser, User.class);

        return repository.saveAndFlush(user);
    }

    public void deleteById(Integer id) {
        repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does not exist !!!"));
        repository.deleteById(id);
    }


    public EditUserDTO updateUser(EditUserDTO updateUser, Integer id) {
        User user = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, id + "does not exist!!!"));

        user.setName(updateUser.getName().trim());
        user.setEmail(updateUser.getEmail().trim());
        user.setRole(updateUser.getRole());

        repository.saveAndFlush(user);
        return updateUser;
    }

//    public boolean login (String email, String password) {
//        List<User> allUser = repository.findAll();
//
//        for (User user : allUser) {
//            if (user.getEmail().equals(email.trim())) {
//                if (matchPassword(password, user.getPassword())) {
//                    throw new ResponseStatusException(HttpStatus.OK , "Login Successful");
//                }else {
//                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "c");
//                }
//            }
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND , "A user with the specified email DOSE NOT exist");
//    }
//
//    public boolean matchPassword(String rawPassword, String encodePassword) {
//        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
//        boolean validPassword = encoder.matches(rawPassword, encodePassword);
//        System.out.println(validPassword);
//        return validPassword;
//    }

    public ResponseEntity login(LoginDTO userlogin) throws  Exception {
        if (repository.existsByEmail(userlogin.getEmail())) {
            User user = repository.findByEmail(userlogin.getEmail());
            if (argon2PasswordEncoder.matches(userlogin.getPassword(), user.getPassword())) {
//                authentication(userlogin.getEmail() , userlogin.getPassword());

                final UserDetails userDetails = userDetailsService.loadUserByUsername(userlogin.getEmail());
                final String token = jwtTokenUtil.generateToken(userDetails);
                final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

                return ResponseEntity.ok(new ResponseCreateTokenDTO("Login Success",user.getName(),user.getRole(),token,refreshToken));

            } else {
                return ValidationHandler.ExceptionError(HttpStatus.UNAUTHORIZED, "Password Incorrect");
            }
        } else {
            return ValidationHandler.ExceptionError(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist");

        }
    }

//    private void authentication(String email, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
}
