package ua.kpi.comsys.IO8323;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BookInfoActivity extends AppCompatActivity {

    ImageView imageView;
    TextView title;
    TextView subtitle;
    TextView desc;
    TextView authors;
    TextView publisher;
    TextView isbn13;
    TextView pages;
    TextView year;
    TextView rating;
    TextView price;
    ProgressBar progressBar;
    Drawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        String id = getIntent().getStringExtra("id");

        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.booktitle);
        subtitle = findViewById(R.id.subtitle);
        desc = findViewById(R.id.description);
        authors = findViewById(R.id.authors);
        publisher = findViewById(R.id.publisher);
        isbn13 = findViewById(R.id.isbn13);
        pages = findViewById(R.id.pages);
        year = findViewById(R.id.year);
        rating = findViewById(R.id.rating);
        price = findViewById(R.id.price);
        
        progressBar = findViewById(R.id.progressBar);

        new JsonTask().execute(id);
    }

    private class JsonTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://api.itbook.store/1.0/books/" + params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line+"\n");
                }
                connection.disconnect();
                JSONObject json = new JSONObject(buffer.toString());
                String urlImageString = json.getString("image");
                URL urlImage = new URL(urlImageString);
                connection = (HttpURLConnection) urlImage.openConnection();
                connection.connect();
                drawable = Drawable.createFromStream(connection.getInputStream(), null);
                connection.disconnect();
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
                try {
                    if (reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject jsonBookLoadedObject = new JSONObject(result);
                Book book = new Book(
                        jsonBookLoadedObject.getString("title"),
                        jsonBookLoadedObject.getString("subtitle"),
                        jsonBookLoadedObject.getString("authors"),
                        jsonBookLoadedObject.getString("publisher"),
                        jsonBookLoadedObject.getString("isbn13"),
                        jsonBookLoadedObject.getInt("pages"),
                        jsonBookLoadedObject.getInt("year"),
                        jsonBookLoadedObject.getInt("rating"),
                        jsonBookLoadedObject.getString("desc"),
                        jsonBookLoadedObject.getString("price"),
                        jsonBookLoadedObject.getString("image")
                );
                title.setText("Title: " + book.getTitle());
                subtitle.setText("Subtitle: " + book.getSubtitle());
                authors.setText("Authors: " + book.getAuthors());
                publisher.setText("Publisher: " + book.getPublisher());
                isbn13.setText("ISBN-13: " + book.getIsbn13());
                pages.setText("Pages: " + book.getPages());
                year.setText("Year: " + book.getYear());
                rating.setText("Rating: " + book.getRating() + "/5");
                desc.setText("Description: " + book.getDesc());
                price.setText("Price: " + book.getPrice());
                imageView.setImageDrawable(drawable);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(BookInfoActivity.this, ListActivity.class);
        startActivity(intent);
    }
}