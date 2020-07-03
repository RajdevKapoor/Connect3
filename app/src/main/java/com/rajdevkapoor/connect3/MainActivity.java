package com.rajdevkapoor.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int activePlayer=0; // 0->yellow, 1->red

    int[] game ={2,2,2,2,2,2,2,2,2};
    int[][] win ={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int won=2;
    Context context;
    Dialog dialog;
    Button reset;
    boolean active=true;
    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int current = Integer.parseInt(counter.getTag().toString());


        if(game[current]==2 && active){
            game[current]=activePlayer;
            counter.setTranslationY(-1000f);
            if(activePlayer==0){
                counter.setImageResource(R.drawable.yellow);
                activePlayer=1;
            }
            else{
                counter.setImageResource(R.drawable.red);
                activePlayer=0;
            }
            counter.animate().translationYBy(1000f).rotation(3600).setDuration(600);

            for(int[] w:win){

                if(game[w[0]]==game[w[1]] &&
                        game[w[1]]==game[w[2]] &&
                        game[w[0]]!=2 ){
                    won=game[w[0]];
                    active=false;
                    showDialog();

                }
            }

        }

    }

    public void showDialog() {
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog);
        reset=(Button) dialog.findViewById(R.id.reset);
        ImageView win = (ImageView) dialog.findViewById(R.id.winner);
        if (won == 0) {
            win.setImageResource(R.drawable.yellow);
        } else {
            win.setImageResource(R.drawable.red);
        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void playAgain() {
        active=true;
        activePlayer=0;
        GridLayout gridLayout= (GridLayout) findViewById(R.id.grid);
        for( int i =0;i<game.length;i++){
            game[i]=2;
        }
        for(int i=0;i<gridLayout.getChildCount();i++){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
        dialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog= new Dialog(this);

    }
}