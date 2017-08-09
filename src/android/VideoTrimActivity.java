package org.apache.cordova.trim;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.zensar.zenaskapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnK4LVideoListener;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

/**
 * Created by AP47178 on 27-07-2017.
 */


public class VideoTrimActivity extends Activity implements OnTrimVideoListener, OnK4LVideoListener {

    private K4LVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;
    String path = "";
    int limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimmer);
        Bundle extras = getIntent().getExtras();

        try{
            String action = extras.getString("action");
            String configuration_string = extras.getString("configuration");
            JSONObject configuration = new JSONObject(configuration_string);

            if(action.equals("trim")){
                trimVideo(configuration);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void trimVideo(JSONObject configuration){

        try{
            if(configuration.has("path")){
                path = configuration.getString("path");
            }

            if(configuration.has("limit")){
                limit = configuration.getInt("limit");
            }
            //setting progressbar
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.trimming_progress));

            mVideoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
            if (mVideoTrimmer != null) {
                mVideoTrimmer.setMaxDuration(limit);
                mVideoTrimmer.setOnTrimVideoListener(this);
                mVideoTrimmer.setOnK4LVideoListener(this);
                //mVideoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
                mVideoTrimmer.setVideoURI(Uri.parse(path));
                mVideoTrimmer.setVideoInformationVisibility(true);
            }


        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoTrimActivity.this, getString(R.string.video_saved_at, uri.getPath()), Toast.LENGTH_SHORT).show();
            }
        });

        try{
            JSONObject response = new JSONObject();
            response.put("outputFile", uri);
            response.put("status", "success");

            Intent intent = new Intent();
            intent.putExtra("information", response.toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        catch (JSONException e){}

    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }

    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoTrimActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        try{
            JSONObject response = new JSONObject();
            response.put("message", message);
            response.put("status", "success");

            Intent intent = new Intent();
            intent.putExtra("information", response.toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        catch (JSONException e){}

    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoTrimActivity.this, "Video Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
