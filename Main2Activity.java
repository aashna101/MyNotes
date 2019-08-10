package com.example.aashna.mynotes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Main2Activity extends AppCompatActivity {
Button  imgBtn, showOpt;
EditText url, desc, msg,imageDesc;
LinearLayout msgL,urlL,imageL,chooseUploadL;
private TextView proText;
DatabaseReference ref, refPush;
private Button uploadMsgBtn, checkStatusBtn, urlBtn;
ImageView imageView;
Uri uri,downUri;
int turn=0;
private ProgressBar progress;
private LinearLayout layout;
private String loadstrt="Uploaded: ", loadEnd="...";
private Handler handler= new Handler();
int a=0;

    StorageReference storageReference;
final String urlType="URLTYPE", imageType="IMAGETYPE",msgType="MSGTYPE";//docType="doc", videoType="video"
    private static final int REQUEST_CODE = 1;
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       // getFile=findViewById(R.id.checkBtn);


        storageReference = FirebaseStorage.getInstance().getReference();
layout=findViewById(R.id.linearLayout);
uploadMsgBtn=findViewById(R.id.uploadMsg_btn);// msg btn
checkStatusBtn=findViewById(R.id.checkBtn); // check btn
urlBtn=findViewById(R.id.uploadUrl_btn);
//** progress bar syntax........................................
        progress = findViewById(R.id.progressBar);

        url=findViewById(R.id.URL_Txt);
        desc=findViewById(R.id.description_Txt);
        msg=findViewById(R.id.msgTxt);
        imageDesc=findViewById(R.id.ImageDesc_Txt);
        imageView=findViewById(R.id.image_view);
        imgBtn=findViewById(R.id.uploadImage_btn);//image button
        proText=findViewById(R.id.progressTxt);

        //upload =findViewById(R.id.uploadMsg_btn);
       // layouts
        msgL=findViewById(R.id.msgLayout);
        urlL=findViewById(R.id.urlLayout);
        imageL=findViewById(R.id.imageLayout);
        showOpt=findViewById(R.id.showOptionsBtn);
        chooseUploadL=findViewById(R.id.chooseUploadLayout);
        //end of layouts declaration
        //layouts visibility
       msgL.setVisibility(View.VISIBLE);
       urlL.setVisibility(View.GONE);
       imageL.setVisibility(View.GONE);
       chooseUploadL.setVisibility(View.GONE);
       proText.setVisibility(View.GONE);
       progress.setVisibility(View.GONE);
       // end of visibility
        ref= FirebaseDatabase.getInstance().getReference().child("document");

        checkStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, RecyclerData.class));

            }
        });

checkStatusBtn.setOnTouchListener(new View.OnTouchListener() {

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View v, MotionEvent event) { //check button animation
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                v.setBackgroundColor(getResources().getColor(R.color.backgroundBtn));
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                v.setBackgroundResource(R.drawable.circle_background);
                v.invalidate();
                break;
            }
        }
        return false;
    }
});
imgBtn.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) { //img button animation
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                view.setBackgroundColor(getResources().getColor(R.color.backgroundBtn));
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                view.setBackgroundResource(R.drawable.circle_background);
                view.invalidate();
                break; }
        }
        return false;
    }
});

        showOpt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (turn == 0){

                chooseUploadL.setVisibility(View.VISIBLE);
                msgL.setVisibility(View.GONE);
                urlL.setVisibility(View.GONE);
                imageL.setVisibility(View.GONE);
                turn=1;}
                else{chooseUploadL.setVisibility(View.GONE);
                turn=0;}
            }
        });
        showOpt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.setBackgroundColor(getResources().getColor(R.color.backgroundBtn));
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        view.setBackgroundColor(Color.TRANSPARENT);
                        view.invalidate();
                        break; }
                }
                return false;
            }
        });
uploadMsgBtn.setOnTouchListener(new View.OnTouchListener() { //msg button animation
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                view.setBackgroundColor(getResources().getColor(R.color.backgroundBtn));
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                view.setBackgroundResource(R.drawable.circle_background);
                view.invalidate();
                break; }
        }
        return false;
    }
});

urlBtn.setOnTouchListener(new View.OnTouchListener() { //url button animation
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                view.setBackgroundColor(getResources().getColor(R.color.backgroundBtn));
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                view.setBackgroundResource(R.drawable.circle_background);
                view.invalidate();
                break; }
        }
        return false;
    }
});
        }//end of onCreate()
//#b4a59c
    public void UploadUrlBtn(View view) {

        progress.setVisibility(View.VISIBLE);

        String urlStr=url.getText().toString();
        String  descStr=desc.getText().toString();
        if(TextUtils.isEmpty(urlStr)){
            Toast.makeText(this, "URL must not be empty", Toast.LENGTH_SHORT).show();
        }else {
            refPush =ref.push();
            refPush.child("url").setValue(urlStr);
            refPush.child("description").setValue(descStr);
            refPush.child("type").setValue(urlType).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progress.setVisibility(View.GONE);
                        Toast.makeText(Main2Activity.this, "Uploaded Successfully",Toast.LENGTH_SHORT).show();
                        url.setText(null);
                        desc.setText(null);
                        refPush =null;
                    }else {Toast.makeText(Main2Activity.this, "Something went wrong",Toast.LENGTH_SHORT).show();}
                }
            });

        } //end of else block
       } // end of button method block

    public void urlBtn(View view) {
        chooseUploadL.setVisibility(View.GONE);
        urlL.setVisibility(View.VISIBLE);
        imageL.setVisibility(View.GONE);
        msgL.setVisibility(View.GONE);
        turn=0;
    }

    public void imageBtn(View view) {
        chooseUploadL.setVisibility(View.GONE);
        imageL.setVisibility(View.VISIBLE);
        urlL.setVisibility(View.GONE);
        msgL.setVisibility(View.GONE);
        turn=0;
    }

    public void UploadMSgBtn(View view) {
        String message=msg.getText().toString().trim();
        if(! message.isEmpty()){
            refPush =ref.push();
            refPush.child("url").setValue("null");
            refPush.child("description").setValue(message);
            refPush.child("type").setValue(msgType).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        msg.setText("");
                        refPush=null;
                        Toast.makeText(Main2Activity.this, "Message uploaded successfully", Toast.LENGTH_SHORT).show();
                                            }
                    else {Toast.makeText(Main2Activity.this, "Something went wrong",Toast.LENGTH_SHORT).show();}
                }// end of onComplete()
            });
        }//end of if(empty)

    }//end of uploadMsgBtn()

    public void UploadImageBtn(View view) {// upload a image first


if (uri != null){
    progress.setVisibility(View.VISIBLE);
    proText.setVisibility(View.VISIBLE);
    imgBtn.setEnabled(false);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//for restricting user interaction

       // Uri cmpressUri=compressImage();
    final StorageReference refStorage = storageReference.child("images/"+uri.getLastPathSegment());
   UploadTask uploadTask = refStorage.putFile(uri);

    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
        @Override
        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
            if (!task.isSuccessful()) {
                throw new Exception("task is not succeeded");
            } return refStorage.getDownloadUrl();
        }//end of throws Exception method
    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
            if (task.isSuccessful()) {
                downUri = task.getResult();



                String imgD=imageDesc.getText().toString().trim();

                refPush =ref.push();
                refPush.child("url").setValue(downUri.toString());
                refPush.child("description").setValue(imgD);
                refPush.child("type").setValue(imageType).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {//updating values on database
                        if (task.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(Main2Activity.this, "Post uploaded", Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                            imageDesc.setText("");
                            GlideApp.with(Main2Activity.this)
                                    .load(android.R.drawable.ic_input_add)
                                    .into(imageView);
                            uri = null;
                            refPush = null;

                        }
                       // progress.setVisibility(View.GONE);
                        imgBtn.setEnabled(true);

                    }//end of onComplete()for updating values at database
                });//end of addOnCompleteListener() for updating values at database

             Log.d("OnComplete()", "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+task.getResult());
            } else {
                // Handle failures
                // ...
                Log.d("<<<<<<<<", "<<<<<<<<<<<<<<<<<<<< not uplooaded ");
            }
        }//end of onComplete()for getting download uri
    });
            uri=null;

uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        proText.setVisibility(View.VISIBLE);
        double prog = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

            a = (int) (Math.round(prog)); //parsing float to the integer
        if(a<100) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5);
                    }catch(InterruptedException e){
                        e.printStackTrace(); }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progress.setProgress(a);
                                progress.setSecondaryProgress(a+5);
                                proText.setText(loadstrt+a+ loadEnd); }
                        });//end of handler runnable
                }//end of new Thread->run()

            }).start();

        }
        else if(a==100){
            progress.setVisibility(View.GONE);
            proText.setText(null);
            proText.setVisibility(View.GONE);
        Log.d("See %=", "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+prog);}
    }//end of onProgress()
});
    imgBtn.setEnabled(true);
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//for allowing back user interaction

}//end of if(uri != null)
        else {
           Toast.makeText(this, "not getting image path", Toast.LENGTH_SHORT).show(); }
      //  progress.setVisibility(View.GONE);
}//end of method()




    public void getImageBtn(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null){
            uri=data.getData();
            GlideApp.with(Main2Activity.this)
                    .load(uri)
                    .into(imageView);

        }
    }

    public void msgBtn(View view) {
        chooseUploadL.setVisibility(View.GONE);
        msgL.setVisibility(View.VISIBLE);
        imageL.setVisibility(View.GONE);
        urlL.setVisibility(View.GONE);
        turn=0;

    }


} // end of activity
