package be.devfest.dabomb.entities;

import java.util.HashMap;

/**
 * TODO describe class
 *
 * @author Stefan Hoth <sh@jnamic.com>
 *         date: 17.11.13 16:13
 * @since TODO add version
 */
public class Question {

    public String question;
    public HashMap<String, String> options;
    public String answer;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Question{");
        sb.append("question='").append(question).append('\'');
        sb.append(", options=").append(options);
        sb.append(", answer='").append(answer).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
