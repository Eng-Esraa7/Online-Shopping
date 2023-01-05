package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class ProductsActivity extends AppCompatActivity {

    private RecyclerView RecyclerView;
   public static List<product> productList;
    public static  adapter adapter1;
    ProductHelper productHelper;
    //text
  public static SearchView searchView;
    //voice,camera
      ImageButton voiceButton,CameraButton;
    int voiceCode = 1;
    ProductHelper myHelper;
    ImageButton back;
    ArrayList< String> productfilter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.home: Intent i = new Intent(ProductsActivity.this, CategoriesActivity.class);
                i.putExtra("Name", getIntent().getExtras().getString("Name") );
                i.putExtra("userid",getIntent().getExtras().getString("userid"));
                startActivity(i);
                finish();
                return true;
            case R.id.Cart: Intent ii = new Intent(ProductsActivity.this, CartActivity.class);
                ii.putExtra("userid",getIntent().getExtras().getString("userid"));//sent user id
                startActivity(ii);
                return true;
            case R.id.logout: Intent i2 = new Intent(ProductsActivity.this, Login.class);
                startActivity(i2);
                finish();
                return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        int id = getIntent().getIntExtra("id",1);
        String userid=getIntent().getExtras().getString("userid");
        back = (ImageButton)findViewById(R.id.Back_category);
        productList = new ArrayList<>();
//database pf product
        productHelper = new ProductHelper(getApplicationContext());
//recycle view
        RecyclerView = findViewById(R.id.resycleView1);
        if(productHelper.getAllProduct().size()==0) {//initial data when install
           // Toast.makeText(this, "0000000", Toast.LENGTH_SHORT).show();
            data();
            storeProduct();
        }
        productList=productHelper.getAllProducOfCategorytHasQuantity(String.valueOf(id));
         productfilter=new ArrayList<>();
//adapter
        adapter1 = new adapter(this,userid);

        //productList=adapter1.setList(productList);
        //RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.setAdapter(adapter1);

//        adapter1.setList(productList);
//        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        RecyclerView.setAdapter(adapter1);
        productList=adapter1.setList(productList);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter1);

        //textpart
        searchView = (SearchView) findViewById(R.id.searchtext);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProduct(newText);
                return true;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ProductsActivity.this,CategoriesActivity.class);
                startActivity(i);
            }
        });
        //voicepart
        voiceButton = (ImageButton) findViewById(R.id.voiceButton);
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openvoiceButton();
            }
        });

        //camerapart
        CameraButton =  findViewById(R.id.CameraButton);
        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ProductsActivity.this, "toast", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ProductsActivity.this, ScannerActivity2.class);
                startActivity(i);
            }
        });

    }



    private void openvoiceButton() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, voiceCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == voiceCode && resultCode == RESULT_OK) {
            //    ArrayList<product> arrayList = new ArrayList<>();
            productfilter = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            filterProduct(productfilter.get(0));

        } //else {
          //  Toast.makeText(this, "No Matched Product", Toast.LENGTH_SHORT).show();
      //  }

    }
    public void data(){
        Bitmap[] images ={
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.j1),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.pn9),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.pockets),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.d1),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.jg),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.a2),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.d4),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.d5),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.d6),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.b1),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bch1),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bch2),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bch3),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.pb1),
        };
        String [] name={
                "Black Jacket ",
                "janice ",
                "Pockets",
                "Black Dress",
                "white Jacket",
                "Hoodie ",
                "Black Dress ",
                "White Dress",
                "pink Dress",
                "boys pajamas",
                "spyderman ",
                "Baby Boys Set",
                "boys pajamas",
                "white Trouser  ",
        };

        String [] price={
                "1750",
                "1500",
                "300",
                "545",
                "500",
                "700",
                "370",
                "900",
                "700",
                "1750",
                "1750",
                "1500",
                "300",
                "545",
        };
        int [] catId={
                1,1,1,1,1,1,1,1,1,2,2,2,2,4,
        };
        String [] quantity={
                "10",
                "10",
                "35",
                "10",
                "10",
                "35",
                "20",
                "50",
                "60",
                "10",
                "40",
                "20",
                "40",
                "20",
        };
        String [] desc={
                "Color: black,white,Grey" +
                        "\n"+" Size: S,M,L",
                "Color: black,white,Grey,beige" +
                        "\n"+"Size: one size",
                "Color: black,red,Grey" +
                        "\n"+"Size: S,M,L,XL",
                "Color: black,white,Grey" +
                        "\n"+ "Size: S,M,L",
                "Color: black,white,Grey" +
                        "\n"+  "Size: S,M,L",
                "Color: Blue,black,white,Grey" +
                        "\n"+  "Size: S,M,L",
                "Color: black,white,Grey,red" +
                        "\n"+   "Size: S,M,L",
                "Color:  pink,black,white,Grey" +
                        "\n"+ "Size: S,M,L",
                "Color: black,white,Grey" +
                        "\n"+ "Size: one size",
                "Color: black,white,Grey,red" +
                        "\n"+"Size: oversize",
                "Color: black,white,Grey" +
                        "\n"+"Size: S,M,L",
                "Color: black,white,Grey,beige" +
                        "\n"+"Size: one size",
                "Color: black,red,Grey" +
                        "\n"+"Size: S,M,L,XL",
                "Color: black,white,Grey,pink" +
                        "\n"+"Size: S,M,L",
        };
        String [] countSale={
                "40","30","100","90","30","40","30","100","90","30","40","30","100","90",

        };
        for(int i=0;i<14;i++){
            productList.add(new product(name[i],price[i],quantity[i],images[i],catId[i],desc[i],countSale[i]));
        }

    }

    public void storeProduct(){
        try {
            for(int i=0;i<14;i++){
                productHelper.CreateData(productList.get(i));
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void filterProduct(String txt) {
        ArrayList<product> product1 = new ArrayList<>();
        for (product i : productList) {
            if (i.getName().toLowerCase().contains(txt)) {
                product1.add(i);
            }
        }
        if(product1.isEmpty())
        {
           // Toast.makeText(ProductsActivity.this, "Not Not Not Found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            adapter1.setfilterproducts(product1);
        }
    }
    }

