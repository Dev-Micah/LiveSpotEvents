package com.micahnyabuto.livespotevents.ui.auth.login

import androidx.activity.result.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    // A SharedFlow to send one-time navigation events to the UI
    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onSignInClicked() {
        // Here you would typically add your authentication logic,
        // for example, calling a repository or a use case.
        // For now, we'll just simulate a successful login and navigate.

        viewModelScope.launch {
            // TODO: Add your actual authentication logic here
            // if (authRepository.login(email, password)) {
            _navigationEvent.emit("main")
            // } else {
            // Handle error case, e.g., show a snackbar
            // }
        }
    }
}