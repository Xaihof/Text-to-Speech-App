package com.example.texttospeach;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

//Implementing onitemselectedlistner in Main Class for Spinner.
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Initialization & Declaration.
    EditText et_user_text;
    SeekBar sb_pitch, sb_speed;
    Button btn_say_it, btn_clear;
    TextToSpeech var_text_to_speech;
    Spinner spinner_var;
    ImageButton mic_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Assigning Variables.
        et_user_text = findViewById(R.id.et_text);
        sb_pitch = findViewById(R.id.skbr_pitch);
        sb_speed = findViewById(R.id.skbr_speed);
        btn_say_it = findViewById(R.id.button_say_it);
        btn_clear = findViewById(R.id.button_clear);
        spinner_var = findViewById(R.id.language);
        mic_var = findViewById(R.id.mic);

        //Setting OnClickListner on button say it.
        btn_say_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling speak function/method.
                speak();
            }
        });

        //Setting OnClickListner on button clear.
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_user_text.setText("");
            }
        });

        //Setting OnClickListner on mic.
        mic_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                try {
                    startActivityForResult(intent, 7);
                    et_user_text.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Your Device Does Not Support Speech Input.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        //Making and Setting Array Adapter for Spinner.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.accent, android.R.layout.simple_spinner_dropdown_item);
        spinner_var.setAdapter(adapter);

        //Setting selected item clicklistner on spinner.
        spinner_var.setOnItemSelectedListener(this);

    }

    //Overriding onActivityResult method for Speech to text.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 7:

                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_user_text.setText(result.get(0));
                }
                break;
        }
    }

    //defining speak method/function.
    private void speak() {
        String user_text = et_user_text.getText().toString();
        Float pitch = (float) sb_pitch.getProgress() / 50;
        if (pitch < 0.1) {
            pitch = 0.1f;
        }
        Float speed = (float) sb_speed.getProgress() / 50;
        if (speed < 0.1f) {
            speed = 0.1f;
        }
        var_text_to_speech.setPitch(pitch);
        var_text_to_speech.setSpeechRate(speed);

        var_text_to_speech.speak(user_text, TextToSpeech.QUEUE_FLUSH, null);

    }


    //Calling predefined onDestroy function by pressing ctrl+o.
    @Override
    protected void onDestroy() {
        if (var_text_to_speech != null) {
            var_text_to_speech.stop();
            var_text_to_speech.shutdown();
        }
        super.onDestroy();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();


        if (text.equals("US(American)")) {
            //Initializing Text To Speech Engine.
            var_text_to_speech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
                        int result = var_text_to_speech.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This language is not supported.");
                            Toast.makeText(MainActivity.this, "Can not understand this language.", Toast.LENGTH_SHORT).show();
                        } else {
                            btn_say_it.setEnabled(true);
                            btn_clear.setEnabled(true);
                        }
                    } else {
                        Log.e("TTS", "Initialization failed");
                    }

                }
            });
        }


        if (text.equals("UK(British)")) {
            //Initializing Text To Speech Engine.
            var_text_to_speech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
                        int result = var_text_to_speech.setLanguage(Locale.UK);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This language is not supported.");
                            Toast.makeText(MainActivity.this, "Can not understand this language.", Toast.LENGTH_SHORT).show();
                        } else {
                            btn_say_it.setEnabled(true);
                            btn_clear.setEnabled(true);
                        }
                    } else {
                        Log.e("TTS", "Initialization failed");
                    }

                }
            });
        }
        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}