package com.micahnyabuto.livespotevents.ui.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
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

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
    }

    fun onSignUpClicked() {
        // Here you would add your registration logic.
        // For example, validating inputs and calling a repository.

        // Basic validation example
        if (password != confirmPassword) {
            // TODO: Handle password mismatch error (e.g., show a toast or a snackbar)
            return
        }

        viewModelScope.launch {
            // TODO: Add your actual registration logic here using a repository.
            // try {
            //   authRepository.register(email, password)
            _navigationEvent.emit("login") // Navigate to login on success
            // } catch (e: Exception) {
            //   // Handle error case
            // }
        }
    }
}
