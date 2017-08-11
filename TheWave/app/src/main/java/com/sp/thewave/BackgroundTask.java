package com.sp.thewave;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


/**v
 * Created by Raman Kannan on 7/6/2017.
 */

public class BackgroundTask extends AsyncTask<String, Void, String> {
    AlertDialog alertDialog;
    String method;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SENSOR = "sensor";
    private static final String TAG_SID = "sid";
    private static final String TAG_NAME = "name";
    private static final String TAG_READING = "reading";

    BackgroundTask()
    {

    }

    @Override
    protected void onPreExecute() {
        //alertDialog=new AlertDialog.Builder(ctx).create();
        //alertDialog.setTitle("Login Information...");
        //super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url = "http://10.0.2.2:10/theWavePHP/register.php";
        String login_url = "http://10.0.2.2:10/theWavePHP/login.php";
        String book_url = "http://10.0.2.2:10/theWavePHP/book.php";
        String cancelBooking_url = "http://10.0.2.2:10/theWavePHP/cancelBooking.php";
        String getResourceStatus_url = "http://10.0.2.2:10/theWavePHP/getResourceStatus.php";

        method = params[0];
        if (method.equals("register")) {
            String name = params[1];
            String nric = params[2];
            String userName = params[3];
            String userPass = params[4];
            String phoneNumber = params[5];
            String email = params[6];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("nric", "UTF-8") + "=" + URLEncoder.encode(nric, "UTF-8") + "&" +
                        URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                        URLEncoder.encode("userPass", "UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8") + "&" +
                        URLEncoder.encode("phoneNumber", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("login")) {
            String userName = params[1];
            String userPass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                        URLEncoder.encode("userPass", "UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (method.equals("book")) {

            String selectResource = params[1];
            String selectDate = params[2];
            String selectSession = params[3];
            String userName=params[4];

            try {
                URL url = new URL(book_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("selectResource", "UTF-8") + "=" + URLEncoder.encode(selectResource, "UTF-8") + "&" +
                        URLEncoder.encode("selectDate", "UTF-8") + "=" + URLEncoder.encode(selectDate, "UTF-8") + "&" +
                        URLEncoder.encode("selectSession", "UTF-8") + "=" + URLEncoder.encode(selectSession, "UTF-8")+"&"+
                        URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

              InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (method.equals("cancelBooking")) {

            String selectResource = params[1];
            String selectDate = params[2];
            String selectSession = params[3];
            String userName = params[4];

            try {
                URL url = new URL(cancelBooking_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("selectResource", "UTF-8") + "=" + URLEncoder.encode(selectResource, "UTF-8") + "&" +
                        URLEncoder.encode("selectDate", "UTF-8") + "=" + URLEncoder.encode(selectDate, "UTF-8") + "&" +
                        URLEncoder.encode("selectSession", "UTF-8") + "=" + URLEncoder.encode(selectSession, "UTF-8")  + "&" +
                        URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("getStatus")) {

            String selectResource = params[1];

            try {
                URL url = new URL(getResourceStatus_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("selectResource", "UTF-8") + "=" + URLEncoder.encode(selectResource, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }

        return null;
    }

        @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);

    }

    protected void onPostExecuteCallback (JSONObject json) {

    }

    @Override
    protected void onPostExecute(String result){
        try {
            JSONObject json = new JSONObject(result);
            onPostExecuteCallback (json);
        } catch (JSONException e) {

        } finally {

        }
    }
}



