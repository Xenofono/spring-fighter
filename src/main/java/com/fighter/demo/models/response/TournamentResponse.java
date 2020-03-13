package com.fighter.demo.models.response;

import com.fighter.demo.models.dto.Fighter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * Class only exists to function as a JSON-response to queries about tournaments. it removes unnecessary fields.
 * @author Kristoffer Näsström
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentResponse {

    private String id;
    private List<Fighter> allFighters;
    private LinkedList<Fighter> fightersRemaining;
}
