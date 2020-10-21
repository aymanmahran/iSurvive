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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CheckList extends AppCompatActivity implements View.OnClickListener{

    CheckBox[] bxs = new CheckBox[100];
    String[][] st = new String[100][5];
    int alldis = 0;
    String kit;

    String g ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDis();
            }
        });

        kit= getIntent().getStringExtra("kit");
        setTitle(kit);

        //WriteAll("");
        String s = ReadBtn();
        Divide(s);

    }

    public void WriteBtn(String args) {
        try {
            String a = ReadBtn();
            FileOutputStream fileout=openFileOutput("checks.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(a+args);
            outputWriter.close();

            /*Toast.makeText(getBaseContext(), "Disaster added successfully!\n" +a+args,
                    Toast.LENGTH_SHORT).show();*/
            Snackbar.make((LinearLayout) findViewById(R.id.field), "Item added successfully!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            String s = ReadBtn();
            Divide(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ReadBtn() {
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

    public void Divide(String h){

        int cnt = 0;
        int bt=0;
        int total = 0;
        st[total][0]="";
        st[total][1]="";
        st[total][2]="";
        st[total][3]="";
        st[total][4]="";

        LinearLayout options_layout = (LinearLayout) findViewById(R.id.field);
        for(int i=0;i<alldis;i++){
            options_layout.removeView((View) findViewById(i));
        }

        for(int i=0;i<h.length();i++){
            if(h.charAt(i) != ';'){
                st[total][cnt]+=h.charAt(i);
            }
            else {
                if (cnt % 5 != 4) {
                    cnt++;
                } else {
                    cnt=0;

                    if (st[total][0].equals(kit)){
                        cnt = 0;
                    LayoutInflater inflater = LayoutInflater.from(this);
                    options_layout = (LinearLayout) findViewById(R.id.field);
                    View to_add = inflater.inflate(R.layout.checkbox, options_layout, false);

                    bxs[total] = (CheckBox) to_add.findViewById(R.id.check);
                    bxs[total].setText(st[total][1]);

                    LinearLayout ln = (LinearLayout) to_add.findViewById(R.id.clk);
                    ln.setId(total);
                    ln.setOnClickListener(this);

                    ln.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {

                            final int a = v.getId();
                            AlertDialog.Builder builder = new AlertDialog.Builder(CheckList.this, R.style.AlertDialog);
                            builder.setTitle("Delete?");
                            final TextView one = new TextView(CheckList.this);
                            one.setText("Are you sure you want to delete this item?");
                            one.setTextColor(Color.BLACK);
                            one.setTextSize(16);
                            LinearLayout lay = new LinearLayout(CheckList.this);
                            lay.setOrientation(LinearLayout.VERTICAL);
                            lay.addView(one);
                            lay.setPadding(50, 50, 50, 0);
                            builder.setView(lay);

                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String s = "";
                                    for (int j = 0; j < a; j++) {
                                        s = s + kit + ";" + st[j][1] + ";" + st[j][2] + ";" + st[j][3] + ";" + st[j][4] + ";";
                                    }
                                    for (int j = a + 1; j < alldis; j++) {
                                        s = s + kit + ";" + st[j][1] + ";" + st[j][2] + ";" + st[j][3] + ";" + st[j][4] + ";";
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

                            return true;
                        }

                    });


                    if (st[total][4] == "1") {
                        bxs[total].setChecked(true);
                    } else {
                        bxs[total].setChecked(false);
                    }

                    total++;

                    options_layout.addView(to_add);

                    st[total][0] = "";
                    st[total][1] = "";
                    st[total][2] = "";
                    st[total][3] = "";
                    st[total][4] = "";
                }
                else{
                        st[total][0] = "";
                        st[total][1] = "";
                        st[total][2] = "";
                        st[total][3] = "";
                        st[total][4] = "";
                    }
                }
            }
        }
        alldis = total;
    }



    public void AddDis(){

        AlertDialog.Builder builder = new AlertDialog.Builder(CheckList.this, R.style.AlertDialog);
        builder.setTitle("Add item");

        final EditText one = new EditText(this);
        final EditText two = new EditText(this);
        final EditText three = new EditText(this);
        one.setInputType(InputType.TYPE_CLASS_TEXT);
        two.setInputType(InputType.TYPE_CLASS_NUMBER);
        three.setInputType(InputType.TYPE_CLASS_NUMBER);
        one.setHint("Item");
        two.setHint("Weight");
        three.setHint("Count");

        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(one);
        lay.addView(two);
        lay.addView(three);
        lay.setPadding(50, 0, 50 , 0);
        builder.setView(lay);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String item = one.getText().toString();
                final String weight = two.getText().toString();
                final String count = three.getText().toString();

                WriteBtn(kit + ";" + item + ";" + weight + ";" + count + ";0;");

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

        AlertDialog.Builder builder = new AlertDialog.Builder(CheckList.this, R.style.AlertDialog);
        builder.setTitle("Edit item");

        final EditText one = new EditText(this);
        final EditText two = new EditText(this);
        final EditText three = new EditText(this);
        one.setInputType(InputType.TYPE_CLASS_TEXT);
        two.setInputType(InputType.TYPE_CLASS_NUMBER);
        three.setInputType(InputType.TYPE_CLASS_NUMBER);
        one.setHint("Item");
        two.setHint("Weight");
        three.setHint("Count");
        one.setText(st[a][1]);
        two.setText(st[a][2]);
        three.setText(st[a][3]);

        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(one);
        lay.addView(two);
        lay.addView(three);
        lay.setPadding(50, 0, 50 , 0);
        builder.setView(lay);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String item = one.getText().toString();
                final String weight = two.getText().toString();
                final String count = three.getText().toString();

                String s="";
                for(int j=0;j<a;j++){
                    s=s+kit + ";" + st[j][1] + ";" + st[j][2] + ";" + st[j][3] + ";"+ st[j][4] +";";
                }
                s=s+kit + ";" + item + ";" + weight + ";" + count + ";"+st[a][4]+";";
                for(int j=a+1;j<alldis;j++){
                    s=s+kit + ";" + st[j][1] + ";" + st[j][2] + ";" + st[j][3] + ";"+ st[j][4] +";";
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
    public void WriteAll(String args) {
        try {
            FileOutputStream fileout=openFileOutput("checks.txt", MODE_PRIVATE);
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

}
