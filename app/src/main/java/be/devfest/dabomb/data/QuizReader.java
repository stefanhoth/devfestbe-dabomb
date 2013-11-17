package be.devfest.dabomb.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import be.devfest.dabomb.R;
import be.devfest.dabomb.entities.Question;

/**
 * TODO describe class
 *
 * @author Stefan Hoth <sh@jnamic.com>
 *         date: 17.11.13 16:24
 * @since TODO add version
 */
public abstract class QuizReader {

    private static final String TAG = QuizReader.class.getName();

    public static List<Question> load(Context ctx) {
        Type listType = new TypeToken<List<Question>>() {
        }.getType();
        Gson gson = new Gson();

        List<Question> questions = null;
        try {
            InputStream inputStream = ctx.getResources().openRawResource(R.raw.quiz);
            String jsonContent = readTextFile(inputStream);

            questions = gson.fromJson(jsonContent, listType);

        } catch (IllegalStateException e) {
            Log.e(TAG, "Could not parse JSON ", e);
            return null;
        } catch (NullPointerException e) {
            Log.e(TAG, "Error while handling stream", e);
            return null;
        } catch (IOException e) {

        }

        return questions;
    }

    private static String readTextFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.close();
        inputStream.close();

        return outputStream.toString();
    }


}
