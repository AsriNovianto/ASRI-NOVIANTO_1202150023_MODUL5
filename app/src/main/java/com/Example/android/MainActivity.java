package com.Example.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //deklarasi variabel yang akan digunakan
    private Database dtBase;
    private RecyclerView rcView;
    private Adapter adapter;
    private ArrayList<AddData> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // menamai aplikasi menjadi "to Do List App"
        setTitle("To Do List App");

        //menampilkan daftar list recycler yang ada pada database
        rcView = findViewById(R.id.rec_view);
        data_list = new ArrayList<>();
        dtBase = new Database(this);
        dtBase.readdata(data_list);

        //menginisialisasi shared preference
        SharedPreferences sharedP = this.getApplicationContext().getSharedPreferences("Preferences", 0);
        int color = sharedP.getInt("Colourground", R.color.white);

        //membuat adapter baru
        adapter = new Adapter(this,data_list, color);
        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.setAdapter(adapter);

        //menjalankan method hapus data pada list to do
        hapusToDo();
    }

  // inisiasi untuk menghapus list pada menu utama
    public void hapusToDo(){
        //membuat touch helper baru untuk recycler view
        ItemTouchHelper.SimpleCallback touchcall = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddData current = adapter.getData(position);
               // saat item yang dipilih di swipe ke arah kiri sampai pojok, maka item tersebut akan dihapus
                if(direction==ItemTouchHelper.LEFT){
                    if(dtBase.removedata(current.getTodo())){
                        adapter.deleteData(position);
                        Snackbar.make(findViewById(R.id.coordinator), "List Telah Berhasil diHapus", 2000).show();
                    }
                }
            }
        };
        //menentukan itemtouchhelper untuk recycler view
        ItemTouchHelper touchhelp = new ItemTouchHelper(touchcall);
        touchhelp.attachToRecyclerView(rcView);
    }

    //ketika menu pada activity di buat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_settings){
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    //method untuk menambahkan list baru yang telah ditambahkan
    public void addlist(View view) {
        //intent ke class add to do
        Intent intent = new Intent(MainActivity.this, AddToDo.class);
        //memulai intent
        startActivity(intent);
    }
}
