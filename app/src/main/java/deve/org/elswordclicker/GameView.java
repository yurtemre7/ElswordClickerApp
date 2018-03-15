//package where the application is stored
package deve.org.elswordclicker;
//imports for the functions and methods and variables to work
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
//class
public class GameView extends AppCompatActivity {
    //for saving variables
    SharedPreferences sharedPref;
    //all the images used
    ImageView enemy,magma,buggiebomb,milch;
    //the healthbar
    ProgressBar health;
    //all the buttons used
    Button btn,btn2,btn3,btn4,btn5,btn6;
    //all the textviews used
    TextView stage, eldollar,dmmg,lifee;
    //for having delays
    Handler h = new Handler();
    //the layout
    LinearLayout rl;
    //method to get random integer
    Random rn = new Random();
    //Runnable function
    Runnable runnable;
    //all saved (numbers xD)
    int delay = (5)*1000;
    int max_life = 100;
    int current_life = 100;
    int current_stage = 1;
    long ieldollar = 0;
    long longswoard_price = 500;
    long bigswoard_price = 10000;
    long dollarperstage;
    long giantsword_price = 50000;
    long giantplussword_price = 500000;
    long giantplusplussword_price = 5000000;
    long giantplusplusplussword_price = 50000000;
    long dmg = 1;
    int intBoss = 1;

    //on create function
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameview_main);
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        //telling the app where anything is
        enemy = findViewById(R.id.enemy);
        magma = findViewById(R.id.magma);
        buggiebomb = findViewById(R.id.buggiebomb);
        milch = findViewById(R.id.milch);
        health = findViewById(R.id.health);
        stage = findViewById(R.id.stage);
        eldollar = findViewById(R.id.eldollar);
        dmmg = findViewById(R.id.dmmg);
        lifee = findViewById(R.id.lifee);
        rl = findViewById(R.id.rl);
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //methods to let this stuff being clickable
        enemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEnemy(0);
            }
        });
        enemy.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                intBoss++;
                switch (intBoss){
                    case 1: enemy.setImageResource(R.drawable.elsword1);
                        break;
                    case 2: enemy.setImageResource(R.drawable.elsword2);
                        break;
                    case 3: enemy.setImageResource(R.drawable.elsword3);
                        break;
                    case 4: enemy.setImageResource(R.drawable.elsword4);
                            intBoss = 0;
                        break;
                }
                sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("boss",intBoss);
                editor.apply();
                return true;
            }
        });
        magma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                magma.setVisibility(View.INVISIBLE);
                onClickEnemy(dmg*20);
                h.postDelayed(new Runnable() {
                    public void run() {
                        magma.setVisibility(View.VISIBLE);
                    }
                }, (1000)*30);
            }
        });
        buggiebomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buggiebomb.setVisibility(View.INVISIBLE);
                onClickEnemy(dmg*40);
                h.postDelayed(new Runnable() {
                    public void run() {
                        buggiebomb.setVisibility(View.VISIBLE);
                    }
                }, (1000)*60);
            }
        });
        milch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milch.setVisibility(View.INVISIBLE);
                onClickEnemy(dmg*60);
                h.postDelayed(new Runnable() {
                    public void run() {
                        milch.setVisibility(View.VISIBLE);
                    }
                }, (1000)*90);
            }
        });
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
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyGiantPlusPlusSword();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyGiantPlusPlusPlusSword();
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
        //when you click info
        if (id == R.id.info) {
           onAppInfo();
        }
        //when you click settings
        if (id == R.id.settings){
            startActivity(new Intent(GameView.this, Settings.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //Setting all variables to their saved value on every application start
    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        max_life = sharedPref.getInt("l",100);
        intBoss = sharedPref.getInt("boss",1);
        switch (intBoss){
            case 1: enemy.setImageResource(R.drawable.elsword1);
                break;
            case 2: enemy.setImageResource(R.drawable.elsword2);
                break;
            case 3: enemy.setImageResource(R.drawable.elsword3);
                break;
            case 4: enemy.setImageResource(R.drawable.elsword4);
                intBoss = 1;
                break;
        }
        health.setMax(max_life);
        current_life = sharedPref.getInt("c",100);
        health.setProgress(current_life);
        current_stage = sharedPref.getInt("s",1);
        stage.setText("Stage "+ current_stage);
        ieldollar = sharedPref.getLong("el",0);
        eldollar.setText("Eldollar: "+ieldollar);
        dmg = sharedPref.getLong("dmgl",1);
        longswoard_price = sharedPref.getLong("lspl",500);
        bigswoard_price = sharedPref.getLong("bspl",(10000));
        dmmg.setText("Damage: "+dmg);
        giantsword_price = sharedPref.getLong("gspl",(50000));
        giantplussword_price = sharedPref.getLong("gsppl",(500000));
        giantplusplussword_price = sharedPref.getLong("gspppl",(5000000));
        giantplusplusplussword_price = sharedPref.getLong("gsppppl",(50000000));
        lifee.setText(current_life+"/"+max_life);
        btn.setText("Longsword\n"+longswoard_price +"ED\n" + "+1 DMG");
        btn2.setText("Bigsword\n"+bigswoard_price +"ED\n"+ "+20 DMG");
        btn3.setText("Giantsword\n"+giantsword_price+ "ED\n"+ "+100 DMG");
        btn4.setText("Giant+sword\n"+giantplussword_price+ "ED\n"+ "+1.000 DMG");
        btn5.setText("Giant++sword\n"+giantplusplussword_price+ "ED\n"+ "+10.000 DMG");
        btn6.setText("Giant+++sword\n"+giantplusplusplussword_price+ "ED\n"+ "+100.000 DMG");
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
             editor.putLong("lspl",longswoard_price);
             editor.putLong("dmgl",dmg);
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
            editor.putLong("bspl",bigswoard_price);
            editor.putLong("dmgl",dmg);
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
            editor.putLong("gspl",giantsword_price);
            editor.putLong("dmgl",dmg);
            editor.apply();
        }
    }
    //buying Giant Plus Sword function
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
            btn4.setText("Giant+sword\n"+giantplussword_price+"ED\n" + "+1.000 DMG");
            editor.putLong("gsppl",giantplussword_price);
            editor.putLong("dmgl",dmg);
            editor.apply();
        }
    }
    //buying Giant Plus Plus Sword function
    @SuppressLint("SetTextI18n")
    public void onBuyGiantPlusPlusSword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>giantplusplussword_price){
            ieldollar -= giantplusplussword_price;
            dmg += 10000;
            dmmg.setText("Damage: "+dmg);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("el", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            giantplusplussword_price += 5000000;
            btn5.setText("Giant++sword\n"+giantplusplussword_price+"ED\n" + "+10.000 DMG");
            editor.putLong("gspppl",giantplusplussword_price);
            editor.putLong("dmgl",dmg);
            editor.apply();
        }
    }
    //buying Giant Plus Plus Plus Sword function
    @SuppressLint("SetTextI18n")
    public void onBuyGiantPlusPlusPlusSword(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>giantplusplusplussword_price){
            ieldollar -= giantplusplusplussword_price;
            dmg += 100000;
            dmmg.setText("Damage: "+dmg);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("el", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            giantplusplusplussword_price += 50000000;
            btn6.setText("Giant+++sword\n"+giantplusplusplussword_price+"ED\n" + "+100.000 DMG");
            editor.putLong("gsppppl",giantplusplusplussword_price);
            editor.putLong("dmgl",dmg);
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
    public void onClickEnemy(long magmadmg) {
        onShakeImage();
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        dollarperstage = 2 * (current_stage + dmg + magmadmg);
        if(rn.nextInt(1000-1)+1 == 1){
            current_life -= 10*(dmg + magmadmg);
            ieldollar += 10*(dollarperstage + (dmg+magmadmg));
            Toast.makeText(this, "Crit: "+10*(dmg + magmadmg), Toast.LENGTH_SHORT).show();
        }else{
            current_life -= (dmg + magmadmg);
            ieldollar += dollarperstage + (dmg+magmadmg);
        }
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
    public void onAutoClickEnemy(){
        onShakeImage();
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        dollarperstage = (2 * (current_stage + dmg));
        current_life -= (dmg);
        ieldollar += dollarperstage + (dmg);
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
                    builder.setMessage("This app/game was made by Emre.\nYou can tell me your feedback and if You have found a bug just tell it me in the group with a screenshot and a nice describtion of the bug.\n\nalpha version 1.2-0");
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



    @Override
    protected void onResume() {
        h.postDelayed(new Runnable() {
            public void run() {
                onAutoClickEnemy();
                runnable=this;
                h.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        h.removeCallbacks(runnable);
        super.onPause();
    }

}
