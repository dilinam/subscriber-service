package org.dtf202.subscriberservice.utils;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class StringHelpers {

    public String generateRandomStringUsingEmail(String email) {

        UUID uuid = UUID.randomUUID();
        return uuid + getAlphaNumericString(email.length());
    }

    public String getAlphaNumericString(int n) {

        // choose a Character random from this String
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of alphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to alphaNumericString variable length
            int index
                = (int) (alphaNumericString.length()
                * Math.random());

            // add Character one by one in end of sb
            sb.append(alphaNumericString
                          .charAt(index));
        }

        return sb.toString();
    }

}
