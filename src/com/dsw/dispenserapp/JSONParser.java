package com.dsw.dispenserapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
 
public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONParser() {
 
    }
 
    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
 
        // Making HTTP request
        try {        	
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpParams httpParameters = httpPost.getParams();
            
            //Set the timeout in milliseconds until a connection is established
        	int timeoutConnection = 180000;
        	HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        	
        	// Set the default socket timeout (SO_TIMEOUT) 
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 180000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        	
            HttpResponse httpResponse = httpClient.execute(httpPost);
            
            StatusLine statusLine = httpResponse.getStatusLine();
            
            int statusCode = statusLine.getStatusCode();
            
            if (statusCode == 200) {
            	HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
                Log.e("JSON", json);
                jObj = new JSONObject(json);
                return jObj;
            }
            else {
            	Log.e("Server Error", "Failed to download file");
            }            
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            //Log.e("JSON Parser", "Error parsing data " + e.toString());
        	e.printStackTrace();
        }       
 
        // return JSON String
        return null; 
    }
}