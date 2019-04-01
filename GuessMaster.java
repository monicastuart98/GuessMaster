package com.example.monica.guessmaster;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;
import java.util.Random;


public class GuessMaster extends AppCompatActivity {

    private TextView entityName;
    private TextView ticketsum;
    private Button guessButton;
    private EditText userIn;
    private Button btnclearContent;
    private ImageView entityImage;
    private Entity currentEntity;



    private int numOfEntities;
    //an array that sores the entities being added
    private Entity[] entities;
    private int entityid = 0;
    //an array that holds the amount of tickets earned in each round of the game
    private int[] tickets;
    private int numOfTickets;
    int totaltik = 0;
    private int ticketsumval = 0;
    String answer;
    String entname;

    Politician jTrudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);////
    Singer cDion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu", new Date("November", 6, 1981), 0.5);////
    Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);
    Person myCreator = new Person("My Creator", new Date("September", 1, 2000),"Female", 1);






    public GuessMaster() {
        numOfEntities = 0;
        entities = new Entity[10];
    }

    public void addEntity(Entity entity) {
        entities[numOfEntities++] = entity.clone();
    }


    public void welcomeToGame(Entity entity) {

        //sets welcome message in an alert dialog box
        AlertDialog.Builder welcomealert = new AlertDialog.Builder(GuessMaster.this);
        welcomealert.setTitle("GuessMaster_Game_v3");
        welcomealert.setMessage(entity.welcomeMessage());
        welcomealert.setCancelable(false);
        welcomealert.setNegativeButton("START_GAME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Game_is_Starting...Enjoy", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = welcomealert.create();
        dialog.show();
    }
    public void playGame() {

        int entityId = genRandomEntityId();
        playGame(entityId);

    }

    public int genRandomEntityId() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(numOfEntities);
    }
    public void playGame(int entityId) {
        Entity entity = entities[entityId];
        entname = entity.getName();
        //calls xml file to display the name of the entity to guess
        entityName.setText(entname);
        //sets image of the entity
        ImageSetter();
        //updates the current entity being guessed
        currentEntity=entity;

    }

    public void playGame(Entity entity) {

        //assign player's guess to answer variable
        answer = userIn.getText().toString();
        answer = answer.replace("\n", "").replace("\r", "");

        Date date = new Date(answer);

        //checks if player's answer is the correct date
        if (date.precedes(entity.getBorn())) {

            ImageView ex1=new ImageView(GuessMaster.this);
            ex1.setImageResource(R.drawable.ic_error_outline_black_24dp);

            AlertDialog.Builder earlydate = new AlertDialog.Builder(GuessMaster.this);
            earlydate.setView(ex1);
            earlydate.setMessage("Try a date later than"+answer);
            earlydate.setTitle("Incorrect");
            earlydate.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialop, int which) {
                    Toast.makeText(getBaseContext(), "Try again", Toast.LENGTH_SHORT).show();
                    //clears user's guess
                    userIn.getText().clear();

                }
            });
            AlertDialog dialog2 = earlydate.create();
            dialog2.show();

        } else if (entity.getBorn().precedes(date)) {
            ImageView ex=new ImageView(GuessMaster.this);
            ex.setImageResource(R.drawable.ic_error_outline_black_24dp);

            AlertDialog.Builder latedate = new AlertDialog.Builder(GuessMaster.this);
            latedate.setView(ex);
            latedate.setMessage("Try an earlier date than "+answer);
            latedate.setTitle("Incorrect. Try an earlier date.");
            latedate.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialop, int which) {
                    Toast.makeText(getBaseContext(), "Try again", Toast.LENGTH_SHORT).show();
                    //clears users answer
                    userIn.getText().clear();
                }
            });
            AlertDialog dialog3 = latedate.create();
            dialog3.show();
        } else {
            ticketsumval = entity.getAwardedTicketNumber();
            tickets=new int[100];
            tickets[numOfTickets++] = entity.getAwardedTicketNumber();
            for (int i = 0; i < 100; i++) {
                totaltik = totaltik + tickets[i];
            }
            //updates the total tickets earned on screen
            ticketsum.setText("Total Tickets Earned: "+totaltik);

            ImageView checkMark=new ImageView(GuessMaster.this);
            checkMark.setImageResource(R.drawable.ic_check_circle_black_24dp);

            AlertDialog.Builder won = new AlertDialog.Builder(GuessMaster.this);
            won.setView(checkMark);
            won.setTitle("You Won!");
            won.setMessage("Bingo!" + entity.closingMessage());
            won.setNegativeButton("Continue playing", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialop, int which) {
                    Toast.makeText(getBaseContext(), "You won " + ticketsumval + " tickets in this round.", Toast.LENGTH_SHORT).show();
                    //if the player has won the continueGame() method is called to change the next entity to be guessed
                    ContinueGame();
                }
            });
            AlertDialog dialog4 = won.create();
            dialog4.show();



        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets activity_guess_activity layout to be displayed on screen
        setContentView(R.layout.activity_guess_activity);

        //adds entities to be guessed
        addEntity(usa);
        addEntity(jTrudeau);
        addEntity(cDion);
        addEntity(myCreator);


        //submit guess button
        guessButton = (Button) findViewById(R.id.guessButton);
        //Change entity button
        btnclearContent = (Button) findViewById(R.id.btnclearContent);

        //defining editText to allow users to type their guess
        userIn = (EditText) findViewById(R.id.userIn);
        //defining textview to display total tickets earned
        ticketsum = (TextView) findViewById(R.id.ticketsum);
        //defining textview to display name of entity to be guessed
        entityName = (TextView) findViewById(R.id.entityName);
        //defining imageview to display image of entity to be guessed
        entityImage = (ImageView) findViewById(R.id.entityImage);

        //starts the game with the first entity to be guessed
        entityid = genRandomEntityId();
        Entity entity = entities[entityid];
        currentEntity=entity;
        entname = entity.getName();
        entityName.setText(entname);
        ImageSetter();
        welcomeToGame(entity);

        //when the change entity button is pressed the entity is changed by calling changeEntity();
        btnclearContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEntity();
            }
        });
        //when the submit guess button is clicked the users guess is evaluated by calling playGame(currentEntity)
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               playGame(currentEntity);
            }
        });



    }


//resets the entity
    public void ContinueGame() {
        playGame();
        userIn.getText().clear();

    }
//clears the user input, and changes the entity to be guessed
    public void changeEntity() {
        userIn.getText().clear();
        playGame();



    }

//sets the image of the entity being played
    public void ImageSetter() {
        if (entname.equals("Justin Trudeau")) {
            entityImage.setImageResource(R.drawable.justint);
        } else if (entname.equals("Celine Dion")) {
            entityImage.setImageResource(R.drawable.celidion);
        } else if (entname.equals("United States")) {
            entityImage.setImageResource(R.drawable.usaflag);
        } else {
            entityImage.setImageResource(R.drawable.pregnantmom);
        }


    }

}