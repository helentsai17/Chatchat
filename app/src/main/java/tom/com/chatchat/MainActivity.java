package tom.com.chatchat;

import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String Tag = "FirebaseTest";

    private FirebaseApp mApp;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFirebase();
        writeDatabaseData();
        writeObject();
        readObject();
    }

    private void readObject() {
        final DatabaseReference reference = mDatabase.getReference("ChatMessage");
        final ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(Tag,"Data received: "+ dataSnapshot.getChildrenCount());
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    ChatMessage message = new ChatMessage();
                    message = child.getValue(ChatMessage.class);
                    Log.e(Tag,"child "+message.chatMessage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(listener);
    }


    private void initFirebase() {
        mApp = FirebaseApp.getInstance();
        mDatabase = FirebaseDatabase.getInstance(mApp);
    }

    private void writeDatabaseData() {
        DatabaseReference reference = mDatabase.getReference("ChatMessage").child("Andrew 12:41:23 25-05-2018");
        reference.child("sentTime").setValue("12:41:23 25-05-2018");
        reference.child("Sender").setValue("Andrew");
        reference.child("ChatMessage").setValue("Today is Sunday");

    }
    private void writeObject() {

        ChatMessage msg = new ChatMessage();
        msg.sender = "Sue";
        msg.sentTime = "11:26:01 26-05-2018";
        msg.chatMessage = "Drive my car";

        DatabaseReference ref = mDatabase.getReference("ChatMessage").child("Sue 11:26:01 26-05-2018");
        ref.setValue(msg);

//        ChatMessage message = new ChatMessage();
//        message.Sender ="Sue";
//        message.sendTime ="11:26:01 26-05-2018";
//        message.Chatmessage ="Drive safe";
//
//        DatabaseReference reference = mDatabase.getReference("Chatmessage").child("Sue 11:26:01 26-05-2018");
//        reference.setValue(message);
    }
}
