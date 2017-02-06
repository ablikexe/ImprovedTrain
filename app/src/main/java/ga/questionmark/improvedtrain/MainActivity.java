package ga.questionmark.improvedtrain;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
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
    CountDownTimer timer = null;

    void playAgainOrExit(String msg) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(msg)
                .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current = 0;
        TableLayout ll = (TableLayout) findViewById(R.id.table);

        timer = new CountDownTimer(60000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((TextView)findViewById(R.id.timer)).setText(String.valueOf(millisUntilFinished/1000) + ":" + String.format("%02d", (millisUntilFinished%1000)/10));
            }

            @Override
            public void onFinish() {
                playAgainOrExit("Time's up!");

            }
        };
        timer.start();
        int numbers[][] = Rand.makeMatrix(H, W);


        int k = 0;
        for (int i = 0; i < H; ++i) {
            for (int j = 0; j < W; ++j) {
                sorted[k] = numbers[i][j];
                ++k;
            }
        }

        Arrays.sort(sorted);

        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
                c[numbers[i][j]] = (pattern[i].charAt(j) == 'x');

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        int cell_size = Math.min((int)(0.8*width)/W, (int)(0.8*height)/H)-2;
        int table_width = (cell_size+2)*W + (int)(0.1 * Math.min(width, height));
        int table_height = (cell_size+2)*H + (int)(0.1 * Math.min(width, height));
        ll.setMinimumHeight(table_height);

        for (int i = 0; i < H; i++) {

            TableRow row = new TableRow(this);

            TableRow.LayoutParams lp = new TableRow.LayoutParams();
            lp.height = cell_size;
            lp.width = table_width;
            // lp.topMargin = 10;
            // lp.bottomMargin = 10;
            row.setLayoutParams(lp);
            // row.setBackgroundColor(0x0ff194759);
            // row.setPadding(0, 1, 0, 1);
            row.setGravity(Gravity.CENTER);

            for (int j = 0; j < W; j++) {
                TextView tv = new TextView(this);
                tv.setTextSize(35);
                tv.setTextColor(0xffd8f2f0);
                tv.setWidth(cell_size);
                tv.setHeight(cell_size);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundColor(0xff296b73);
                TableRow.LayoutParams lp2 = new TableRow.LayoutParams();
                lp2.rightMargin = 1;
                lp2.leftMargin = 1;
                lp2.topMargin = 1;
                lp2.bottomMargin = 1;
                tv.setLayoutParams(lp2);
                // tv.setPadding(10, 10, 10, 10);
                tv.setText(String.valueOf(numbers[i][j]));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = (String)((TextView) v).getText();
                        Log.e("", number + " " + String.valueOf(sorted[current]));
                        if (number.equals("") || number.equals(":)")) {
                            // ((TextView) v).setText(":)");
                        } else if (number.equals(":(") || Integer.valueOf(number) != sorted[current]) {
                            ((TextView) v).setText(":(");
                            playAgainOrExit("You lost!");
                        } else {
                            // ((TextView) v).setText(c[Integer.valueOf(number)] ? ":)" : "");
                            ((TextView) v).setText("");
                            ((TextView) v).setBackgroundColor(0x0ff194759);
                            // ((TextView) v).setTextSize(15);
                            current++;
                            if (current >= W*H) playAgainOrExit("You won!");
                        }
                    }
                });
                row.addView(tv);
            }
            ll.addView(row, i);
        }
        ll.setPadding(0, 20, 0, 20);
    }
}
