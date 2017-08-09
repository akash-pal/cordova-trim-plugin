package org.apache.cordova.trim;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by AP47178 on 27-07-2017.
 */

public class VideoTrim extends CordovaPlugin {

    private CallbackContext PUBLIC_CALLBACKS = null;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        PUBLIC_CALLBACKS = callbackContext;
        String json_configuration = data.getString(0);

        try {
            Intent intent = new Intent("org.apache.cordova.trim.VideoTrimActivity");
            intent.putExtra("action", action);
            intent.putExtra("configuration", json_configuration);

            cordova.startActivityForResult((CordovaPlugin) this, intent, 0);
        } catch (ActivityNotFoundException e) {
            PluginResult errorResults = new PluginResult(PluginResult.Status.ERROR, "Cannot find activity org.apache.cordova.trim.VideoTrimActivity");
            errorResults.setKeepCallback(true);
            PUBLIC_CALLBACKS.sendPluginResult(errorResults);
        }

        return true;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if(resultCode == cordova.getActivity().RESULT_OK){
            Bundle extras = data.getExtras();
            String resultData = extras.getString("information");

            PluginResult resultado = new PluginResult(PluginResult.Status.OK, resultData);
            resultado.setKeepCallback(true);
            PUBLIC_CALLBACKS.sendPluginResult(resultado);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
