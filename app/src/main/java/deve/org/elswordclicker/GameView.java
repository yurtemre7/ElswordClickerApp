package deve.org.elswordclicker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class GameView extends AppCompatActivity {
    SharedPreferences sharedPref;
    ImageView enemy,gs;
    ProgressBar health;
    int max_life = 100;
    int current_life = 100;
    TextView stage, eldollar,current_life_tv,ccrit,dmmg;
    int current_stage = 1;
    int ieldollar = 0;
    int longswoard_price = 500;
    int bigswoard_price = 10000;
    int crit_price = 10000;
    int crit = 0;
    int dollarperstage;
    int giantsword_price = 50000;
    Random r = new Random();
    int i = 1;
    Button skill1;
    Handler h = new Handler();
    RelativeLayout rl;
    RelativeLayout.LayoutParams params;
    int dmg = 1;
    long lastClickTime = 0;
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);


        dollarperstage=current_stage/10+dmg;
        enemy = findViewById(R.id.enemy);
        health = findViewById(R.id.health);
        stage = findViewById(R.id.stage);
        eldollar = findViewById(R.id.eldollar);
        dmmg = findViewById(R.id.dmmg);
        ccrit = findViewById(R.id.ccrit);


        gs = new ImageView(GameView.this);
        gs.setImageDrawable(getDrawable(R.drawable.garensword));
        gs.setScaleType(ImageView.ScaleType.CENTER_CROP);
        gs.setRotation(135);
        gs.setPadding(500,100,500,100);
        params = new RelativeLayout.LayoutParams(500, 500);
        params.topMargin=100;
        params.leftMargin=500;
        params.rightMargin=500;
        params.bottomMargin=100;
        skill1 = findViewById(R.id.skill1);

        rl = findViewById(R.id.rl);
        current_life_tv = findViewById(R.id.current_life_tv);
        enemy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    onClickEnemy();
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    onClickEnemy();
                }else{
                    onClickEnemy();
                }
                return true;
            }
        });
        enemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    onClickEnemy();
                    return;
                }
                onClickEnemy();
                lastClickTime = SystemClock.elapsedRealtime();

    }
});
        onAutoClick1();
    }
    public void onAutoClick1(){
        onClickEnemy();
        h.postDelayed(new Runnable() {
            public void run() {
                onAutoClick2();
            }
        }, 1);
    }
    public void onAutoClick2(){
        onClickEnemy();
        h.postDelayed(new Runnable() {
            public void run() {
                onAutoClick1();
            }
        }, 1);
    }
    @SuppressLint("SetTextI18n")
    public void onSetup(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        current_life = sharedPref.getInt("c",100);
        health.setProgress(current_life);
        max_life = sharedPref.getInt("l",100);
        health.setMax(max_life);
        current_stage = sharedPref.getInt("s",1);
        stage.setText("Stage "+ current_stage);
        ieldollar = sharedPref.getInt("e",0);
        eldollar.setText("Eldollar: "+ieldollar);
        dmg = sharedPref.getInt("dmg",1);
        longswoard_price = sharedPref.getInt("lsp",500);
        crit = sharedPref.getInt("crit",0);
        crit_price = sharedPref.getInt("cp",1000);
        current_life_tv.setText(current_life+"/"+max_life);
        bigswoard_price = sharedPref.getInt("bsp",(500*15));
        giantsword_price = sharedPref.getInt("gsp",(50000));
        ccrit.setText("Crit: "+crit);
        dmmg.setText("Damage: "+dmg);

    }
    @SuppressLint({"NewApi", "SetTextI18n"})
    public void onShop(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);
        LinearLayout ll = new LinearLayout(GameView.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        builder.setView(ll);
        TextView tw = new TextView(GameView.this);
        tw.setText("Shop");
        tw.setGravity(Gravity.CENTER);
        tw.setTextColor(getColor(R.color.colortv));
        Button btn = new Button(GameView.this);
        btn.setText("Longsword");
        Button btn2 = new Button(GameView.this);
        btn2.setText("Bigsword");
        Button btn3 = new Button(GameView.this);
        btn3.setText("Critical hit");
        Button btn4 = new Button(GameView.this);
        btn4.setText("Giantsword");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyLongsword();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyBigsword();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyCrit();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyGiantSword();
            }
        });
        ll.addView(tw);
        ll.addView(btn);
        ll.addView(btn2);
        ll.addView(btn3);
        ll.addView(btn4);


        AlertDialog diag = builder.create();

        diag.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        max_life = sharedPref.getInt("l",100);
        health.setMax(max_life);
        current_life = sharedPref.getInt("c",100);
        health.setProgress(current_life);
        current_stage = sharedPref.getInt("s",1);
        stage.setText("Stage "+ current_stage);
        ieldollar = sharedPref.getInt("e",0);
        eldollar.setText("Eldollar: "+ieldollar);
        dmg = sharedPref.getInt("dmg",1);
        longswoard_price = sharedPref.getInt("lsp",500);
        current_life_tv.setText(current_life+"/"+max_life);
        crit = sharedPref.getInt("crit",0);
        crit_price = sharedPref.getInt("cp",1000);
        bigswoard_price = sharedPref.getInt("bsp",(500*15));
        ccrit.setText("Crit: "+crit);
        dmmg.setText("Damage: "+dmg);
        giantsword_price = sharedPref.getInt("gsp",(50000));

        if(crit>=51){
            crit=50;
            editor.putInt("crit",crit);
            editor.apply();
        }
        super.onStart();
    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "Clicking in the background...", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @SuppressLint("SetTextI18n")
    public void onBuyLongsword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
         if(ieldollar>=longswoard_price){
             ieldollar -= longswoard_price;
             dmg++;
             dmmg.setText("Damage: "+dmg);
             SharedPreferences.Editor editor = sharedPref.edit();
             editor.putInt("e", ieldollar);
             eldollar.setText("Eldollar: "+ieldollar);
             longswoard_price += 500;
             editor.putInt("lsp",longswoard_price);
             editor.putInt("dmg",dmg);
             editor.apply();

         }else{
            final Toast toast =  Toast.makeText(getApplicationContext(),"-You need "+longswoard_price+" Eldollar-",Toast.LENGTH_SHORT);
            toast.show();
             h.postDelayed(new Runnable() {
                 public void run() {
                    toast.cancel();
                 }
             }, 600);
         }
    }
    @SuppressLint("SetTextI18n")
    public void onBuyBigsword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>bigswoard_price){
            ieldollar -= bigswoard_price;
            dmg += 15;
            dmmg.setText("Damage: "+dmg);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("e", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            bigswoard_price += (500*15);
            editor.putInt("bsp",bigswoard_price);
            editor.putInt("dmg",dmg);
            editor.apply();

        }else{
            final Toast toast =  Toast.makeText(getApplicationContext(),"-You need "+bigswoard_price+" Eldollar-",Toast.LENGTH_SHORT);
            toast.show();
            h.postDelayed(new Runnable() {
                public void run() {
                    toast.cancel();
                }
            }, 600);
        }
    }
    @SuppressLint("SetTextI18n")
    public void onBuyGiantSword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>giantsword_price){
            ieldollar -= giantsword_price;
            dmg += 100;
            dmmg.setText("Damage: "+dmg);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("e", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            giantsword_price += 50000;
            editor.putInt("gsp",giantsword_price);
            editor.putInt("dmg",dmg);
            editor.apply();

        }else{
            final Toast toast =  Toast.makeText(getApplicationContext(),"-You need "+giantsword_price+" Eldollar-",Toast.LENGTH_SHORT);
            toast.show();
            h.postDelayed(new Runnable() {
                public void run() {
                    toast.cancel();
                }
            }, 600);
        }
    }
    @SuppressLint("SetTextI18n")
    public void onBuyCrit(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>=crit_price && crit <= 49){
            ieldollar -= crit_price;
            crit += 1;
            ccrit.setText("Crit: "+crit);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("e", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            crit_price *= 2;
            editor.putInt("cp",crit_price);
            editor.putInt("crit",crit);
            editor.apply();

        }else{
            if(crit <= 49){
                final Toast toast =  Toast.makeText(getApplicationContext(),"-You need "+crit_price+" Eldollar-",Toast.LENGTH_SHORT);
                toast.show();
                h.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, 600);
            }else{
                final Toast toast =  Toast.makeText(getApplicationContext(),"You already reached the max!\n",Toast.LENGTH_SHORT);
                toast.show();
                h.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, 600);
            }

        }
    }
    public void onSkill1(View v){
         sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
         final SharedPreferences.Editor editor = sharedPref.edit();
         if(i == 1){
             rl.addView(gs,params);
             i=0;
             skill1.setVisibility(View.INVISIBLE);
             gs.setVisibility(View.VISIBLE);
             final float bottomOfScreen = getResources().getDisplayMetrics()
                     .heightPixels - (enemy.getHeight());
             gs.animate()
                     .translationY(bottomOfScreen)
                     .setInterpolator(new AccelerateInterpolator())
                     .setDuration(1000);
             h.postDelayed(new Runnable() {
                 @SuppressLint("SetTextI18n")
                 public void run() {
                     gs.animate()
                             .translationY(((-1)*bottomOfScreen)/12)
                             .setDuration(100);
                     rl.removeView(gs);
                     current_life -= dmg*50;
                     ieldollar += dmg*50;
                     editor.putInt("c",current_life);
                     health.setProgress(current_life);
                     current_life_tv.setText(current_life+"/"+max_life);
                     editor.putInt("e", ieldollar);
                     eldollar.setText("Eldollar: "+ieldollar);
                     if(current_life <= 0){
                         current_stage++;
                         onStageTest();
                         editor.putInt("s",current_stage);
                         stage.setText("Stage "+current_stage);

                         max_life += 100;
                         editor.putInt("l",max_life);
                         health.setMax(max_life);

                         current_life = max_life;
                         health.setProgress(current_life);

                         current_life_tv.setText(max_life+"/"+max_life);
                         editor.apply();

                     }
                     h.postDelayed(new Runnable() {
                         public void run() {
                             rl.removeView(gs);
                             skill1.setVisibility(View.VISIBLE);
                             i = 1;
                         }
                     }, 60000);
                 }
             }, 1500);

         }
     }

    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        enemy.startAnimation(shake);
    }

@SuppressLint("SetTextI18n")
public void onStageTest(){
    sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();

    if(current_stage==88){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);

        builder.setTitle("CONGRATULATIONS TO 88 STAGES");

        builder.setMessage("You'll get a bonus of 88% of your current Eldollar!");

        AlertDialog diag = builder.create();

        diag.show();

        ieldollar *= 0.88;
        editor.putInt("e", ieldollar);
        eldollar.setText("Eldollar: "+ieldollar);
    }
    if(current_stage==333){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);

        builder.setTitle("CONGRATULATIONS TO 333 STAGES");

        builder.setMessage("You'll get a bonus of 333% of your current Eldollar!");

        AlertDialog diag = builder.create();

        diag.show();

        ieldollar *= 3.333;
        editor.putInt("e", ieldollar);
        eldollar.setText("Eldollar: "+ieldollar);
    }
    if(current_stage==1337){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);

        builder.setTitle("CONGRATULATIONS TO 1337 STAGES");

        builder.setMessage("You'll get a bonus of 1337% of your current Eldollar!");

        AlertDialog diag = builder.create();

        diag.show();

        ieldollar *= 13.37;
        editor.putInt("e", ieldollar);
        eldollar.setText("Eldollar: "+ieldollar);
    }
    if(current_stage==2018){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);

        builder.setTitle("CONGRATULATIONS TO 2018 STAGES");

        builder.setMessage("You'll get a bonus of 2018% of your current Eldollar!");

        AlertDialog diag = builder.create();

        diag.show();

        ieldollar *= 20.18;
        editor.putInt("e", ieldollar);
        eldollar.setText("Eldollar: "+ieldollar);
    }
    if(current_stage==4043){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);

        builder.setTitle("CONGRATULATIONS TO 4043 STAGES");

        builder.setMessage("You'll get a bonus of 4043% of your current Eldollar!");

        AlertDialog diag = builder.create();

        diag.show();

        ieldollar *= 40.43;
        editor.putInt("e", ieldollar);
        eldollar.setText("Eldollar: "+ieldollar);
     }
     editor.apply();
    }

    @SuppressLint("SetTextI18n")
    public void onClickEnemy(){
        onShakeImage();
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        dollarperstage=current_stage+dmg;
        int rnd = r.nextInt(101 - 1) + 1;
        if(rnd<=crit){
            current_life -= dmg*5;
            ieldollar += (dmg + dollarperstage)*5;
            editor.putInt("e", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            editor.apply();
        }else{
            current_life -= dmg;
            ieldollar += dollarperstage+dmg;
            editor.putInt("e", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            editor.apply();
        }

        editor.putInt("c",current_life);
        health.setProgress(current_life);

        current_life_tv.setText(current_life+"/"+max_life);
        if(current_life <= 0){
            current_stage++;
            onStageTest();
            editor.putInt("s",current_stage);
            stage.setText("Stage "+current_stage);

            max_life += 100;
            editor.putInt("l",max_life);
            health.setMax(max_life);

            current_life = max_life;
            health.setProgress(current_life);

            current_life_tv.setText(max_life+"/"+max_life);
            editor.apply();

        }

    }

    public void onAppInfo(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);

        builder.setTitle("App info");

        builder.setMessage("This app/game was made by Emre.\nYou can tell me your feedback and if You have found a bug just tell it me in the group with a screenshot and a nice describtion of the bug.\n\nalpha version 1.0-1");
        builder.setPositiveButton("Join my Telegram group!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://t.me/joinchat/Fbev6FHu8EhWMIA1E_2-KA"));
                        startActivity(intent);
                    }
                });

                AlertDialog diag = builder.create();

        diag.show();
    }

}
