package facades;

import dto.UserDTO;
import dto.UsersDTO;
import entities.Role;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO createUser(String name, String password, String email, String phoneNumber) {

        EntityManager em = emf.createEntityManager();

        try {

            User u = new User(name, password, email, phoneNumber);
            Role userRole = new Role("user");

            u.addRole(userRole);
            em.getTransaction().begin();

            em.persist(u);

            em.getTransaction().commit();

            return new UserDTO(u);
        } finally {
            em.close();
        }
    }

    public UserDTO deleteUser(String name) {
        EntityManager em = emf.createEntityManager();

        try {
            User user = em.find(User.class, name);
            
            em.getTransaction().begin(); 

            em.remove(user);

            em.getTransaction().commit();

            UserDTO uDTO = new UserDTO(user);

            return uDTO;

        } finally {
            em.close();
        }
    }
    
    public UserDTO editUser(UserDTO u, String name) {
        EntityManager em = emf.createEntityManager();

        try {
            User user = em.find(User.class, name);

            //user.setUserName(u.getName());
            user.setUserPass(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(5)));
            user.setEmail(u.getEmail());
            user.setPhoneNumber(u.getPhoneNumber());

            em.getTransaction().begin();

            em.persist(user);

            em.getTransaction().commit();

            UserDTO uDTO = new UserDTO(user);
            return uDTO;
        } finally {
            em.close();
        }
    }
    public UsersDTO getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            UsersDTO all = new UsersDTO(users);
            return all;
        } finally {
            em.close();
        }
    }
}
