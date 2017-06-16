package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DiplomaActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    ImageView imageView = null;
    String name,surname;
    Uri selectedimg;
    ArrayList<PersonalActivity> personalActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diploma);

        if (Image.getImage() == null) {
            Image.setImage(getCircularBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.logo_essteling)));
        }

        name = getIntent().getExtras().getString("name");
        surname = getIntent().getExtras().getString("surname");

        personalActivities = (ArrayList<PersonalActivity>)getIntent().getSerializableExtra("personalActivities");
        ListView diplomaLV = (ListView) findViewById(R.id.diploma_lv_id);
        imageView = (ImageView) findViewById(R.id.diploma_picture);
        ArrayList<Ride> dpVisited = new ArrayList<>(Ride.getTestRides().values());
        DiplomaAdapter dpAdapter = new DiplomaAdapter(getApplicationContext(),dpVisited,personalActivities);
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
            case R.id.diploma_menu_print_id: {
                doPrint();
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
                selectedimg = data.getData();
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

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);

            Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_LONG).show();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.error_saved, Toast.LENGTH_LONG).show();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    private void doPrint() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        String jobName = this.getString(R.string.app_name) + " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(this,name,surname), null);

    }
}
