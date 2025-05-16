package com.chattide.web.Service;

import com.chattide.web.Modelo.Likes;
import com.chattide.web.Service.generic.ICrudService;
import java.util.Optional;

/**
 *
 * @author Juan - Luis
 */
public interface ILikeService extends ICrudService<Likes, Long> {

    Optional<Likes> findByUserAndPublication(Long userId, Long pubId);

    boolean deleteLike(Long likeId);

    Long countByPublication(Long idPub);
}
