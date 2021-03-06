package tech.nathann.riot4j.json.valMatch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutablePlayerStatsData.class)
@JsonDeserialize(as = ImmutablePlayerStatsData.class)
public interface PlayerStatsData {
    int score();
    int roundsPlayed();
    int kills();
    int deaths();
    int assists();
    int playtimeMillis();
    Optional<AbilityCastsData> abilityCasts();
}
