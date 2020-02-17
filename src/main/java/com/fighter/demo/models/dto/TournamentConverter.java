package com.fighter.demo.models.dto;

import com.fighter.demo.models.response.TournamentResponse;
import org.springframework.beans.BeanUtils;

public class TournamentConverter {

    public static TournamentResponse tournamentToResponse(Tournament tournament){
        TournamentResponse response = new TournamentResponse();
        BeanUtils.copyProperties(tournament, response);
        return response;
    }
}
