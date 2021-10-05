package me.astero.mlb_redo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import me.astero.mlb_redo.R;
import me.astero.mlb_redo.Singleton;
import me.astero.mlb_redo.adapter.BooksAdapter;
import me.astero.mlb_redo.database.Database;
import me.astero.mlb_redo.database.LibraryDatabase;
import me.astero.mlb_redo.database.data.BookData;
import me.astero.mlb_redo.database.data.LibraryData;
import me.astero.mlb_redo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private GoogleMap mMap;

    private Spinner librariesSpinner, sortSpinner;

    private BooksAdapter booksAdapter;

    private RecyclerView booksRecycler;

    private boolean run = false;

    private SearchView search;

    private List<BookData> books = new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        librariesSpinner = binding.librariesSpinner;
        sortSpinner = binding.sortSpinner;

        search = binding.searchView;

        System.out.println("test");








        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
                Singleton.getInstance().map = googleMap;

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                Singleton.getInstance().map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                Singleton.getInstance().map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                System.out.println("MAP DONE");
            }
        });

            getBooks();


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                System.out.println("TEST SEARCH");
                booksAdapter = new BooksAdapter(searchForBooks(newText));
                booksRecycler = binding.booksRecycler;

                booksRecycler.setAdapter(booksAdapter);
                booksRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                return false;
            }
        });



        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {




                try {
                    getLibraries();


                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item,

                            LibraryDatabase.getInstance(getContext()).libraryDataList);
                    librariesSpinner.setAdapter(arrayAdapter);

                    librariesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            if(!run)
                            {
                                run = true;
                                return;
                            }
                            getBooks(id + 1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    String[] sort = {"Sort: None", "Sort: A-Z", "Sort: Z-A"};

                    ArrayAdapter arrayAdapter2 = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item,

                            sort);
                    sortSpinner.setAdapter(arrayAdapter2);

                    sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            List newList = new LinkedList<>(books);

                            if(id != 0)
                            {


                                Collections.sort(newList, new Comparator<BookData>() {
                                    @Override
                                    public int compare(BookData o1, BookData o2) {
                                        return o1.name.compareTo(o2.name);
                                    }
                                });

                                if(id == 2)
                                {
                                    Collections.reverse(newList);
                                }



                            }

                            booksAdapter = new BooksAdapter(newList);
                            booksRecycler = binding.booksRecycler;

                            booksRecycler.setAdapter(booksAdapter);
                            booksRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        return root;
    }


    private void getLibraries() throws IOException, JSONException {
        LibraryDatabase.getInstance(getContext());
    }

    private List<BookData> searchForBooks(String title)
    {

        List<BookData> tempBookData = new LinkedList<>();

        for(BookData data : books)
        {
            if(data.name.toLowerCase().contains(title.toLowerCase()))
            {
                tempBookData.add(data);
            }

        }

        return tempBookData;
    }


    private void getBooks(long id)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {




                    JSONArray array = new JSONArray(Database.get("available/lib/" + id));



                    books = new LinkedList<>();


                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = new JSONObject(array.get(i).toString());


                        BookData data = new BookData();
                        data.author = object.getString("author");
                        data.name = object.getString("title");
                        data.imageUrl = object.getString("imageLink");

                        books.add(data);

                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            booksAdapter = new BooksAdapter(books);
                            booksRecycler = binding.booksRecycler;

                            booksRecycler.setAdapter(booksAdapter);
                            booksRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                    });


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getBooks()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {




                    JSONArray array = new JSONArray(Database.get("available/lib/" + 1));



                    books = new LinkedList<>();


                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = new JSONObject(array.get(i).toString());


                        BookData data = new BookData();
                        data.author = object.getString("author");
                        data.name = object.getString("title");
                        data.imageUrl = object.getString("imageLink");

                        books.add(data);

                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            booksAdapter = new BooksAdapter(books);
                            booksRecycler = binding.booksRecycler;

                            booksRecycler.setAdapter(booksAdapter);
                            booksRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

                        }
                    });


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}