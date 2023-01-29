package com.example.exm;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {
    Bundle arg;
    Model model;
    EditText title, cost, stockAvailability, availabilityInTheStore, Description, Rewiews;

    String varcharImage;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        cost = findViewById(R.id.Cost);
        stockAvailability = findViewById(R.id.StockAvailability);
        availabilityInTheStore = findViewById(R.id.AvailabilityInTheStore);
        Description = findViewById(R.id.Description);
        Rewiews = findViewById(R.id.Rewiews);

        arg = getIntent().getExtras();
        model = arg.getParcelable("key");
        image im = new image(this);
        image.setImageBitmap(im.getUserImage(model.getImage()));
        title.setText(model.getTitle());
        cost.setText(Integer.toString(model.getCost()));
        stockAvailability.setText(Integer.toString(model.getStockAvailability()));
        availabilityInTheStore.setText(Integer.toString(model.getAvailabilityInTheStore()));
        Description.setText(model.getDescription());
        Rewiews.setText(model.getRewiews());
        if(model.getImage()!=null){
            varcharImage = model.getImage();
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Bitmap bitmap = null;
            if(result.getResultCode() == Activity.RESULT_OK)
            {
                Uri selectedImage = result.getData().getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                }
                catch (Exception ex)
                {
                }
                image.setImageBitmap(null);
                image.setImageBitmap(bitmap);
                varcharImage = BitmapToString(bitmap);
            }
        }
    });

    public String BitmapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, boas);
        byte[] b = boas.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void updImage(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        someActivityResultLauncher.launch(intent);
    }

    public void saveChanges(View view) {
        Put();
        startActivity(new Intent(this, MainWindow.class));
    }

    void Put(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/%D0%AD%D0%BA%D0%B7%D0%B0%D0%BC%D0%B5%D0%BD/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Model modal = new Model(model.getID(), title.getText().toString(), Integer.valueOf(cost.getText().toString()), Integer.valueOf(stockAvailability.getText().toString()), Integer.valueOf(availabilityInTheStore.getText().toString()), Description.getText().toString(), Rewiews.getText().toString(), varcharImage);
        Call<Model> call = retrofitAPI.putData(model.getID(), modal);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(UpdateActivity.this, "При изменении возникла ошибка", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UpdateActivity.this, "Данные успешно сохранились в базу данных", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "При изменении возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(this, MainWindow.class));
    }
}