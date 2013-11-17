package be.devfest.dabomb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Stefan Hoth <sh@jnamic.com>
 *         date: 17.11.13 16:13
 * @since TODO add version
 */
public class Question {

    public String question;
    public List<String> options;
    @SerializedName("answer")
    public Integer answerId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Question{");
        sb.append("question='").append(question).append('\'');
        sb.append(", options=").append(options);
        sb.append(", answerId=").append(answerId);
        sb.append('}');
        return sb.toString();
    }
}
