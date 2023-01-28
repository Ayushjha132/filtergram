package com.example.filtergram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;

import java.io.FileDescriptor;
import java.io.IOException;

import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    // used to load images (declared in android)
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
    }

    public void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        //to select any type of image
        intent.setType("image/*");
        startActivityForResult(intent, 1);

    }

    public void apply(Transformation<Bitmap> filter){
        Glide
                .with(this)
                .load(image)
                .apply(RequestOptions.bitmapTransform(filter))
                .into(imageView);
    }

    //Sepia filter
    public void applySepia(){
        apply(new SepiaFilterTransformation());
    }

    //Toon filter
    public void applyToon(){
        apply(new ToonFilterTransformation());
    }

    //Sketch filer
    public void applySketch(){
        apply(new SketchFilterTransformation());
    }

    //
    public void applyContrast(){
        apply(new ContrastFilterTransformation());
    }

    //pixelation filter
    public void  applyPixelation(){
        apply(new PixelationFilterTransformation());
    }

    //kuwahara filter
    public void applyKuwahara(){
        apply(new KuwaharaFilterTransformation());
    }

    //vignette filter
    public void applyVignette(){
        apply(new VignetteFilterTransformation());
    }


    // handle image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && data != null){
            try {
                Uri uri = data.getData();
                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                imageView.setImageBitmap(image);
            }catch(IOException e){
                Log.e("cs50", "Image not found", e);
            }
        }
    }
}