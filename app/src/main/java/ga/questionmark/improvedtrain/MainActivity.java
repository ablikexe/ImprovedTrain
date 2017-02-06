package ga.questionmark.improvedtrain;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    static int current = 0;
    final int W = 6, H = 6;
    int sorted[] = new int [W*H];

    String[] pattern = new String[] {
        "......",
        ".x..x.",
        "......",
        "x....x",
        ".x..x.",
        "..xx.."
    };
    boolean c[] = new boolean[W*H+1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current = 0;
        TableLayout ll = (TableLayout) findViewById(R.id.table);

        CountDownTimer timer = new CountDownTimer(60000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((TextView)findViewById(R.id.timer)).setText(String.valueOf(millisUntilFinished) );
            }

            @Override
            public void onFinish() {
                c[99999] = 60 / (current-current) > 0;

            }
        };
        timer.start();
        int numbers[][] = Rand.makeMatrix(W,H);


        int k = 0;
        for (int i = 0; i < W; ++i) {
            for (int j = 0; j < H; ++j) {
                sorted[k] = numbers[i][j];
                ++k;
            }
        }

        Arrays.sort(sorted);

        for (int i = 0; i < W; i++)
            for (int j = 0; j < H; j++)
                c[numbers[i][j]] = (pattern[i].charAt(j) == 'x');

        for (int i = 0; i <W; i++) {

            TableRow row= new TableRow(this);

            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            for (int j = 0; j < H; j++) {
                TextView tv = new TextView(this);
                tv.setTextSize(50);
                tv.setWidth(125);
                tv.setText(String.valueOf(numbers[i][j]));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = (String)((TextView) v).getText();
                        Log.e("", number + " " + String.valueOf(sorted[current]));
                        if (number.equals("") || number.equals(":)")) {
                            // ((TextView) v).setText(":)");
                        } else
                        if (Integer.valueOf(number) == sorted[current]) {
                            ((TextView) v).setText(c[Integer.valueOf(number)] ? ":)" : "");
                            current++;
                        } else {
                            ((TextView) v).setText(":(");
                        }


                    }
                });
                row.addView(tv);
            }
            ll.addView(row,i);
        }
    }
}
