package uk.co.agileworx.tennis.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;
import uk.co.agileworx.tennis.model.Player;
import uk.co.agileworx.tennis.model.Ranking;
import uk.co.agileworx.tennis.repo.PlayerRepository;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Created by conor on 06/04/2017.
 */
@Component
public class RankingScraper {
    private Pattern p = Pattern.compile("(([A-Z].*[0-9])|([0-9].*[A-Z]))");

    private static final Logger LOGGER = Logger.getLogger(RankingScraper.class.getName());

    @Autowired
    private PlayerRepository playerRepo;

    @Scheduled(fixedRate = 5000)
    public void scrapeRankingForDate() throws Exception {
        LOGGER.info("Started Scraping");
        final Document doc = Jsoup.connect("http://www.atpworldtour.com/en/rankings/singles?rankDate=2017-04-10&rankRange=1-5000").maxBodySize(0).get();
        final Elements rankingElements = doc.select("table.mega-table");

        LOGGER.info("\n "+rankingElements.toString()+"\n");

        final Elements rankingRows = rankingElements.select("tr");

        final List<Player> players = new ArrayList<>();
        final Map<String, Ranking> rankings = new HashMap<String, Ranking>();
        int i = 0;

        for (Element element : rankingRows) {
            i++;
            final String playerurl = element.select("td.player-cell").select("a").attr("href");
            final String playerName = element.select("td.player-cell").text();
            final String playerATPID = playerurl.split("/")[4];
            final String playerATPNAME = playerurl.split("/")[3];

            LOGGER.info("Position: ".concat(Integer.toString(i).concat("\n\n\t-----------------Player Name :".concat(playerName)
                    .concat(" Player URL : ".concat(playerurl)))));

            final Player player = new Player(playerATPID, playerName);
            players.add(player);

            final Elements pointesCelleElement = element.select("td.points-cell");
            final String rankingNumber = element.select("td.rank-cell").text().replace("T", "");
            final int playerRanking = Integer.parseInt(rankingNumber);
            final int rankingPoints = NumberFormat.getNumberInstance(java.util.Locale.US).parse(pointesCelleElement.text()).intValue();

            final Ranking currentRanking = new Ranking(playerRanking, rankingPoints, null);

            rankings.put(playerATPID, currentRanking);
            LOGGER.info("\n\n\t!!-----------------Completed For :".concat(playerName));

        }

        playerRepo.save(players);

    }
}
