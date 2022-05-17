package com.probasteReiniciando.TPTACS.validators;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import org.apache.commons.lang3.EnumUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class TournamentValidator {

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static void validateLanguage(String language) {
        if (!EnumUtils.isValidEnum(Language.class, language)) {
            String messageError = String.format("%s is not a valid language or is not supported", language);
            throw new TournamentBadRequestException(messageError);
        }

    }

    public static void validateRangeDate(LocalDate startDate, LocalDate endDate) {

        LocalDate currentDate = LocalDate.now();

        if (endDate.isBefore(startDate) || startDate.isBefore(currentDate)) {
            String messageError = String.format("[%s, %s] range date is invalid", startDate, endDate);
            throw new TournamentBadRequestException(messageError);
        }

    }

    public static void validatePrivacy(String privacy) {
        if (!EnumUtils.isValidEnum(Privacy.class, privacy)) {
            String messageError = String.format("%s is not supported", privacy);
            throw new TournamentBadRequestException(messageError);
        }

    }


    public static void validateStartDate(LocalDate tournamentStartDate) {
        if(tournamentStartDate.isBefore(LocalDate.now())){
            String messageError = String.format("You cant join the tournament. The tournament has already started.");
            throw new TournamentBadRequestException(messageError);
        }

    }
}
