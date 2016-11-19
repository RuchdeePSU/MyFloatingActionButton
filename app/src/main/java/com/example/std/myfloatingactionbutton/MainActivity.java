package com.example.std.myfloatingactionbutton;

import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> myFriend = new ArrayList<String>();
    ArrayAdapter<String> friendAdapter;
    ListView myFriend_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFriend_lv = (ListView) findViewById(R.id.lvfriend);
        friendAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFriend);
        myFriend_lv.setAdapter(friendAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewFriend();
            }
        });
    }

    public void addNewFriend() {
        // show custom dialog to get friend name
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater mLayoutInflater = getLayoutInflater();
        View add_friend_view = mLayoutInflater.inflate(R.layout.friend_dialog, null);
        builder.setView(add_friend_view);

        // define coordinatorlayout for snackbar
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);

        final EditText friendname = (EditText) add_friend_view.findViewById(R.id.edt_friend);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.show();

        // action for undo button
        final View.OnClickListener undoOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove latest item from array
                myFriend.remove(myFriend.size()-1);
                // update change in adapter
                friendAdapter.notifyDataSetChanged();
                // show message on snackbar
                Snackbar.make(v, friendname.getText() + " is removed", Snackbar.LENGTH_LONG).show();
            }
        };

        mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!friendname.getText().toString().isEmpty()) {
                    // add new item into array
                    myFriend.add(friendname.getText().toString());
                    // update change in adapter
                    friendAdapter.notifyDataSetChanged();
                    // show message on snackbar
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, friendname.getText() + " is added", Snackbar.LENGTH_LONG)
                            .setAction("Undo", undoOnClickListener);
                    snackbar.show();
                    mAlertDialog.dismiss();
                }
                else {
                    // friendname is empty, display warning message
                    Toast.makeText(MainActivity.this,
                            "Please enter your friend name",
                            Toast.LENGTH_SHORT).show();
                    // set focus
                    friendname.requestFocus();
                }
            }
        });
    }
}
