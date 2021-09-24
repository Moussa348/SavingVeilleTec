package com.keita.spendingcontrol.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.keita.spendingcontrol.model.dto.ArticleDetail;
import lombok.extern.java.Log;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log
@Service
public class AuthorizationService {

    public boolean isConnected(Long id) {
        return ((DecodedJWT) SecurityContextHolder.getContext().getAuthentication().getCredentials())
                .getClaim(JwtService.PERSON_ID_CLAIM).asLong().equals(id);
    }

    public boolean listBelongToUserConnected(List<ArticleDetail> articleDetails){
        return !articleDetails.stream().map(articleDetail -> isConnected(articleDetail.getPersonId()))
                .collect(Collectors.toSet()).contains(false);
    }
}
