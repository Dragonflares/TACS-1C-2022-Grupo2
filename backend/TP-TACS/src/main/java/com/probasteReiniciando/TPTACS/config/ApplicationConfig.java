package com.probasteReiniciando.TPTACS.config;

import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        this.tournamentMapping(modelMapper);

        return modelMapper;
    }

    private void tournamentMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Tournament.class, TournamentDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getName(), TournamentDto::setName))
                .addMappings(mapper -> mapper.map(Tournament::getEndDate, TournamentDto::setEndDate))
                .addMappings(mapper -> mapper.map(Tournament::getStartDate, TournamentDto::setStartDate))
                .addMappings(mapper -> mapper.map(Tournament::getLanguage, TournamentDto::setLanguage))
                .addMappings(mapper -> mapper.map(Tournament::getPrivacy, TournamentDto::setPrivacy))
                .addMappings(mapper -> mapper.map(Tournament::getOwner, TournamentDto::setOwner))
        ;
    }



}
