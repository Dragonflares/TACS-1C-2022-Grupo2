package com.probasteReiniciando.TPTACS.config;

import com.probasteReiniciando.TPTACS.dao.ResultDAO;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapperTacs modelMapper() {
        ModelMapperTacs modelMapper = new ModelMapperTacs();

        this.tournamentMapping(modelMapper);
        this.resultMapping(modelMapper);
        this.resultDtoMapping(modelMapper);
        this.resultDAOMapping(modelMapper);
        this.resultFromDAOMapping(modelMapper);

        return modelMapper;
    }

    private void tournamentMapping(ModelMapperTacs modelMapper) {
        modelMapper.createTypeMap(Tournament.class, TournamentDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getName(), TournamentDto::setName))
                .addMappings(mapper -> mapper.map(Tournament::getEndDate, TournamentDto::setEndDate))
                .addMappings(mapper -> mapper.map(Tournament::getStartDate, TournamentDto::setStartDate))
                .addMappings(mapper -> mapper.map(Tournament::getLanguage, TournamentDto::setLanguage))
                .addMappings(mapper -> mapper.map(Tournament::getPrivacy, TournamentDto::setPrivacy))
                .addMappings(mapper -> mapper.map(Tournament::getOwner, TournamentDto::setOwner))
        ;
    }

    private void resultMapping(ModelMapperTacs modelMapper) {
        modelMapper.createTypeMap(Result.class, ResultDto.class)
                .addMappings(mapper -> mapper.map(Result::getDate, ResultDto::setDate))
                .addMappings(mapper -> mapper.map(Result::getLanguage, ResultDto::setLanguage))
                .addMappings(mapper -> mapper.map(Result::getPoints, ResultDto::setPoints))
        ;
    }

    private void resultDtoMapping(ModelMapperTacs modelMapper) {
        modelMapper.createTypeMap(ResultDto.class, Result.class)
                .addMappings(mapper -> mapper.map(ResultDto::getDate, Result::setDate))
                .addMappings(mapper -> mapper.map(ResultDto::getLanguage, Result::setLanguage))
                .addMappings(mapper -> mapper.map(ResultDto::getPoints, Result::setPoints))
        ;
    }

    private void resultDAOMapping(ModelMapperTacs modelMapper) {
        Converter<LocalDate, String> formatDate = ctx -> ctx.getSource() != null
                ? formatDateToString(ctx.getSource())
                : "";
        modelMapper.createTypeMap(Result.class, ResultDAO.class)
                .addMappings(mapper -> mapper.using(formatDate).map(Result::getDate, ResultDAO::setDate))
                .addMappings(mapper -> mapper.map(Result::getLanguage, ResultDAO::setLanguage))
                .addMappings(mapper -> mapper.map(Result::getPoints, ResultDAO::setPoints))
        ;
    }

    private String formatDateToString(LocalDate source) {
        return source.format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
    }






    private void resultFromDAOMapping(ModelMapperTacs modelMapper) {

        Provider<LocalDate> localDateProvider = new AbstractProvider<LocalDate>() {
            @Override
            public LocalDate get() {
                return LocalDate.now();
            }
        };

        Converter<String, LocalDate> toStringDate = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-mm-dd");
                LocalDate localDate = LocalDate.parse(source, format);
                return localDate;
            }
        };


        modelMapper.createTypeMap(ResultDAO.class, Result.class)
                .addMappings(mapper -> mapper.using(toStringDate).map(ResultDAO::getDate, Result::setDate))
                .addMappings(mapper -> mapper.map(ResultDAO::getLanguage, Result::setLanguage))
                .addMappings(mapper -> mapper.map(ResultDAO::getPoints, Result::setPoints))
        ;
        modelMapper.addConverter(toStringDate);
    }

    private LocalDate formatStringToDate(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        formatter = formatter.withLocale(Locale.CANADA);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        return LocalDate.parse(source, formatter);
    }

}
