package com.example.ayman.isurvive;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HomeScreen extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    TextView content;
    Button saveme;
    EditText fname, email, login, pass;
    String Name, Email, Login, Pass;
    private WebView wv1;
    LinearLayout scrl;

    int state = 0;
    int safe = 0;

    String[] data = new String[100];
    int total_members=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        /*wv1=(WebView)findViewById(R.id.webView);

        final MyJavaScriptInterface myJavaScriptInterface
                = new MyJavaScriptInterface(this);
        wv1.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");

        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setWebViewClient(new WebViewClient());
        wv1.loadUrl("https://www.google.com");*/

        //WriteBtn("");
        final Button btn = (Button) findViewById(R.id.button);

        btn.setBackgroundColor(Color.RED);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        scrl = findViewById(R.id.members);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();



                        if(menuItem.getTitleCondensed().equals("kits")){
                            startActivity(new Intent(HomeScreen.this, KitsList.class));
                        }
                        else if(menuItem.getTitleCondensed().equals("learn")){
                            startActivity(new Intent(HomeScreen.this, One.class));
                        }
                        else if(menuItem.getTitleCondensed().equals("connect")){
                            Con();
                        }
                        else if(menuItem.getTitleCondensed().equals("code")){
                            Cod();
                        }

                        return true;
                    }
                });

        //WriteBtn("");
        update_members();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn.getText().equals("HELP!")){
                    btn.setText("I'm OK now :)");
                    btn.setBackgroundColor(Color.GREEN);
                    state = 1;
                    safe = 1;
                    new PostDataAsyncTask().execute();
                }
                else{
                    btn.setText("HELP!");
                    btn.setBackgroundColor(Color.RED);
                    state = 1;
                    safe = 0;
                    new PostDataAsyncTask().execute();
                }
            }
        });

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //CALL YOUR ASSYNC TASK HERE.
                state=0;
                total_members=0;
                new PostDataAsyncTask().execute();
            }
        };

        Timer timer = new Timer();

        //DELAY: the time to the first execution
        //PERIODICAL_TIME: the time between'iip[pi each execution of your task.
        timer.schedule(timerTask, 20,10000);


      /*  Snackbar.make((FrameLayout) findViewById(R.id.content_frame), Boolean.toString(isInternetAvailable()), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
/*
        try {
            URL url = new URL("http://stackoverflow.com/posts/11642475/edit" );
            //URL url = new URL("http://www.nofoundwebsite.com/" );
            executeReq(url);
            Toast.makeText(getApplicationContext(), "Webpage is available!", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), "oops! webpage is not available!", Toast.LENGTH_SHORT).show();
        }*/


        /*saveme=(Button)findViewById(R.id.button);


        saveme.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v)
            {
                try{
                    //GetText(0, 0);
                    new PostDataAsyncTask().execute();
                }
                catch(Exception ex)
                {
                    content.setText(" url exeption! " );
                }
            }
        });*/
    }

    /*public  void  GetText(int sr, int val)  throws UnsupportedEncodingException {

        if (sr == 0) {
            String data = URLEncoder.encode("family", "UTF-8")
                    + "=" + URLEncoder.encode(Integer.toString(2), "UTF-8");

        //data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(Email, "UTF-8");

            String text = "";
            BufferedReader reader = null;

            try {
                URL url = new URL("https://isurvive-app.000webhostapp.com/get_member.php");

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                text = sb.toString();
                Snackbar.make((FrameLayout) findViewById(R.id.content_frame), text, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (Exception ex) {
                Snackbar.make((FrameLayout) findViewById(R.id.content_frame), "fdgd", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                }
            }

        }
        else {
            String data = URLEncoder.encode("family", "UTF-8")
                    + "=" + URLEncoder.encode(Name, "UTF-8");

            data += "&" + URLEncoder.encode("member", "UTF-8") + "="
                   + URLEncoder.encode(Email, "UTF-8");

            data += "&" + URLEncoder.encode("state", "UTF-8") + "="
                    + URLEncoder.encode(Integer.toString(val), "UTF-8");

            String text = "";
            BufferedReader reader = null;

            try {
                URL url = new URL("https://isurvive-app.000webhostapp.com/add_member.php");

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                text = sb.toString();
            } catch (Exception ex) {

            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                }
            }

        }
    }
    */
   /* @Override
    public void onClick(View v){
        if(v.getId()==R.id.button){
            try {
                GetText(0,0);
            } catch (UnsupportedEncodingException e) {
                Snackbar.make((FrameLayout) findViewById(R.id.content_frame), "GFd", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                e.printStackTrace();
            }
        }
    }*/

   /* public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    private void executeReq(URL urlObject) throws IOException
    {
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) urlObject.openConnection();
        conn.setReadTimeout(30000);//milliseconds
        conn.setConnectTimeout(3500);//milliseconds
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        // Start connect
        conn.connect();
        InputStream response =conn.getInputStream();
        Log.d("Response:", response.toString());
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }*/

    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast){
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void openAndroidDialog(){
            AlertDialog.Builder myDialog
                    = new AlertDialog.Builder(HomeScreen.this);
            myDialog.setTitle("DANGER!");
            myDialog.setMessage("You can do what you want!");
            myDialog.setPositiveButton("ON", null);
            myDialog.show();
        }

        @JavascriptInterface
        public void ede(String c){
            // simpleSwitch1.setText(c+process(x)+process(y));

        }
    }



    public class PostDataAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                // 1 = post text data, 2 = post file
                int actionChoice = 1;

                // post a text data
                if(actionChoice==1){
                    postText();
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String lenghtOfFile) {
            String d="";
            if(state == 0){
                scrl.removeAllViews();
                for (int i = 0; i < total_members; i++) {
                LayoutInflater inflater = LayoutInflater.from(HomeScreen.this);
                View to_add = inflater.inflate(R.layout.mem, scrl, false);

                TextView text = (TextView) to_add.findViewById(R.id.n1);
                text.setText(data[i * 2]);
                TextView tit = (TextView) to_add.findViewById(R.id.n2);
                if (data[(i * 2) + 1].equals("0")) {
                    tit.setText("Safe :)");
                } else if (data[(i * 2) + 1].equals("1")) {
                    d+=data[i*2];
                    d+=",";
                    tit.setText("IN DANGER!");
                }
                            /*data[0]="";
                            data[1]="";
                            cn=0;*/
                scrl.addView(to_add);
            }
            if(d.length()!=0){
                    addNotification(d);
            }
        }
        }
    }


    // this will post our text data
    private void postText(){
        try{
            // url where the data will be posted
            String postReceiverUrl;
            if(state == 0) {
                postReceiverUrl = "https://isurvive-app.000webhostapp.com/get_member.php";
            }
            else{
                postReceiverUrl = "https://isurvive-app.000webhostapp.com/add_member.php";
            }
            //Log.v(TAG, "postURL: " + postReceiverUrl);

            // HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            // post header
            HttpPost httpPost = new HttpPost(postReceiverUrl);

            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            if(state == 0) {
                nameValuePairs.add(new BasicNameValuePair("family", pro_read(0)));
            }
            else{
                nameValuePairs.add(new BasicNameValuePair("family", pro_read(0)));
                nameValuePairs.add(new BasicNameValuePair("member", pro_read(1)));
                nameValuePairs.add(new BasicNameValuePair("state", Integer.toString(safe)));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // execute HTTP post request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                //Snackbar.make((FrameLayout) findViewById(R.id.content_frame), responseStr, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //responseStr = "ayman,0,tarek,0,";

                if(state == 0){
                    fun(responseStr);
                }


                //Snackbar.make((FrameLayout) findViewById(R.id.content_frame), responseStr, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //Log.v(TAG, "Response: " +  responseStr);



            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteBtn(String args) {
        try {
            String a = ReadBtn();
            FileOutputStream fileout=openFileOutput("code.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(args);
            outputWriter.close();

            /*Toast.makeText(getBaseContext(), "Disaster added successfully!\n" +a+args,
                    Toast.LENGTH_SHORT).show();*/
            //Snackbar.make((FrameLayout) findViewById(R.id.content_frame), "Disaster added successfully!", Snackbar.LENGTH_LONG).setAction("Action", null).show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ReadBtn() {
        try {
            FileInputStream fileIn=openFileInput("code.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            return s;
            //textmsg.setText(s);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void Con(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this, R.style.AlertDialog);
        builder.setTitle("Details");

        final EditText one = new EditText(this);
        final EditText two = new EditText(this);
        one.setInputType(InputType.TYPE_CLASS_TEXT);
        two.setInputType(InputType.TYPE_CLASS_NUMBER);
        one.setHint("Name");
        two.setHint("Code");

        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(one);
        lay.addView(two);
        lay.setPadding(50, 0, 50 , 0);
        builder.setView(lay);

        builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String nam = one.getText().toString();
                final String cod = two.getText().toString();
                WriteBtn(cod + ";" + nam + ";");
                state = 1;
                safe = 0;
                new PostDataAsyncTask().execute();
                Snackbar.make((FrameLayout) findViewById(R.id.content_frame), "Action done successfully!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        //input1.setCompoundDrawablePadding(50);
        //input2.setCompoundDrawablePadding(50);
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();

        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        builder.show().getWindow().setLayout((int)(displayRectangle.width() *
                0.95f), ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public String pro_read(int pos){
        String s = ReadBtn();
        String[] d = {"", ""};
        int cnt=0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=';'){
                d[cnt]+=s.charAt(i);
            }
            else{
                cnt++;
            }
        }
        return d[pos];
    }

    public void Cod(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this, R.style.AlertDialog);
        builder.setTitle("Code");
        final TextView one = new TextView(HomeScreen.this);
        one.setText(get_code());
        one.setTextColor(Color.BLACK);
        one.setTextSize(16);
        LinearLayout lay = new LinearLayout(HomeScreen.this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(one);
        lay.setPadding(50, 50, 50 , 0);
        builder.setView(lay);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public String get_code(){
        String s = ReadBtn();
        if(s.length()==0){
            long time= System.currentTimeMillis();
            /*Snackbar.make((LinearLayout) findViewById(R.id.main_con), Long.toString(time), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
            WriteBtn(Long.toString(time)+";");
            return Long.toString(time);
            //return "Df";
        }
        else{
            return pro_read(0);
        }
    }
    public void update_members(){
        state = 0;
        safe = 0;
        total_members=0;
        //Snackbar.make((FrameLayout) findViewById(R.id.content_frame), Integer.toString(total_members), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void fun(String responseStr){
        int cn = 0;
        data[0]="";
        for(int i=0;i<responseStr.length();i++){
            if(responseStr.charAt(i)!=','){
                data[cn]+=responseStr.charAt(i);
            }
            else{
                if(cn%2!=1){
                    cn++;
                    data[cn]="";
                }
                else{
                    cn++;
                    data[cn]="";
                    total_members++;
                }
            }
        }
        return;
    }

    private void addNotification(String s) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo2)
                        .setContentTitle("Someone needs help!")
                        .setContentText(s+" are in DANGER!")
                        .setSound(soundUri);

        Intent notificationIntent = new Intent(this, HomeScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
