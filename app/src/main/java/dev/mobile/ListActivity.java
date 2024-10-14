package dev.mobile;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.adapter.StarAdapter;
import dev.mobile.beans.Star;
import dev.mobile.service.StarService;

public class ListActivity extends AppCompatActivity {

    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Initialisation de la Toolbar
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();

        // Configuration de la RecyclerView
        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d(TAG, "Stars in adapter: " + service.findAll().size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }
    public void init() {
        service.create(new Star("Deniz Can Aktas", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYHsLGGyvjDWy4bIX3tYYMmuUBH3s99XKDSQ&s", 3.5f));
        service.create(new Star("Tuba Buyukustun", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9oZOket5USbWrnhakGNmSgjxv2QN9LOAGPA&s", 3));
        service.create(new Star("Zayn Malik", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiq5RprCafJLF7LUgPOXJ39sXzwfZVqn1-TQ&s", 5));
        service.create(new Star("Bella Hadid", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9Mkk2KrKx9j43O2TztaazbWc5-xIvkeaaOQ&s", 1));
        service.create(new Star("Alperen Duymaz", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRC2y4V-Vkk9_VNzEVvSW3c47DgoER7XiF2Rw&s", 5));
        service.create(new Star("Melisa Asli Pamuk", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTaNUSyTpU2fsBHqehoTr29FmPhmJWSurG1Sw&s", 1));

        Log.d(TAG, "Stars created: " + service.findAll().size());
    }
}