package com.probasteReiniciando.TPTACS.config;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapperTacs modelMapper() {
        ModelMapperTacs modelMapper = new ModelMapperTacs();

        this.tournamentMapping(modelMapper);
        this.resultMapping(modelMapper);

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
                .addMappings(mapper -> mapper.map(Result::getUsername, ResultDto::setUsername))
        ;
    }


}
