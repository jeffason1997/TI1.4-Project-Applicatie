package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;

import com.b2.projectgroep.ti14_applicatie.DiplomaClasses.AchievementClasses.Achievement;
import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Jeffrey on 12-6-2017.
 */

public class MyPrintDocumentAdapter extends PrintDocumentAdapter {

    Context context;
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    public int totalpages = 2;
    String name,surname;
    ArrayList<PersonalActivity> personalActivities;

    public MyPrintDocumentAdapter(Context context, String name, String surname, ArrayList<PersonalActivity> personalActivities) {
        this.context = context;
        this.name = name;
        this.surname = surname;
        this.personalActivities = personalActivities;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        myPdfDocument = new PrintedPdfDocument(context, newAttributes);
        pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
        pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        if (totalpages > 0) {
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_output.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Page count is zero.");
        }


    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        for (int i = 0; i < totalpages; i++) {
            if (pageInRange(pageRanges, i)) {
                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create();

                PdfDocument.Page page =
                        myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                drawPage(page, i);
                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }

        callback.onWriteFinished(pageRanges);
    }

    private boolean pageInRange(PageRange[] pageRanges, int page) {
        for (int i = 0; i < pageRanges.length; i++) {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }


    private void drawPage(PdfDocument.Page page, int pagenumber) {
        int titleBaseLine = 72;
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        paint.setTextAlign(Paint.Align.LEFT);

        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.rgb(0, 128, 0));
        pagenumber++;
        PdfDocument.PageInfo pageInfo = page.getInfo();

        if(page.getInfo().getPageNumber() == 0) {

            canvas.drawText(
                    context.getResources().getString(R.string.grats) + " " + name + " ",
                    canvas.getWidth() / 10,
                    titleBaseLine,
                    paint);

            paint.setTextSize(14);
            canvas.drawText("" + context.getResources().getString(R.string.diploma_tekst), canvas.getWidth() / 10, titleBaseLine + 25, paint);

            Bitmap image = Bitmap.createScaledBitmap(Image.getImage(), pageInfo.getPageWidth() / 6, pageInfo.getPageWidth() / 6, true);
            canvas.drawBitmap(image, pageInfo.getPageWidth() - (int) (pageInfo.getPageWidth() / 5), pageInfo.getPageHeight() / 40, paint);

            paint.setTextSize(22);
            canvas.drawText(context.getResources().getString(R.string.diploma_activity_text), pageInfo.getPageWidth()/10, titleBaseLine + 150, paint);
            canvas.drawRect(0, titleBaseLine + 155, pageInfo.getPageWidth(), titleBaseLine + 160, greenPaint);
            canvas.drawRect(0, titleBaseLine + 155, pageInfo.getPageWidth()/10 - 10, pageInfo.getPageHeight(), greenPaint);

            paint.setTextSize(14);
            int startHeightRides = pageInfo.getPageHeight() / 8;
            ArrayList<Ride> allRides = new ArrayList<>(Ride.getTestRides().values());
            int amountNotNull = 0;
            for (int x = 0; x < allRides.size(); x++) {
                int amountThisRide = getAmount(allRides.get(x).getName(), personalActivities);
                if (amountThisRide > 0) {
                    amountNotNull++;
                    Bitmap imagePa = BitmapFactory.decodeResource(context.getResources(), allRides.get(x).getRideImage());
                    imagePa = Bitmap.createScaledBitmap(imagePa, 60, 60, false);
                    canvas.drawBitmap(imagePa, pageInfo.getPageWidth() / 10, titleBaseLine + startHeightRides + (amountNotNull * 70), paint);
                    canvas.drawText(context.getResources().getString(allRides.get(x).getName()), pageInfo.getPageWidth() / 10 + 100, titleBaseLine + startHeightRides + (amountNotNull * 70) + 30, paint);
                    String totalTimes = context.getResources().getString(R.string.diploma_visitedText1) + " " + context.getResources().getString(allRides.get(x).getName()) + " " + amountThisRide + " " + context.getResources().getString(R.string.diploma_visitedText2);
                    canvas.drawText(totalTimes, pageInfo.getPageWidth() / 10 + 200, titleBaseLine + startHeightRides + (amountNotNull * 70) + 30, paint);
                }
            }
        } else if(page.getInfo().getPageNumber() == 1) {
            paint.setTextSize(25);
            canvas.drawText(
            context.getResources().getString(R.string.grats) + " " + name + " " , canvas.getWidth() / 10, titleBaseLine, paint);

            paint.setTextSize(14);
            canvas.drawText("" + context.getResources().getString(R.string.diploma_tekst), canvas.getWidth() / 10, titleBaseLine + 25, paint);

            paint.setTextSize(22);
            canvas.drawText(context.getResources().getString(R.string.diploma_achievement_text), pageInfo.getPageWidth()/10, titleBaseLine + 80, paint);
            canvas.drawRect(0, titleBaseLine + 85, pageInfo.getPageWidth(), titleBaseLine + 90, greenPaint);
            canvas.drawRect(0, titleBaseLine + 85, pageInfo.getPageWidth()/10 - 10, pageInfo.getPageHeight(), greenPaint);

            Bitmap image = Bitmap.createScaledBitmap(Image.getImage(), pageInfo.getPageWidth() / 6, pageInfo.getPageWidth() / 6, true);
            canvas.drawBitmap(image, pageInfo.getPageWidth() - (int) (pageInfo.getPageWidth() / 5), pageInfo.getPageHeight() / 40, paint);

            paint.setTextSize(14);
            int startHeightAchievement = titleBaseLine + page.getInfo().getPageHeight() / 30;
            ArrayList<Achievement> achievementsRecieved = Achievement.getCompletedAchievements(personalActivities);
            for (int x = 0; x < achievementsRecieved.size(); x++) {
                Bitmap imagePa = BitmapFactory.decodeResource(context.getResources(), achievementsRecieved.get(x).getImage());
                imagePa = Bitmap.createScaledBitmap(imagePa, 60, 60, false);
                canvas.drawBitmap(imagePa, page.getInfo().getPageWidth() / 10, titleBaseLine + startHeightAchievement + (x * 70), paint);
                canvas.drawText(context.getResources().getString(achievementsRecieved.get(x).getTitle()), page.getInfo().getPageWidth() / 10 + 100, titleBaseLine + startHeightAchievement + (x * 70) + 30, paint);
            }
        }
    }

    public int getAmount(int name, ArrayList<PersonalActivity> activities){
        int counter = 0;
        for(int i=0;i<activities.size();i++){
            if(activities.get(i).getRide().getName()==(name)){
                counter++;
            }
        }
        return counter;
    }
}