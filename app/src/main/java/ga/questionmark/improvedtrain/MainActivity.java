package ga.questionmark.improvedtrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static int current = 1;
    final int W = 6, H = 6;

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
        current = 1;
        TableLayout ll = (TableLayout) findViewById(R.id.table);

        int numbers[][] = new int[W][H];
        for (int i = 0; i < W; i++)
            for (int j = 0; j < H; j++)
                numbers[i][j] = i*H+j+1;

        Random r = new Random();

        for (int i = 0; i < W*H; i++) {
            int ind = r.nextInt(W*H-i);
            int tmp = numbers[(W*H-i-1)/H][(W*H-i-1)%H];
            numbers[(W*H-i-1)/H][(W*H-i-1)%H] = numbers[ind/H][ind%H];
            numbers[ind/H][ind%H] = tmp;
        }

        for (int i = 0; i < W; i++)
            for (int j = 0; j < H; j++)
                c[numbers[i][j]] = (pattern[i].charAt(j) == 'x');

        for (int i = 0; i <W; i++) {

            TableRow row= new TableRow(this);

            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            for (int j = 0; j < H; j++) {
                TextView tv = new TextView(this);
                tv.setTextSize(55);
                tv.setWidth(95);
                tv.setText(String.valueOf(numbers[i][j]));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = (String)((TextView) v).getText();
                        Log.e("", number + " " + String.valueOf(current));
                        if (number.equals("") || number.equals(":)")) {
                            // ((TextView) v).setText(":)");
                        } else
                        if (Integer.valueOf(number) == current) {
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
