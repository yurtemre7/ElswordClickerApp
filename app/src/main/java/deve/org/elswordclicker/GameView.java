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
    Button btn,btn2;
    //all the textviews used
    TextView stage, eldollar,dmmg,lifee,acs;
    //for having delays
    Handler h = new Handler();
    //the layout
    LinearLayout rl;
    //method to get random integer
    Random rn = new Random();
    //Runnable function
    Runnable runnable;
    //all saved (numbers xD)


    int delay = 10000;
    int autoclick = 0;

    int max_life = 100;
    int current_life = 100;
    int current_stage = 1;

    long ieldollar = 0;

    long longswoard_price = 100;
    int autoclick_price = 15000;



    long dollarperstage;


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
        acs = findViewById(R.id.acs);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //methods to let this stuff being clickable
        enemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEnemy(0);
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
                }, (1000)*45);
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
                }, (1000)*90);
            }
        });
        milch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milch.setVisibility(View.INVISIBLE);
                onClickEnemy(dmg*80);
                h.postDelayed(new Runnable() {
                    public void run() {
                        milch.setVisibility(View.VISIBLE);
                    }
                }, (1000)*180);
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
                onBuyAutoClick();
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
        return super.onOptionsItemSelected(item);
    }

    //Setting all variables to their saved value on every application start
    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        max_life = sharedPref.getInt("mlife",100);
        intBoss = sharedPref.getInt("boss",1);
        enemy.setImageResource(R.drawable.e1);
        health.setMax(max_life);
        current_life = sharedPref.getInt("clife",100);
        health.setProgress(current_life);
        current_stage = sharedPref.getInt("stage",1);
        stage.setText("Stage "+ current_stage);
        ieldollar = sharedPref.getLong("eldollar",0);
        eldollar.setText("Eldollar: "+ieldollar);
        dmg = sharedPref.getLong("damage",1);
        longswoard_price = sharedPref.getLong("longswordp",100);
        dmmg.setText("Damage: "+dmg);
        lifee.setText(current_life+"/"+max_life);
        btn.setText("Longsword\n"+longswoard_price +"ED\n" + "+1 DMG");
        autoclick_price = sharedPref.getInt("autoclickp",15000);
        btn2.setText("Autoclick\n"+autoclick_price+"ED\n"+"-100ms");
        delay = sharedPref.getInt("delay",5000);
        acs.setText("dmg/ms: "+dmg+"/"+delay);
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
             editor.putLong("eldollar", ieldollar);
             eldollar.setText("Eldollar: "+ieldollar);
             longswoard_price += 100;
             btn.setText("Longsword\n"+longswoard_price +"ED\n" + "+1 DMG");
             acs.setText("dmg/ms: "+dmg+"/"+delay);
             editor.putLong("longswordp",longswoard_price);
             editor.putLong("damage",dmg);
             editor.apply();
         }else{
             Toast.makeText(this, "Not enaugh money.", Toast.LENGTH_SHORT).show();
         }
    }
    @SuppressLint("SetTextI18n")
    public void onBuyAutoClick(){
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        if(ieldollar>=autoclick_price){
            ieldollar -= autoclick_price;
            autoclick++;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("ac",autoclick);
            editor.putLong("eldollar", ieldollar);
            eldollar.setText("Eldollar: "+ieldollar);
            delay = delay - 100;
            editor.putInt("delay",delay);
            autoclick_price += 15000;
            editor.putInt("autoclickp", autoclick_price);
            btn2.setText("Autoclick\n"+autoclick_price +"ED\n" + "-100ms");
            acs.setText("dmg/ms: "+dmg+"/"+delay);
            editor.apply();

        }else{
            Toast.makeText(this, "Not enaugh money.", Toast.LENGTH_SHORT).show();
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
            current_life -= 100*(dmg + magmadmg);
            ieldollar += 100*(dollarperstage + (dmg+magmadmg));
            Toast.makeText(this, "Crit: "+10*(dmg + magmadmg), Toast.LENGTH_LONG).show();
        }else{
            current_life -= (dmg + magmadmg);
            ieldollar += dollarperstage + (dmg+magmadmg);
        }
        editor.putLong("eldollar", ieldollar);
        eldollar.setText("Eldollar: " + ieldollar);
        editor.putInt("clife", current_life);
        health.setProgress(current_life);
        while (current_life < 0) {
            current_stage++;
            intBoss++;
            editor.putInt("stage", current_stage);
            stage.setText("Stage " + current_stage);
            max_life += 100;
            editor.putInt("mlife", max_life);
            health.setMax(max_life);
            current_life = current_life + max_life;
            health.setProgress(current_life);
            lifee.setText(current_life + "/" + max_life);
            switch (intBoss){
                case 1: enemy.setImageResource(R.drawable.e1);
                    break;
                case 6: enemy.setImageResource(R.drawable.e2);
                    break;
                case 11: enemy.setImageResource(R.drawable.e3);
                    break;
                case 16: enemy.setImageResource(R.drawable.e4);
                    intBoss = 1;
                    break;
            }

            editor.putInt("clife", current_life);
        }
        lifee.setText(current_life + "/" + max_life);
        editor.apply();
    }
    @SuppressLint("SetTextI18n")
    public void onAutoClickEnemy(){
        onShakeImage();
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        dollarperstage = (2 * (current_stage + dmg));
        current_life -= (dmg);
        ieldollar += dollarperstage + (dmg);
        editor.putLong("eldollar", ieldollar);
        eldollar.setText("Eldollar: " + ieldollar);
        editor.putInt("c", current_life);
        health.setProgress(current_life);
        while (current_life < 0) {
            current_stage++;
            intBoss++;
            editor.putInt("stage", current_stage);
            stage.setText("Stage " + current_stage);
            max_life += 100;
            editor.putInt("mlife", max_life);
            health.setMax(max_life);
            current_life = current_life + max_life;
            health.setProgress(current_life);
            lifee.setText(current_life + "/" + max_life);

            switch (intBoss){
                case 1: enemy.setImageResource(R.drawable.e1);
                    break;
                case 6: enemy.setImageResource(R.drawable.e2);
                    break;
                case 11: enemy.setImageResource(R.drawable.e3);
                    break;
                case 16: enemy.setImageResource(R.drawable.e4);
                    intBoss = 1;
                    break;
            }

            editor.putInt("clife", current_life);
        }
        lifee.setText(current_life + "/" + max_life);
        editor.apply();
    }

    //stop function
    @Override
    protected void onStop() {
        sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("clife",current_life);
        editor.apply();
        super.onStop();
    }

    //"Information about the App"-Button
    public void onAppInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);
                    builder.setTitle("App info");
                    builder.setMessage("This app/game was made by Emre.\nYou can tell me your feedback and if You have found a bug just tell it me in the group with a screenshot and a nice describtion of the bug.\n\nBeta version 2.0-0");
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
                sharedPref = getSharedPreferences("data2", Context.MODE_PRIVATE);
                delay = sharedPref.getInt("delay",5000);
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
