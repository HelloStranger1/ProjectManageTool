package com.example.projectmanagetool.firebase

import android.util.Log
import com.example.projectmanagetool.activities.SignInActivity
import com.example.projectmanagetool.activities.SignUpActivity
import com.example.projectmanagetool.models.User
import com.example.projectmanagetool.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FirestoreClass {

    private val mFirestore = Firebase.firestore

    fun registerUser(activity : SignUpActivity, userInfo : User){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error Writing Document",
                    e
                )
            }

    }

    fun signInUser(activity : SignInActivity){
        mFirestore.collection(Constants.USERS)
            .document()
            .get()
            .addOnSuccessListener {document ->
                val loggedInUser = document.toObject(User::class.java)
                if (loggedInUser != null){
                    activity.signInSuccess(loggedInUser!!)
                }

            }.addOnFailureListener{
                Log.e("Firestore SignIn", "Error writing document")

            }

    }
    fun getCurrentUserId() : String{
        val currentUser =  FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId

    }
}