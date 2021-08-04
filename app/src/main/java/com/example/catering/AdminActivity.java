package com.example.catering;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

public class AdminActivity extends AppCompatActivity {
    private String categoriName, Description, Price, Pname, saveCurrentData, saveCurrentTime, nasi1,nasi2,lauk1, lauk2,lauk2_1,lauk2_2;
    private Button addNewProductbtn;
    private EditText InputProductname, InputproductDescrip, InputProductPrice;
    private EditText InputProductNasi1,InputProductNasi2, InputProductlauk1, InputProductlauk2, InputProductlauk2_1, InputProductlauk2_2;//add1
    private ImageView InputProductImg;
    private static final int GalerryPick = 1;
    private Uri ImageUrl;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        categoriName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef=FirebaseDatabase.getInstance().getReference().child("Products");

        addNewProductbtn = (Button) findViewById(R.id.add_new_product);
        InputProductImg = (ImageView) findViewById(R.id.select_product_image);
        InputProductname = (EditText) findViewById(R.id.product_name);
        InputProductNasi1 = (EditText) findViewById(R.id.product_nasi1);
        InputProductNasi2 = (EditText) findViewById(R.id.product_nasi2);
        InputProductlauk1 = (EditText) findViewById(R.id.product_lauk1_1);
        InputProductlauk2 = (EditText) findViewById(R.id.product_lauk1_2);
        InputProductlauk2_1 = (EditText) findViewById(R.id.product_lauk2_1);
        InputProductlauk2_2 = (EditText) findViewById(R.id.product_lauk2_2);
        InputproductDescrip = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        loadingBar=new ProgressDialog(this);

        InputProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGalery();
            }

        });

        addNewProductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduceData();
            }
        });
    }

    private void OpenGalery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/");
        startActivityForResult(galleryIntent, GalerryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalerryPick && resultCode == RESULT_OK && data != null) {
            ImageUrl = data.getData();
            InputProductImg.setImageURI(ImageUrl);
        }
    }

    private void ValidateProduceData() {
        Description = InputproductDescrip.getText().toString();
        Price = InputProductPrice.getText().toString();
        Pname = InputProductname.getText().toString();
        nasi1 = InputProductNasi1.getText().toString();
        nasi1 = InputProductNasi2.getText().toString();
        lauk1 = InputProductlauk1.getText().toString();
        lauk2 = InputProductlauk2.getText().toString();
        lauk2_1 = InputProductlauk2_1.getText().toString();
        lauk2_2 = InputProductlauk2_2.getText().toString();


        if (ImageUrl == null) {
            Toast.makeText(this, "Product image is mandatory....", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Please write description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please add price", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Please write product name", Toast.LENGTH_SHORT).show();
        } else {
            StoreImageInformation();
        }
    }

    private void StoreImageInformation() {

        loadingBar.setTitle("Adding new product");
        loadingBar.setMessage("Please wait, add new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentData = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calender.getTime());

        productRandomKey = saveCurrentData = saveCurrentTime;

        StorageReference filepath = ProductImageRef.child(ImageUrl.getLastPathSegment()+ productRandomKey+".jpg");

        final UploadTask uploadTask = filepath.putFile(ImageUrl);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                String message = e.toString();
                Toast.makeText(AdminActivity.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminActivity.this, "Image Upload sukses", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull  Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return  filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull  Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminActivity.this, "Produtct image save berhasil", Toast.LENGTH_SHORT).show();
                            
                            saveProductInfoDatabase();
                        }
                    }
                });
            }
        });

    }

    private void saveProductInfoDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentData);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", categoriName);
        productMap.put("price", Price);
        productMap.put("pname", Pname);
        productMap.put("nasi1", nasi1);
        productMap.put("nasi2", nasi2);
        productMap.put("lauk1", lauk1);
        productMap.put("lauk2", lauk2);
        productMap.put("lauk2_1", lauk2_1);
        productMap.put("lauk2_2", lauk2_2);


        ProductRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Intent intent= new Intent(AdminActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminActivity.this, "add product berhasil...", Toast.LENGTH_SHORT).show();
                        }else{
                            loadingBar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(AdminActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
