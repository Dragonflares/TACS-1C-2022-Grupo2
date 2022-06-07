package com.probasteReiniciando.TPTACS.dao;


import com.probasteReiniciando.TPTACS.domain.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class UserDAO {

    @Id
    private String id;

    private String username;

    private String password;

    private List<ResultDAO> resultDAOS = new ArrayList<>();


}
