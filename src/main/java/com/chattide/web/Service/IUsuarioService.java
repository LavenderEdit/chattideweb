package com.chattide.web.Service;

import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.generic.ICrudService;

/**
 *
 * @author Juan - Luis
 */
public interface IUsuarioService extends ICrudService<Usuario, Long> {

    Usuario findByEmail(String email);
    
    boolean updateBasicInfo(Long id, String nombre, String email, String avatar);
}
