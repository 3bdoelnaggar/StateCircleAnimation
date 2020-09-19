package elnaggar.statecircleanimation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean firstTime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final StateCircleAnimation stateCircleAnimation = findViewById(R.id.stateCircleView);
        stateCircleAnimation.setViews(getStateViews(), (TextView) findViewById(R.id.tv_stateName));
        final EditText stateIDEditText=findViewById(R.id.et_stateId);
        stateIDEditText.setText("1");
        Button startAnimationButton=findViewById(R.id.button_startAnimation);
        startAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int stateId = Integer.parseInt(stateIDEditText.getText().toString());
                if(stateId>5||stateId<1) {
                    stateId=1;
                }
                stateCircleAnimation.setStateId(stateId);
                if (firstTime) {
                    stateCircleAnimation.loadNormalAnimation();
                    firstTime = false;
                } else {
                    stateCircleAnimation.loadReverseAnimation();
                }

            }
        });

    }

    private ArrayList<ImageView> getStateViews() {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        ImageView imageView1 = findViewById(R.id.iv1);
        ImageView imageView2 = findViewById(R.id.iv2);
        ImageView imageView3 = findViewById(R.id.iv3);
        ImageView imageView4 = findViewById(R.id.iv4);
        ImageView imageView5 = findViewById(R.id.iv5);
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);
        imageViews.add(imageView5);
        return imageViews;

    }
}
