package deve.org.elswordclicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class GameView extends AppCompatActivity {
    SharedPreferences sharedPref;
    ImageView enemy,gs;
    ProgressBar health;
    int max_life = 100;
    int current_life = 100;
    TextView stage, eldollar,dmmg;
    int current_stage = 1;
    long ieldollar = 0;
    int longswoard_price = 500;
    int bigswoard_price = 10000;
    int crit = 0;
    int dollarperstage;
    int giantsword_price = 50000;
    int giantplussword_price = 500000;
    Random r = new Random();
    Handler h = new Handler();
    RelativeLayout rl;
    RelativeLayout.LayoutParams params;
    int dmg = 1;

    //on create function
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
        rl = findViewById(R.id.rl);
        enemy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    onClickEnemy();
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    onClickEnemy();
                }
                return true;
            }
        });
        enemy.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });

    }

    //function for creating the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //function for clicking the options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info) {
           onAppInfo();
        }
        if (id == R.id.shop) {
            onShop();
        }
        return super.onOptionsItemSelected(item);
    }

    //shop function
    @SuppressLint({"NewApi", "SetTextI18n"})
    public void onShop(){
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
        btn3.setText("Giantsword");
        Button btn4 = new Button(GameView.this);
        btn4.setText("Giantplussword");
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
                onBuyGiantSword();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyGiantPlusSword();
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

    //Setting all variables to their saved value on every application start
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
        ieldollar = sharedPref.getLong("el",0);
        eldollar.setText("Eldollar: "+ieldollar);
        dmg = sharedPref.getInt("dmg",1);
        longswoard_price = sharedPref.getInt("lsp",500);
        crit = sharedPref.getInt("critc",0);
        bigswoard_price = sharedPref.getInt("bsp",(10000));
        dmmg.setText("Damage: "+dmg);
        giantsword_price = sharedPref.getInt("gsp",(50000));
        giantplussword_price = sharedPref.getInt("gspp",(500000));
        super.onStart();
    }

    //buying Long Sword function
    @SuppressLint("SetTextI18n")
    public void onBuyLongsword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
         if(ieldollar>=longswoard_price){
             ieldollar -= longswoard_price;
             dmg++;
             dmmg.setText("Damage: "+dmg);
             SharedPreferences.Editor editor = sharedPref.edit();
             editor.putLong("el", ieldollar);
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

    //buying Big Sword function
    @SuppressLint("SetTextI18n")
    public void onBuyBigsword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>bigswoard_price){
            ieldollar -= bigswoard_price;
            dmg += 20;
            dmmg.setText("Damage: "+dmg);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("el", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            bigswoard_price += (10000);
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

    //buying Giant Sword function
    @SuppressLint("SetTextI18n")
    public void onBuyGiantSword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>giantsword_price){
            ieldollar -= giantsword_price;
            dmg += 100;
            dmmg.setText("Damage: "+dmg);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("el", ieldollar);
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
    //buying Giant Sword function
    @SuppressLint("SetTextI18n")
    public void onBuyGiantPlusSword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>giantplussword_price){
            ieldollar -= giantplussword_price;
            dmg += 1000;
            dmmg.setText("Damage: "+dmg);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("el", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            giantplussword_price += 500000;
            editor.putInt("gspp",giantplussword_price);
            editor.putInt("dmg",dmg);
            editor.apply();
        }else{
            final Toast toast =  Toast.makeText(getApplicationContext(),"-You need "+giantplussword_price+" Eldollar-",Toast.LENGTH_SHORT);
            toast.show();
            h.postDelayed(new Runnable() {
                public void run() {
                    toast.cancel();
                }
            }, 600);
        }
    }

    //shacking Enemy function
    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        enemy.startAnimation(shake);
    }

    //Clicking function
    @SuppressLint("SetTextI18n")
    public void onClickEnemy(){
        onShakeImage();
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        dollarperstage= 2*(current_stage/10+dmg);
        int rnd = r.nextInt(100) + 1;
        current_life -= dmg;
        ieldollar += dollarperstage+dmg;
        editor.putLong("el", ieldollar);
        eldollar.setText("Eldollar: "+ieldollar);
        editor.putInt("c",current_life);
        health.setProgress(current_life);
        if(current_life <= 0){
            current_stage++;
            editor.putInt("s",current_stage);
            stage.setText("Stage "+current_stage);
            max_life += 100;
            editor.putInt("l",max_life);
            health.setMax(max_life);
            current_life = max_life;
            health.setProgress(current_life);
        }
        editor.apply();
    }

    //"Information about the App"-Button
    public void onAppInfo(){
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
                    builder.setNegativeButton("Source Code here", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/EmiDevGerman/ElswordClickerApp"));
                    startActivity(intent);
                    }
                });
                AlertDialog diag = builder.create();
                diag.show();
    }

}
