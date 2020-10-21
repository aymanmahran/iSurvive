package com.example.ayman.isurvive;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;

public class KitsList extends AppCompatActivity implements View.OnClickListener {


    Button[][] btns = new Button[100][2];
    String[][] st = new String[100][4];
    String[] st1 = new String[5];
    int alldis = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kits_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, ReadBtn(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                AddDis();
            }
        });

        /*try {
            String a = ReadBtn();
            FileOutputStream fileout=openFileOutput("kits.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("");
            outputWriter.close();

            Toast.makeText(getBaseContext(), "Disaster added successfully!\n",
                    Toast.LENGTH_SHORT).show();
            String s = ReadBtn();
            Divide(s);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //Snackbar.make((LinearLayout) findViewById(R.id.main_con), Divide2("","fd"), Snackbar.LENGTH_LONG).setAction("Action", null).show();

        //String f = Divide2(ReadBtn2(),"Dfd");

        String s = ReadBtn();

        if(s.length()==0){
            s="Volcano;-;0;-;Flood;-;0;-;Hurricane;-;0;-;Earthquake;-;0;-;";
            WriteAll(s);
        }

        Divide(s);

        /*LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout options_layout = (LinearLayout) findViewById(R.id.main_con);
        for (int i = 0; i < 6; i++) {
            View to_add = inflater.inflate(R.layout.kit,
                    options_layout,false);

            TextView text = (TextView) to_add.findViewById(R.id.des);
            text.setText("df");
            options_layout.addView(to_add);
        }*/


    }

    @Override
    protected void onResume(){
        String s = ReadBtn();

        if(s.length()==0){
            s="Volcano;-;0;-;Flood;-;0;-;Hurricane;-;0;-;Earthquake;-;0;-;";
        }

        Divide(s);
        super.onResume();

    }

    public void WriteBtn(String args) {
        try {
            String a = ReadBtn();
            FileOutputStream fileout=openFileOutput("kits.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(a+args);
            outputWriter.close();

            /*Toast.makeText(getBaseContext(), "Disaster added successfully!\n" +a+args,
                    Toast.LENGTH_SHORT).show();*/
            Snackbar.make((LinearLayout) findViewById(R.id.main_con), "Disaster added successfully!", Snackbar.LENGTH_LONG).setAction("Action", null).show();


            String s = ReadBtn();
            Divide(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ReadBtn() {
        try {
            FileInputStream fileIn=openFileInput("kits.txt");
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

    public void Divide(String h){

        int cnt = 0;
        int bt=0;
        int total = 0;
        st[total][0]="";
        st[total][1]="";
        st[total][2]="";
        st[total][3]="";

        LinearLayout options_layout = (LinearLayout) findViewById(R.id.main_con);
        for(int i=0;i<alldis;i++){
            options_layout.removeView((View) findViewById(i*3));
        }

        for(int i=0;i<h.length();i++){
            if(h.charAt(i) != ';'){
                st[total][cnt]+=h.charAt(i);
            }
            else{
                if(cnt%4!=3){
                    cnt++;
                }
                else{
                    cnt=0;
                    LayoutInflater inflater = LayoutInflater.from(this);
                    options_layout = (LinearLayout) findViewById(R.id.main_con);
                    View to_add = inflater.inflate(R.layout.kit, options_layout,false);

                    TextView text = (TextView) to_add.findViewById(R.id.des);

                    String g="";
                    g = Divide2(ReadBtn2(),st[total][0]);
                    if(g.length()==0){
                        g="0";
                    }
                    text.setText("Place: " + st[total][1] + "\nWeight: " + g + "kg\nColor: " + st[total][3]);
                    TextView tit = (TextView) to_add.findViewById(R.id.nm);
                    tit.setText(st[total][0]);

                    LinearLayout lo = (LinearLayout) to_add.findViewById(R.id.mn);
                    lo.setId(bt);
                    lo.setOnClickListener(this);
                    bt++;

                    FrameLayout lk = (FrameLayout) to_add.findViewById(R.id.back_image);
                    if(st[total][0].equals("Volcano")){
                        lk.setBackground(getDrawable(R.drawable.d6));
                    }
                    else if(st[total][0].equals("Flood")){
                        lk.setBackground(getDrawable(R.drawable.d8));
                    }
                    else if(st[total][0].equals("Hurricane")){
                        lk.setBackground(getDrawable(R.drawable.d9));
                    }
                    else if(st[total][0].equals("Earthquake")){
                        lk.setBackground(getDrawable(R.drawable.d7));
                    }
                    else{
                        lk.setBackground(getDrawable(R.drawable.d0));
                    }

                    btns[total][0] = (Button) to_add.findViewById(R.id.delete);
                    //btns[total].setTag("dis"+Integer.toString(total)+"_edit");
                    btns[total][0].setId(bt);
                    bt++;
                    btns[total][0].setOnClickListener(this);

                    btns[total][1] = (Button) to_add.findViewById(R.id.edit);
                    btns[total][1].setId(bt);
                    bt++;
                    btns[total][1].setOnClickListener(this);


                    total ++;

                    options_layout.addView(to_add);

                    st[total][0]="";
                    st[total][1]="";
                    st[total][2]="";
                    st[total][3]="";
                }
            }
        }
        alldis = total;
    }



    public void AddDis(){

        AlertDialog.Builder builder = new AlertDialog.Builder(KitsList.this, R.style.AlertDialog);
        builder.setTitle("Add Kit");

        final EditText one = new EditText(this);
        final EditText two = new EditText(this);
        final EditText three = new EditText(this);
        final EditText four = new EditText(this);
        one.setInputType(InputType.TYPE_CLASS_TEXT);
        two.setInputType(InputType.TYPE_CLASS_TEXT);
        three.setInputType(InputType.TYPE_CLASS_NUMBER);
        four.setInputType(InputType.TYPE_CLASS_TEXT);
        one.setHint("Disaster");
        two.setHint("Place");
        three.setHint("Weight");
        four.setHint("Color");

        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(one);
        lay.addView(two);
        lay.addView(three);
        lay.addView(four);
        lay.setPadding(50, 0, 50 , 0);
        builder.setView(lay);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String disaster = one.getText().toString();
                final String place = two.getText().toString();
                final String weight = three.getText().toString();
                final String color = four.getText().toString();

                WriteBtn(disaster + ";" + place + ";" + weight + ";" + color + ";");

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

    @Override
    public void onClick(View v) {
        final int a = v.getId();
        //Toast.makeText(getBaseContext(), Integer.toString(a), Toast.LENGTH_SHORT).show();
        if(a%3==0){
            Intent intent = new Intent(KitsList.this, CheckList.class);
            intent.putExtra("kit", st[a/3][0]);
            startActivity(intent);
        }
        else if(a%3==1){

            AlertDialog.Builder builder = new AlertDialog.Builder(KitsList.this, R.style.AlertDialog);
            builder.setTitle("Delete?");
            final TextView one = new TextView(this);
            one.setText("Are you sure you want to delete this kit?");
            one.setTextColor(Color.BLACK);
            one.setTextSize(16);
            LinearLayout lay = new LinearLayout(this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(one);
            lay.setPadding(50, 50, 50 , 0);
            builder.setView(lay);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String s="";
                            for(int j=0;j<((a-1)/3);j++){
                                s=s+st[j][0]+";"+st[j][1]+";"+st[j][2]+";"+st[j][3]+";";
                            }
                            for(int j=((a-1)/3)+1;j<alldis;j++){
                                s=s+st[j][0]+";"+st[j][1]+";"+st[j][2]+";"+st[j][3]+";";
                            }
                            WriteAll(s);
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(KitsList.this, R.style.AlertDialog);
            builder.setTitle("Add Kit");

            final EditText one = new EditText(this);
            final EditText two = new EditText(this);
            final EditText three = new EditText(this);
            final EditText four = new EditText(this);
            one.setInputType(InputType.TYPE_CLASS_TEXT);
            two.setInputType(InputType.TYPE_CLASS_TEXT);
            three.setInputType(InputType.TYPE_CLASS_NUMBER);
            four.setInputType(InputType.TYPE_CLASS_TEXT);
            one.setHint("Disaster");
            two.setHint("Place");
            three.setHint("Weight");
            four.setHint("Color");
            one.setText(st[(a-2)/3][0]);
            two.setText(st[(a-2)/3][1]);
            three.setText(st[(a-2)/3][2]);
            four.setText(st[(a-2)/3][3]);

            LinearLayout lay = new LinearLayout(this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(one);
            lay.addView(two);
            lay.addView(three);
            lay.addView(four);
            lay.setPadding(50, 0, 50 , 0);
            builder.setView(lay);

            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final String disaster = one.getText().toString();
                    final String place = two.getText().toString();
                    final String weight = three.getText().toString();
                    final String color = four.getText().toString();

                    String s="";
                    for(int j=0;j<((a-2)/3);j++){
                        s=s+st[j][0]+";"+st[j][1]+";"+st[j][2]+";"+st[j][3]+";";
                    }
                    s=s+disaster + ";" + place + ";" + weight + ";" + color + ";";
                    for(int j=((a-2)/3)+1;j<alldis;j++){
                        s=s+st[j][0]+";"+st[j][1]+";"+st[j][2]+";"+st[j][3]+";";
                    }

                    WriteAll(s);

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
    }

    public void WriteAll(String args) {
        try {
            FileOutputStream fileout=openFileOutput("kits.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(args);
            outputWriter.close();

            /*Toast.makeText(getBaseContext(), "Disaster added successfully!\n" +a+args,
                    Toast.LENGTH_SHORT).show();*/

            String s = ReadBtn();
            Divide(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String ReadBtn2() {
        try {
            FileInputStream fileIn=openFileInput("checks.txt");
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

    public String Divide2(String h, String ki) {

        int cnt = 0;
        int tot=0;
        st1[0] = "";
        st1[1] = "";
        st1[2] = "";
        st1[3] = "";
        st1[4] = "";

        for (int i = 0; i < h.length(); i++) {
            if (h.charAt(i) != ';') {
                st1[cnt] += h.charAt(i);
            }
            else {
                if (cnt % 5 != 4) {
                    cnt++;
                }
                else {
                    cnt = 0;

                    if (st1[0].equals(ki)) {
                        cnt = 0;
                        tot += (Integer.parseInt(st1[2]) * Integer.parseInt(st1[3]));
                    }
                    st1[0] = "";
                    st1[1] = "";
                    st1[2] = "";
                    st1[3] = "";
                    st1[4] = "";
                }
            }
        }
        //alldis = total;*/
        return Integer.toString(tot);
    }
}
