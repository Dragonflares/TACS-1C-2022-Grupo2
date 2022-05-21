package com.probasteReiniciando.TPTACS.domain;

import java.util.Comparator;

public final class PositionComparator implements Comparator<Position> {

    private static PositionComparator instance;

    private PositionComparator() {}

    public static PositionComparator getInstance() {
        if (instance == null) {
            instance = new PositionComparator();
        }
        return instance;
    }

    @Override
    public int compare(Position position1, Position position2) {

        return position1.getPoints().compareTo(position2.getPoints());

    }


}
