/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacobsimonsen
 */
public class UsersDTO {
    List<UserDTO> all = new ArrayList();

    public UsersDTO(List<User> personEntities) {
        personEntities.forEach((u) -> {
            all.add(new UserDTO(u));
        });
    }

    public List<UserDTO> getAll() {
        return all;
    }
    
}
