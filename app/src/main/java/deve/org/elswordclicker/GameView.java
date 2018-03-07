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

public class GameView extends AppCompatActivity {
    SharedPreferences sharedPref;
    ImageView enemy,magma;
    ProgressBar health;
    Button btn,btn2,btn3,btn4;
    int max_life = 100;
    int current_life = 100;
    TextView stage, eldollar,dmmg,lifee;
    int current_stage = 1;
    long ieldollar = 0;
    int longswoard_price = 500;
    int bigswoard_price = 10000;
    int dollarperstage;
    int giantsword_price = 50000;
    int giantplussword_price = 500000;
    Handler h = new Handler();
    RelativeLayout rl;
    int dmg = 1;

    //on create function
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameview_main);
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        dollarperstage=current_stage/10+dmg;
        enemy = findViewById(R.id.enemy);
        magma = findViewById(R.id.magma);
        health = findViewById(R.id.health);
        stage = findViewById(R.id.stage);
        eldollar = findViewById(R.id.eldollar);
        dmmg = findViewById(R.id.dmmg);
        lifee = findViewById(R.id.lifee);

        rl = findViewById(R.id.rl);
        enemy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    onClickEnemy(0);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    onClickEnemy(0);
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
        magma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                magma.setVisibility(View.INVISIBLE);
                onClickEnemy(dmg*10);
                h.postDelayed(new Runnable() {
                    public void run() {
                        magma.setVisibility(View.VISIBLE);
                    }
                }, (1000)*30);
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
        if (id == R.id.settings){
            startActivity(new Intent(GameView.this, Settings.class));
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
        btn = new Button(GameView.this);
        btn.setText("Longsword\n"+longswoard_price +"ED\n" + "+1 DMG");
        btn2 = new Button(GameView.this);
        btn2.setText("Bigsword\n"+bigswoard_price +"ED\n"+ "+20 DMG");
        btn3 = new Button(GameView.this);
        btn3.setText("Giantsword\n"+giantsword_price+ "ED\n"+ "+100 DMG");
        btn4 = new Button(GameView.this);
        btn4.setText("Giantplussword\n"+giantplussword_price+ "ED\n"+ "+1000 DMG");
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
        btn.setBackgroundColor(getResources().getColor(R.color.grey));
        btn2.setBackgroundColor(getResources().getColor(R.color.grey));
        btn3.setBackgroundColor(getResources().getColor(R.color.grey));
        btn4.setBackgroundColor(getResources().getColor(R.color.grey));
        ll.addView(btn);
        ll.addView(btn2);
        ll.addView(btn3);
        ll.addView(btn4);
        AlertDialog diag = builder.create();
        diag.getWindow().setBackgroundDrawableResource(R.color.grey);
        diag.show();
    }

    //Setting all variables to their saved value on every application start
    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
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
        bigswoard_price = sharedPref.getInt("bsp",(10000));
        dmmg.setText("Damage: "+dmg);
        giantsword_price = sharedPref.getInt("gsp",(50000));
        giantplussword_price = sharedPref.getInt("gspp",(500000));
        lifee.setText(current_life+"/"+max_life);
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
             btn.setText("Longsword\n"+longswoard_price +"ED\n" + "+1 DMG");
             editor.putInt("lsp",longswoard_price);
             editor.putInt("dmg",dmg);
             editor.apply();
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
            btn2.setText("Bigsword\n"+bigswoard_price +"ED\n" + "+20 DMG");
            editor.putInt("bsp",bigswoard_price);
            editor.putInt("dmg",dmg);
            editor.apply();
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
            btn3.setText("Giantsword\n"+giantsword_price+ "ED\n" + "+100 DMG");
            editor.putInt("gsp",giantsword_price);
            editor.putInt("dmg",dmg);
            editor.apply();
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
            btn4.setText("Giantplussword\n"+giantplussword_price+"ED\n" + "+1.000 DMG");
            editor.putInt("gspp",giantplussword_price);
            editor.putInt("dmg",dmg);
            editor.apply();
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
    public void onClickEnemy(int magmadmg) {
        onShakeImage();
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        dollarperstage = 2 * (current_stage / 10 + dmg + magmadmg);
        current_life -= (dmg + magmadmg);
        ieldollar += dollarperstage + dmg;
        editor.putLong("el", ieldollar);
        eldollar.setText("Eldollar: " + ieldollar);
        editor.putInt("c", current_life);
        health.setProgress(current_life);
        while (current_life < 0) {
            current_stage++;
            editor.putInt("s", current_stage);
            stage.setText("Stage " + current_stage);
            max_life += 100;
            editor.putInt("l", max_life);
            health.setMax(max_life);
            current_life = current_life + max_life;
            health.setProgress(current_life);
            lifee.setText(current_life + "/" + max_life);
            editor.putInt("c", current_life);
        }
        lifee.setText(current_life + "/" + max_life);
        editor.apply();
    }

    //stop function
    @Override
    protected void onStop() {
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("c",current_life);
        editor.apply();
        super.onStop();
    }

    //"Information about the App"-Button
    public void onAppInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);
                    builder.setTitle("App info");
                    builder.setMessage("This app/game was made by Emre.\nYou can tell me your feedback and if You have found a bug just tell it me in the group with a screenshot and a nice describtion of the bug.\n\nalpha version 1.0-9");
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
                diag.getWindow().setBackgroundDrawableResource(R.color.grey);
                diag.show();
    }

}
