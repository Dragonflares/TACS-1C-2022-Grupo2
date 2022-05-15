package com.probasteReiniciando.TPTACS.domain;

import java.util.Comparator;

public class UserResultComparator implements Comparator<User> {
    @Override
    public int compare(User user1, User user2) {

        Integer totalResultsUser1 = user1.getResults().stream().mapToInt(Result::getPoints).sum();
        Integer totalResultsUser2 = user2.getResults().stream().mapToInt(Result::getPoints).sum();
        return totalResultsUser1.compareTo(totalResultsUser2);

    }
}
