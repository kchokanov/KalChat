package xyz.chokanov.kalchat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mukesh.image_processing.ImageProcessor;

import java.util.Random;

public class SettingsActivity extends AppCompatActivity {
    private EditText mTextUserName;
    private Button mButtonChangeAvatar, mButtonSetUserName;
    private ImageView mImageAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mTextUserName = findViewById(R.id.txtName);
        mImageAvatar = findViewById(R.id.imgAvatarList);
        mButtonSetUserName = findViewById(R.id.btnSetName);
        mButtonChangeAvatar = findViewById(R.id.btnRandAvatar);
        final User user = new User();
        mTextUserName.setText(user.getUsername());

        mButtonChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap avatar = user.getAvatarImage();
                ImageProcessor imageProcessor = new ImageProcessor();
                avatar = imageProcessor.doColorFilter(avatar, new Random().nextInt(256),
                        new Random().nextInt(256),new Random().nextInt(256));
                mImageAvatar.setImageBitmap(avatar);
            }
        });

        mButtonSetUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mTextUserName.getText().toString().toLowerCase();
                if (name.length() >= 30){
                    Toast.makeText(getApplicationContext(), "Name must be 29 characters or less",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!name.contains("kal")){
                    Toast.makeText(getApplicationContext(), "Name must contain the word Kal",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    user.setUsername(mTextUserName.getText().toString());
                    Toast.makeText(getApplicationContext(), "Name Changed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
