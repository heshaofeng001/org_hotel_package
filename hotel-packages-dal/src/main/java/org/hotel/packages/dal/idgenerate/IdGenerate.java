package org.hotel.packages.dal.idgenerate;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class IdGenerate {

    public String generateId(TableEnums table) {

        return "" + new Random().nextInt();
    }
}
