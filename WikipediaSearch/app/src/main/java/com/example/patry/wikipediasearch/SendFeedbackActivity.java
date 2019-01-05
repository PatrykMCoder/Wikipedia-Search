package com.example.patry.wikipediasearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendFeedbackActivity extends AppCompatActivity {


    private Button buttonSendFeedback;
    private EditText editTextMessage;

    private String message;
    private String feedbackEmil;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);

        feedbackEmil = "";
        subject = "feedback";

        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSendFeedback = findViewById(R.id.send_feedback_item);
    }


    public void sendButton(View v){
        sendFeedback();
    }

    private void sendFeedback(){
        message = editTextMessage.getText().toString();

        Intent intentEmail = new Intent(Intent.ACTION_SEND);

        intentEmail.putExtra(Intent.EXTRA_EMAIL, feedbackEmil);
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
        intentEmail.putExtra(Intent.EXTRA_TEXT, message);

        intentEmail.setType("message/rfc822");
        startActivity(Intent.createChooser(intentEmail, "Choose email client"));
    }
}
