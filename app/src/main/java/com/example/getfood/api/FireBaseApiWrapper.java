package com.example.getfood.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Keep this as a Singleton Class
 */
class FireBaseApiWrapper implements FireBaseApiWrapperInterface {

    private static FireBaseApiWrapper apiWrapper;
    private static DatabaseReference mDatabase;
//    todo: create a boolean for logging and enable it for dev/debug build flavors or changeable

    /**
     * Call to initialize various variables
     *
     * @return return instance of @{@link FireBaseApiWrapper}
     */
    static FireBaseApiWrapper getInstance() {
        if (apiWrapper == null) {
            apiWrapper = new FireBaseApiWrapper();
        }
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return apiWrapper;
    }


    /**
     * Add a listener for child events occurring at this location. When child locations are added, removed, changed, or moved, the listener will be triggered for the appropriate event
     * Parameters
     * listener
     *
     * @param mDatabaseReference Path to which Child events to listen to
     * @param childEventListener The listener to be called with changes
     *                           Return A reference to the listener provided. Save this to remove the listener later.
     */
    @Override
    public void childEventListener(@NonNull DatabaseReference mDatabaseReference, final ChildEventListener childEventListener) {
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                childEventListener.onChildAdded(dataSnapshot, s);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                childEventListener.onChildChanged(dataSnapshot, s);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                childEventListener.onChildRemoved(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                childEventListener.onChildMoved(dataSnapshot, s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                childEventListener.onCancelled(databaseError);
            }
        });
    }

    @Override
    public void valueEventListener(@NonNull DatabaseReference mDatabaseReference, final ValueEventListener eventListener) {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventListener.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                eventListener.onCancelled(databaseError);
            }
        });

    }

    /**
     * @param mDatabaseReference
     * @param singleValueEventListener
     */
    @Override
    public void singleValueEventListener(@NonNull DatabaseReference mDatabaseReference, final ValueEventListener singleValueEventListener) {
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                singleValueEventListener.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                singleValueEventListener.onCancelled(databaseError);
            }
        });
    }

    /**
     * Signs out user
     */
    @Override
    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean isUserVerified() {
        return FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified();
    }
}
