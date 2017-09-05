package com.iteso.sesion5;

import android.content.Context;
import android.content.DialogInterface;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.iteso.sesion5.Beans.Student;
import com.iteso.sesion5.Constants.Constants;

public class ActivityMain extends AppCompatActivity {

    protected EditText name;
    protected EditText phone;
    protected AutoCompleteTextView book;
    protected CheckedTextView sport;
    final protected Student student = new Student();
    protected Spinner scholarship;
    protected RadioGroup gender;
    protected Button clean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.activity_main_name);
        phone = (EditText) findViewById(R.id.activity_main_phone);
        book = (AutoCompleteTextView)findViewById(R.id.activity_main_book);
        scholarship = (Spinner)findViewById(R.id.activity_main_spinner);
        sport = (CheckedTextView)findViewById(R.id.activity_main_sport);
        clean = (Button) findViewById(R.id.activity_main_button);
        gender = (RadioGroup) findViewById(R.id.activity_main_gender);


        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sport.isChecked()) sport.setChecked(false);
                else sport.toggle();
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(ActivityMain.this);

                alertDialog.setTitle("Desea limpiar el contenido?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Clean();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        });
        String[] books = getResources().getStringArray(R.array.activity_main_books);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, books);
        book.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.activity_main_save:
                student.setName(name.getText().toString());
                student.setPhone(phone.getText().toString());
                student.setScholarship(scholarship.getSelectedItem().toString());
                student.setBook(book.getText().toString());
                if(gender.getCheckedRadioButtonId() == R.id.activity_main_female){
                    student.setGender("Femenino");
                }
                else student.setGender("Masculino");
                if(sport.isChecked()) student.setSport("Si");
                else student.setSport("No");
                Toast toast = Toast.makeText(this, student.toString(), Toast.LENGTH_LONG);
                toast.show();
                Clean();
                return true;
        }
        return true;
    }
    public void Clean(){
        name.setText("");
        phone.setText("");
        book.setText("");
        if(gender.getCheckedRadioButtonId() ==R.id.activity_main_male){
            gender.clearCheck();
            gender.check(R.id.activity_main_female);
        }
        scholarship.setSelection(0);
        sport.setChecked(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.NAME, name.getText().toString());
        outState.putString(Constants.PHONE, phone.getText().toString());
        outState.putString(Constants.BOOK, student.getBook());
        outState.putInt(Constants.SCHOLARSHIP, scholarship.getSelectedItemPosition());
        outState.putBoolean(Constants.SPORT, sport.isChecked());
        outState.putInt(Constants.GENDER, gender.getCheckedRadioButtonId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle s) {
        super.onRestoreInstanceState(s);
        if(s!= null){
            name.setText(s.getString(Constants.NAME));
            phone.setText(s.getString(Constants.PHONE));
            book.setText(s.getString(Constants.BOOK));
            scholarship.setSelection(s.getInt(Constants.SCHOLARSHIP));
            sport.setChecked(s.getBoolean(Constants.SPORT));
            gender.check(s.getInt(Constants.GENDER));
        }
    }
}
