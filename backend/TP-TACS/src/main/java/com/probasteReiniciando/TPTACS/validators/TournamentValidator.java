package com.probasteReiniciando.TPTACS.validators;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import org.apache.commons.lang3.EnumUtils;

import java.text.SimpleDateFormat;
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

    public static void validateRangeDate(Date startDate, Date endDate) {

        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.setTime(startDate);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(endDate);

        Calendar calendarActualDate = Calendar.getInstance();
        calendarActualDate.setTime(new Date());
        calendarActualDate.set(Calendar.HOUR_OF_DAY, 0);
        calendarActualDate.set(Calendar.MINUTE, 0);
        calendarActualDate.set(Calendar.SECOND, 0);
        calendarActualDate.set(Calendar.MILLISECOND, 0);

        if (calendarEndDate.compareTo(calendarStartDate) < 0 || calendarStartDate.compareTo(calendarActualDate) < 0) {
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


}
