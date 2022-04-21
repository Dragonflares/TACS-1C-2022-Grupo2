package com.probasteReiniciando.TPTACS.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Data
@Builder
public class PingDto {
   private final String message;
}
