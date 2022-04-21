package com.probasteReiniciando.TPTACS.functions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Deprecated
public class JSONWrapper<T> {

    String message = "Everything okay";
    List<T> data;

    public JSONWrapper(List<T> data) {
        this.data = data;
    }

}
