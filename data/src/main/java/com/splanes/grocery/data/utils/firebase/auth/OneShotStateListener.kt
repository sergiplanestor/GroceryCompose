package com.splanes.grocery.data.utils.firebase.auth

import com.google.firebase.auth.FirebaseAuth

class OneShotStateListener(private val block: () -> Unit) : FirebaseAuth.AuthStateListener {
    override fun onAuthStateChanged(auth: FirebaseAuth) {
        auth.removeAuthStateListener(this)
        block()
    }
}