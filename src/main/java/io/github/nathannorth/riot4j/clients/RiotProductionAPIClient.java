package io.github.nathannorth.riot4j.clients;

import io.github.nathannorth.riot4j.api.account.RiotAccount;
import io.github.nathannorth.riot4j.api.match.MatchlistEntry;
import io.github.nathannorth.riot4j.api.match.ValMatch;
import io.github.nathannorth.riot4j.api.match.ValMatchlist;
import io.github.nathannorth.riot4j.enums.ValRecentQueue;
import io.github.nathannorth.riot4j.enums.ValRegion;
import io.github.nathannorth.riot4j.json.Mapping;
import io.github.nathannorth.riot4j.json.valMatch.MatchData;
import io.github.nathannorth.riot4j.json.valMatch.MatchlistData;
import io.github.nathannorth.riot4j.json.valMatch.RecentMatchesData;
import io.github.nathannorth.riot4j.queues.RateLimits;
import reactor.core.publisher.Mono;

public class RiotProductionAPIClient extends RiotDevelopmentAPIClient {
    protected RiotProductionAPIClient(ClientConfig config) {
        super(config);
    }

    public Mono<RecentMatchesData> getRecentMatches(ValRegion region, ValRecentQueue queue) {
        return buckets.pushToBucket(RateLimits.VAL_RECENT_MATCHES, getRecentMatchesRaw(token, region.toString(), queue.toString()))
                .map(Mapping.map(RecentMatchesData.class));
    }

    public Mono<ValMatch> getMatch(ValRegion region, String matchId) {
        return buckets.pushToBucket(RateLimits.VAL_MATCH, getMatchRaw(token, region.toString(), matchId))
                .map(Mapping.map(MatchData.class))
                .map(matchData -> new ValMatch(matchData));
    }

    public Mono<ValMatch> getMatch(MatchlistEntry entry) {
        return getMatch(entry.region(), entry.matchId());
    }

    public Mono<ValMatchlist> getMatchList(ValRegion region, String puuid) {
        return buckets.pushToBucket(RateLimits.VAL_MATCHLIST, getMatchListRaw(token, region.toString(), puuid))
                .map(Mapping.map(MatchlistData.class))
                .map(data -> new ValMatchlist(this, data, region));
    }

    public Mono<ValMatchlist> getMatchList(RiotAccount account) {
        return account.getRegion()
                .flatMap(valRegion -> getMatchList(valRegion, account.puuid()));
    }

    @Override
    public Mono<RiotAPIClient> test() {
        return getRecentMatches(valRegion, ValRecentQueue.UNRATED)
                .thenReturn(this);
    }
}
