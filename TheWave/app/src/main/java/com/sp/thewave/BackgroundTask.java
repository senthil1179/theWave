package com.sp.thewave;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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


/**
 * Created by Raman Kannan on 7/6/2017.
 */

public class BackgroundTask extends AsyncTask<String, Void, String> {
    AlertDialog alertDialog;
    Context ctx;
    BackgroundTask(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information...");
        //super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url = "http://10.0.2.2/theWavePHP/register.php";
        String login_url = "http://10.0.2.2/theWavePHP/login.php";
        String book_url = "http://10.0.2.2/theWavePHP/book.php";
        String cancelBooking_url = "http://10.0.2.2/theWavePHP/cancelBooking.php";
        String getResourceStatus_url = "http://10.0.2.2/theWavePHP/getResourceStatus.php";

        String method = params[0];
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
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("nric", "UTF-8") + "=" + URLEncoder.encode(nric, "UTF-8") + "&" +
                        URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                        URLEncoder.encode("userPass", "UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8") + "&" +
                        URLEncoder.encode("phoneNumber", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Successful!";
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
                        URLEncoder.encode("selectSession", "UTF-8") + "=" + URLEncoder.encode(selectSession, "UTF-8");
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
            //String selectDate = params[2];
           // String selectSession = params[3];


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

    @Override
    protected void onPostExecute(String result){
        if(result.equals("Registration Successful!"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

        }
        else if(result.equals("Already booked!")){
            Toast.makeText(ctx, result+"Please choose another session / resource.", Toast.LENGTH_LONG).show();
           // Toast.makeText(ctx, result+"Please choose another session / resource.", Toast.LENGTH_LONG).show();
            //Toast.makeText(ctx, result+"Please choose another session / resource.", Toast.LENGTH_LONG).show();
            //Toast.makeText(ctx, result+"Please choose another session / resource.", Toast.LENGTH_LONG).show();
        }
         else if(result.equals("Insertion Successful!")){
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else if(result.equals("No Booking detected!")){
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else if(result.equals("Cancelled Booking!")){
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }

        else
        {
           alertDialog.setMessage(result);
            alertDialog.show();

          //  Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

        }

    }
}



