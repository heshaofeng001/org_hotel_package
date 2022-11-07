package org.hotel.packages.dal.idgenerate;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;


@Component
public class IdGenerate {

    public String generateId(TableEnums table) {

        return "21" + System.currentTimeMillis();
    }
}
