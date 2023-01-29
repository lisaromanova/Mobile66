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

public class AddActivity extends AppCompatActivity {
    EditText title, cost, stockAvailability, availabilityInTheStore, Description, Rewiews;

    String varcharImage;
    ImageView image;
    TextView deleteImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        image = findViewById(R.id.image);
        deleteImage = findViewById(R.id.deleteImage);
        title = findViewById(R.id.title);
        cost = findViewById(R.id.Cost);
        stockAvailability = findViewById(R.id.StockAvailability);
        availabilityInTheStore = findViewById(R.id.AvailabilityInTheStore);
        Description = findViewById(R.id.Description);
        Rewiews = findViewById(R.id.Rewiews);
        varcharImage = "";
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
                deleteImage.setVisibility(View.VISIBLE);
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

    public void deleteImage(View view)
    {
        varcharImage = "";
        deleteImage.setVisibility(View.INVISIBLE);
        image.setImageBitmap(null);
        image.setImageResource(R.drawable.absence);

    }

    public void back(View v)
    {
        startActivity(new Intent(this, MainWindow.class));
    }

    public void add(View v)
    {
        postData();
        startActivity(new Intent(this, MainWindow.class));
    }

    public  void postData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/%D0%AD%D0%BA%D0%B7%D0%B0%D0%BC%D0%B5%D0%BD/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Model modal = new Model(0, title.getText().toString(), Integer.valueOf(cost.getText().toString()), Integer.valueOf(stockAvailability.getText().toString()), Integer.valueOf(availabilityInTheStore.getText().toString()), Description.getText().toString(), Rewiews.getText().toString(), varcharImage);
        Call<Model> call = retrofitAPI.postData(modal);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(AddActivity.this, "При добавление возникла ошибка", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddActivity.this, "Данные успешно добавились в базу данных", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(AddActivity.this, "При добавление возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }

}