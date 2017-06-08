package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import java.io.IOException;
import java.util.ArrayList;

public class Diploma extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diploma);

        ListView diplomaLV = (ListView) findViewById(R.id.diploma_lv_id);
        imageView = (ImageView) findViewById(R.id.diploma_picture);
        ArrayList<Ride> dpVisited = new ArrayList<>(Ride.getTestRides().values());
        DiplomaAdapter dpAdapter = new DiplomaAdapter(getApplicationContext(),dpVisited);
        diplomaLV.setAdapter(dpAdapter);

        if (Image.getImage() != null) {
            imageView.setImageBitmap(Image.getImage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.diploma_menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.diploma_menu_photo_id : {
                dispatchTakePictureIntent();
                return true;
            }
            case R.id.diploma_menu_save_id : {
                Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.diploma_menu_print_id: {
                Toast.makeText(getApplicationContext(), "Print", Toast.LENGTH_LONG).show();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Uri selectedimg = data.getData();
                Image.setImage(getCircularBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg)));
                imageView.setImageBitmap(Image.getImage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xffffffff;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        if (bitmap.getWidth() > bitmap.getHeight()) {
            float r = bitmap.getHeight() / 2;
            canvas.drawCircle(bitmap.getHeight()/2, bitmap.getHeight()/2, r, paint);
        } else {
            float r = bitmap.getWidth() / 2;
            canvas.drawCircle(bitmap.getWidth()/2, bitmap.getWidth()/2, r, paint);
        }


        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("Message", "kut jeffrey");
    }
}
