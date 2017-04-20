package uk.co.agileworx.tennis.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.co.agileworx.tennis.model.Player;
import uk.co.agileworx.tennis.model.Ranking;
import uk.co.agileworx.tennis.repo.PlayerRepository;

import java.text.NumberFormat;

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

    @Scheduled(fixedRate = 60*60*1000)
    public void scrapeRankingForDate() throws Exception {
        LOGGER.info("Started Scraping");
        final Document doc = Jsoup.connect("http://www.atpworldtour.com/en/rankings/singles?rankDate=2017-04-10&rankRange=1-5000").maxBodySize(0).get();
        final Elements rankingRows = doc.select("table.mega-table").select("tbody").select("tr");

        for (Element element : rankingRows) {
            final String playerurl = element.select("td.player-cell").select("a").attr("href");
            final String playerATPID = playerurl.split("/")[4];

            Player currentPlayer = playerRepo.findOne(playerATPID);
            if (currentPlayer == null) {
                final String playerName = element.select("td.player-cell").text();
                final String playerATPNAME = playerurl.split("/")[3];

                currentPlayer = new Player(playerATPID, playerName);

                LOGGER.info("New player found : ".concat(playerName));

            }

            final Elements pointesCelleElement = element.select("td.points-cell");
            final String rankingNumber = element.select("td.rank-cell").text().replace("T", "");
            final int playerRanking = Integer.parseInt(rankingNumber);
            final int rankingPoints = NumberFormat.getNumberInstance(java.util.Locale.US).parse(pointesCelleElement.text()).intValue();

            final Ranking currentRanking = new Ranking(playerRanking, rankingPoints, null);
            currentPlayer.addRanking(currentRanking);

            playerRepo.save(currentPlayer);
            LOGGER.info("Saved player : ".concat(currentPlayer.getName()));
        }

    }
}
